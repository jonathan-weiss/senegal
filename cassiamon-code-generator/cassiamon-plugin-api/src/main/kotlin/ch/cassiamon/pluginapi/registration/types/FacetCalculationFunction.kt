package ch.cassiamon.pluginapi.registration.types

import ch.cassiamon.pluginapi.model.ConceptNode

sealed interface FacetCalculationFunction<T> {
    fun invoke(conceptNode: ConceptNode):T
}
