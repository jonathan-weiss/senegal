package ch.cassiamon.pluginapi.registration.types

import ch.cassiamon.pluginapi.model.ConceptModelNode
fun interface FacetTransformationFunction<T> {
    operator fun invoke(conceptModelNode: ConceptModelNode, value: T):T
}
