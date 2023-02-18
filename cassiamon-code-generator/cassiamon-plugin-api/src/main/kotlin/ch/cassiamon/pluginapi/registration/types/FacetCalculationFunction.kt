package ch.cassiamon.pluginapi.registration.types

import ch.cassiamon.pluginapi.model.ConceptModelNode

fun interface FacetCalculationFunction<T> {
    operator fun invoke(conceptModelNode: ConceptModelNode):T
}
