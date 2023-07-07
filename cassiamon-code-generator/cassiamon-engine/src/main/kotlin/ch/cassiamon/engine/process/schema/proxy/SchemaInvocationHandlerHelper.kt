package ch.cassiamon.engine.process.schema.proxy

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName
import ch.cassiamon.api.process.schema.annotations.ChildConcepts
import ch.cassiamon.api.process.schema.annotations.Concept
import ch.cassiamon.api.process.schema.annotations.InputFacet
import ch.cassiamon.api.process.schema.annotations.Schema
import java.lang.reflect.Method
import kotlin.reflect.KClass

object SchemaInvocationHandlerHelper {

    fun isSchemaAnnotated(clazz: Class<*>?): Boolean {
        if(clazz == null) {
            return false
        }
        return clazz.getAnnotation(Schema::class.java) != null
    }

    fun isChildConceptAnnotated(method: Method?): Boolean {
        return validatedMethod(method).getAnnotation(ChildConcepts::class.java) != null
    }

    fun getChildConceptsClazz(method: Method?): KClass<*> {
        return validatedMethod(method).getAnnotation(ChildConcepts::class.java).clazz
    }

    fun getChildConceptsName(method: Method?): ConceptName {
        return ConceptName.of(getChildConceptsClazz(method).java.getAnnotation(Concept::class.java).conceptName)
    }

    fun isInputFacetAnnotated(method: Method?): Boolean {
        return validatedMethod(method).getAnnotation(InputFacet::class.java) != null
    }

    fun getInputFacetName(method: Method?): FacetName {
        return FacetName.of(validatedMethod(method).getAnnotation(InputFacet::class.java).inputFacetName)
    }

    private fun validatedMethod(method: Method?): Method {
        if(method == null) {
            throw IllegalStateException("Proxy $this can only handle methods, not field invocations.")
        }

        return method

    }
}
