package ch.cassiamon.pluginapi.registration.types

import ch.cassiamon.pluginapi.registration.GraphNode

sealed interface FacetFunction<T> {
    fun invoke(graphNode: GraphNode, value: T):T
}
