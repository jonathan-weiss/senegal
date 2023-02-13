package ch.cassiamon.pluginapi.registration.types

import ch.cassiamon.pluginapi.registration.GraphNode

sealed interface FacetCalculationFunction<T> {
    fun invoke(graphNode: GraphNode):T
}
