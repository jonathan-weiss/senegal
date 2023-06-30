package ch.cassiamon.engine.domain.process.proxy

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.annotations.datacollector.*
import ch.cassiamon.api.model.ConceptIdentifier
import java.lang.reflect.Method
import kotlin.reflect.KClass

object DataCollectorInvocationHandlerHelper {

    fun isAddConceptAnnotated(method: Method?): Boolean {
        return validatedMethod(method).getAnnotation(AddConcept::class.java) != null
    }

    fun isAddFacetAnnotated(method: Method?): Boolean {
        return validatedMethod(method).getAnnotation(AddFacet::class.java) != null
    }

    fun isCommitConceptAnnotated(method: Method?): Boolean {
        return validatedMethod(method).getAnnotation(CommitConcept::class.java) != null
    }


    fun getConceptNameParameter(method: Method?, args: Array<out Any>?): ConceptName {
        return getParameter(method, ConceptNameValue::class.java, ConceptName::class.java, args)
    }

    fun getConceptIdentifierParameter(method: Method?, args: Array<out Any>?): ConceptIdentifier {
        return getParameter(method, ConceptIdentifierValue::class.java, ConceptIdentifier::class.java, args)
    }

    fun getParentConceptIdentifierParameter(method: Method?, args: Array<out Any>?): ConceptIdentifier? {
        return getNullableParameter(method, ParentConceptIdentifierValue::class.java, ConceptIdentifier::class.java, args)
    }

    fun getConceptBuilderClazz(method: Method?): KClass<*> {
        return validatedMethod(method).getAnnotation(AddConcept::class.java).clazz
    }

    fun getFacetNameParameter(method: Method?, args: Array<out Any>?): FacetName {
        return getParameter(method, FacetNameValue::class.java, FacetName::class.java, args)
    }

    fun getFacetValueParameter(method: Method?, args: Array<out Any>?): Any? {
        return getNullableParameter(method, FacetValue::class.java, Any::class.java, args)
    }

    private fun <T> getNullableParameter(method: Method?, annotation: Class<out Annotation>, type: Class<T>, args: Array<out Any>?): T? {
        // TODO validate that only one parameter is present
        // TODO handle case that no parameter is present

        val arguments = validatedArguments(method, args)

        for ((index, parameter) in validatedMethod(method).parameters.withIndex()) {
            if(parameter.getAnnotation(annotation) != null) {
                return arguments[index] as T?
            }
        }
        throw IllegalStateException("Method $method: No arguments found for annotation '$annotation' in $arguments")

    }
    private fun <T> getParameter(method: Method?, annotation: Class<out Annotation>, type: Class<T>, args: Array<out Any>?): T {
        return getNullableParameter(method, annotation, type, args)
            ?: throw IllegalStateException("Method $method: Arguments for annotation '$annotation' in $args was null.")
    }

    private fun validatedArguments(method: Method?, args: Array<out Any>?): Array<out Any> {
        val parameterCount = validatedMethod(method).parameterCount
        if(args == null) {
            throw IllegalStateException("No Arguments were provided for method $method in proxy $this.")
        }

        val argumentCount = args.size
        if(argumentCount != parameterCount) {
            throw IllegalStateException("The method $method in proxy $this expect $parameterCount arguments, but was $argumentCount.")
        }

        return args

    }


    private fun validatedMethod(method: Method?): Method {
        if(method == null) {
            throw IllegalStateException("Proxy $this can only handle methods, not field invocations.")
        }

        return method

    }
}
