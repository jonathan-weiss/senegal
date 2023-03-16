package ch.cassiamon.pluginapi.model.facets

sealed interface InputAndTemplateFacet<out I, out T>: InputFacet<I>, TemplateFacet<T>
