package ch.cassiamon.engine.process.schema.proxy

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName
import ch.cassiamon.api.process.schema.annotations.*
import java.lang.reflect.Method
import kotlin.reflect.KClass

object SchemaInvocationHandlerHelper {

    fun isSchemaAnnotated(clazz: Class<*>?): Boolean {
        if(clazz == null) {
            return false
        }
        return clazz.getAnnotation(Schema::class.java) != null
    }

    fun isAnnotatedWithChildConcept(method: Method?): Boolean {
        return validatedMethod(method).getAnnotation(ChildConcepts::class.java) != null
    }

    fun isAnnotatedWithChildConceptWithCommonBaseInterface(method: Method?): Boolean {
        return validatedMethod(method).getAnnotation(ChildConceptsWithCommonBaseInterface::class.java) != null
    }

    private fun getChildInterfaceClass(method: Method?): KClass<*> {
        return if(isAnnotatedWithChildConcept(method)) {
            validatedMethod(method).getAnnotation(ChildConcepts::class.java).conceptClass
        } else if(isAnnotatedWithChildConceptWithCommonBaseInterface(method)) {
            validatedMethod(method).getAnnotation(ChildConceptsWithCommonBaseInterface::class.java).baseInterfaceClass
        } else {
            throw IllegalStateException("Method '$method' must be annotated " +
                    "with ${ChildConcepts::class.java} or ${ChildConceptsWithCommonBaseInterface::class.java} ")
        }

    }

    fun getChildConceptNamesWithInterfaceClass(method: Method?): Map<ConceptName, KClass<*>> {
        return if(isAnnotatedWithChildConcept(method)) {
            val conceptClass = getChildInterfaceClass(method)
            val conceptName = ConceptName.of(conceptClass.java.getAnnotation(Concept::class.java).conceptName)
            mapOf(conceptName to conceptClass)
        } else if(isAnnotatedWithChildConceptWithCommonBaseInterface(method)) {
            validatedMethod(method)
                .getAnnotation(ChildConceptsWithCommonBaseInterface::class.java).conceptClasses
                .associateBy { conceptNameOfClass(it, method) }
        } else {
            throw IllegalStateException("Method '$method' must be annotated " +
                    "with ${ChildConcepts::class.java} or ${ChildConceptsWithCommonBaseInterface::class.java} ")
        }
    }

    private fun conceptNameOfClass(clazz: KClass<*>, method: Method?): ConceptName {
        val conceptAnnotation = clazz.java.getAnnotation(Concept::class.java)
            ?: throw IllegalStateException("Annotation attribute '${ChildConcepts::conceptClasses.name}' " +
                    "of annotation ${ChildConcepts::class.java} " +
                    "in method '$method' " +
                    "can only contain interfaces annotated with ${Concept::class.java}.")
        return ConceptName.of(conceptAnnotation.conceptName)
    }

    fun isInputFacetAnnotated(method: Method?): Boolean {
        return validatedMethod(method).getAnnotation(Facet::class.java) != null
    }

    fun getInputFacetName(method: Method?): FacetName {
        return FacetName.of(validatedMethod(method).getAnnotation(Facet::class.java).facetName)
    }

    private fun validatedMethod(method: Method?): Method {
        if(method == null) {
            throw IllegalStateException("Proxy $this can only handle methods, not field invocations.")
        }

        return method

    }
}
