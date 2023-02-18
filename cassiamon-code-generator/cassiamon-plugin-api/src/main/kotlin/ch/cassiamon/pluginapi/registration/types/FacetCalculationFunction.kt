package ch.cassiamon.pluginapi.registration.types

import ch.cassiamon.pluginapi.model.ConceptNode

fun interface FacetCalculationFunction<T> {
    operator fun invoke(conceptNode: ConceptNode):T
}
