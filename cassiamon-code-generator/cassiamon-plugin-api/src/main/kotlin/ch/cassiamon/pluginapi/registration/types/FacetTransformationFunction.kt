package ch.cassiamon.pluginapi.registration.types

import ch.cassiamon.pluginapi.model.ConceptNode

sealed interface FacetTransformationFunction<T> {
    fun invoke(conceptNode: ConceptNode, value: T):T
}
