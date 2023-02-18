package ch.cassiamon.pluginapi.model

import ch.cassiamon.pluginapi.ConceptName

interface ConceptModelNode {
    val conceptName: ConceptName
    val conceptIdentifier: ConceptIdentifier
    val facetValues: ConceptModelNodeFacetValues

    fun parent(): ConceptModelNode?
    fun allChildren(): List<ConceptModelNode>
    fun children(conceptName: ConceptName): List<ConceptModelNode>

    /**
      Support for template engines
      TODO document which keys are allowed
     */
    operator fun get(key: String): Any?

}
