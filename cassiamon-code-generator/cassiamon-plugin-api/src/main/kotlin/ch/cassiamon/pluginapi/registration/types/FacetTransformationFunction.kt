package ch.cassiamon.pluginapi.registration.types

import ch.cassiamon.pluginapi.ConceptNode

sealed interface FacetTransformationFunction<T> {
    fun invoke(conceptNode: ConceptNode, value: T):T
}
