package ch.cassiamon.engine.domain.process.proxy

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.annotations.ChildConcepts
import ch.cassiamon.api.annotations.Concept
import ch.cassiamon.api.annotations.InputFacet
import java.lang.reflect.Method
import kotlin.reflect.KClass

object InvocationHandlerHelper {

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
