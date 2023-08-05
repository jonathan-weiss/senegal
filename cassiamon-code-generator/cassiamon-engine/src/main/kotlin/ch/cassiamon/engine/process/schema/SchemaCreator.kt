package ch.cassiamon.engine.process.schema

import ch.cassiamon.api.process.schema.*
import ch.cassiamon.api.process.schema.annotations.*
import ch.cassiamon.api.process.schema.exceptions.MalformedSchemaException
import java.lang.reflect.Method
import kotlin.jvm.Throws

object SchemaCreator {

    @Throws(MalformedSchemaException::class)
    fun createSchemaFromSchemaDefinitionClass(schemaDefinitionClass: Class<*>): SchemaImpl {
        validateTypeAnnotation(annotation = Schema::class.java, classToInspect = schemaDefinitionClass)

        if(hasClassAnnotation(annotation = Concept::class.java, classToInspect = schemaDefinitionClass)) {
            throw MalformedSchemaException("Definition class '${schemaDefinitionClass.name}' can not be a concept having an annotation of type '${Concept::class.java.name}'")
        }

        val concepts: MutableMap<ConceptName, ConceptSchema> = mutableMapOf()

        schemaDefinitionClass.methods.forEach { method ->
            if(hasMethodAnnotation(ChildConcepts::class.java, method)) {
                validateAndAddConceptForChildConceptsAnnotation(concepts, schemaDefinitionClass, method, null)
            } else if(hasMethodAnnotation(ChildConceptsWithCommonBaseInterface::class.java, method)) {
                validateAndAddConceptForChildConceptsWithCommonBaseInterfaceAnnotation(concepts, schemaDefinitionClass, method, null)
            } else {
                throw MalformedSchemaException("Schema definition class '${schemaDefinitionClass.name}' can " +
                        "only have methods annotated with '${ChildConcepts::class.qualifiedName}'. Not valid for method '$method'.")
            }
        }

        return SchemaImpl(concepts)
    }

    private fun validateChildConceptMethod(definitionClass: Class<*>, method: Method) {
        if(method.returnType != List::class.java) {
            throw MalformedSchemaException("The method '$method' on the definition class '${definitionClass.name}' " +
                    "must return a '${List::class.java}' but is returning a '${method.returnType}'.")
        }
    }

    private fun validateAndAddConcept(concepts: MutableMap<ConceptName, ConceptSchema>, conceptClass: Class<*>, parentConcept: ConceptName?) {
        validateTypeAnnotation(annotation = Concept::class.java, classToInspect = conceptClass)
        val conceptName = ConceptName.of(conceptClass.getAnnotation(Concept::class.java).conceptName)

        concepts[conceptName]?.let {
            if(it.parentConceptName != parentConcept) {
                throw MalformedSchemaException("There is a cyclic dependency with concept '${conceptName.name}'.")
            }
        }

        concepts[conceptName] = createConceptSchema(conceptName, conceptClass, parentConcept)

        conceptClass.methods.forEach { method ->
            if(hasMethodAnnotation(ChildConcepts::class.java, method)) {
                validateAndAddConceptForChildConceptsAnnotation(concepts, conceptClass, method, conceptName)
            } else if(hasMethodAnnotation(ChildConceptsWithCommonBaseInterface::class.java, method)) {
                validateAndAddConceptForChildConceptsWithCommonBaseInterfaceAnnotation(concepts, conceptClass, method, conceptName)
            } else if(hasMethodAnnotation(Facet::class.java, method)) {
                // ignore and skip, this is allowed
            } else {
                throw MalformedSchemaException("Concept definition class '${conceptClass.name}' can " +
                        "only have methods annotated with '${ChildConcepts::class.qualifiedName}' or '${Facet::class.qualifiedName}'. " +
                        "Not valid for method '$method'.")
            }
        }
    }

    private fun validateAndAddConceptForChildConceptsAnnotation(concepts: MutableMap<ConceptName, ConceptSchema>, definitionClass: Class<*>, method: Method, parentConcept: ConceptName?) {
        validateChildConceptMethod(definitionClass, method)
        val conceptClass = method.getAnnotation(ChildConcepts::class.java).conceptClass.java
        validateAndAddConcept(concepts, conceptClass = conceptClass, parentConcept = parentConcept)
    }

    private fun validateAndAddConceptForChildConceptsWithCommonBaseInterfaceAnnotation(concepts: MutableMap<ConceptName, ConceptSchema>, definitionClass: Class<*>, method: Method, parentConcept: ConceptName?) {
        validateChildConceptMethod(definitionClass, method)
        val baseInterfaceClass = method.getAnnotation(ChildConceptsWithCommonBaseInterface::class.java).baseInterfaceClass.java
        val conceptClasses = method.getAnnotation(ChildConceptsWithCommonBaseInterface::class.java).conceptClasses.map { it.java }
        conceptClasses.forEach { conceptClass ->
            if(!isInheriting(conceptClass, baseInterfaceClass)) {
                throw MalformedSchemaException("Class ${conceptClass.name} must inherit base class ${baseInterfaceClass.name} in method $method.")
            }
            validateAndAddConcept(concepts, conceptClass = conceptClass, parentConcept = parentConcept)
        }
    }

    private fun isInheriting(subInterface: Class<*>, baseInterface: Class<*>): Boolean {
        return subInterface.interfaces.contains(baseInterface)
    }

    private fun createConceptSchema(conceptName: ConceptName, conceptClass: Class<*>, parentConcept: ConceptName?): ConceptSchema {
        val facets = mutableListOf<FacetSchema>()
        conceptClass.methods.forEach { method ->
            if(hasMethodAnnotation(Facet::class.java, method)) {
                val facetName = FacetName.of(method.getAnnotation(Facet::class.java).facetName)
                val isMandatory = method.getAnnotation(Facet::class.java).mandatory
                val returnType = method.returnType
                val facetType: FacetTypeEnum = FacetTypeEnum.matchingEnumByTypeClass(returnType.kotlin)
                    ?: throw MalformedSchemaException("Return type '$returnType' of method '$method' does not match any compatible facet types.")
                val referencingConcept = FacetTypeEnum.referencedTypeConceptName(returnType.kotlin)
                val facet = FacetSchemaImpl(facetName, facetType, mandatory = isMandatory, referencingConcept = referencingConcept)
                facets.add(facet)
            }
        }

        return ConceptSchemaImpl(conceptName, parentConcept, facets)
    }

    private fun validateTypeAnnotation(annotation: Class<out Annotation>, classToInspect: Class<*>) {
        if(!classToInspect.isInterface) {
            throw MalformedSchemaException("Definition class '${classToInspect.name}' must be an interface.")
        }
        if(!hasClassAnnotation(annotation, classToInspect)) {
            throw MalformedSchemaException("Definition class '${classToInspect.name}' must have an annotation of type '${annotation.name}'")
        }
    }

    private fun hasClassAnnotation(annotation: Class<out Annotation>, classToInspect: Class<*>): Boolean {
        return classToInspect.getAnnotation(annotation) != null
    }

    private fun hasMethodAnnotation(annotation: Class<out Annotation>, methodToInspect: Method): Boolean {
        return methodToInspect.getAnnotation(annotation) != null
    }

}
