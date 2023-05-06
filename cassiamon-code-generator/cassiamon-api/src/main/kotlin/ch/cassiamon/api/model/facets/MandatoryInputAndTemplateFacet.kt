package ch.cassiamon.api.model.facets


sealed interface MandatoryInputAndTemplateFacet<out I: Any, out T: Any>: MandatoryInputFacet<I>, MandatoryTemplateFacet<T>, InputAndTemplateFacet<I, T>
