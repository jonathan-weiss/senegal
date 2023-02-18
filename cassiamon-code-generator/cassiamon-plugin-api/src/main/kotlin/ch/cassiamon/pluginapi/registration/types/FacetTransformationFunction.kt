package ch.cassiamon.pluginapi.registration.types

import ch.cassiamon.pluginapi.model.ConceptNode
fun interface FacetTransformationFunction<T> {
    operator fun invoke(conceptNode: ConceptNode, value: T):T
}
