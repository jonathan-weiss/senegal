package ch.cassiamon.api.model.facets


sealed interface OptionalInputAndTemplateFacet<out I: Any?, out T: Any?>: OptionalInputFacet<I>, OptionalTemplateFacet<T>, InputAndTemplateFacet<I, T>