package ch.cassiamon.pluginapi.registration.types

import ch.cassiamon.pluginapi.registration.GraphNode

sealed interface FacetTransformationFunction<T> {
    fun invoke(graphNode: GraphNode, value: T):T
}
