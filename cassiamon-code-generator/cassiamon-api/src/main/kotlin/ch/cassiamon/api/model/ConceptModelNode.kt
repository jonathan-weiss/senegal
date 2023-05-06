package ch.cassiamon.api.model

import ch.cassiamon.api.ConceptName

interface ConceptModelNode {
    val conceptName: ConceptName
    val conceptIdentifier: ConceptIdentifier
    val templateFacetValues: ConceptModelNodeTemplateFacetValues

    fun parent(): ConceptModelNode?
    fun allChildren(): List<ConceptModelNode>
    fun children(conceptName: ConceptName): List<ConceptModelNode>

    /**
      Support for template engines
      TODO document which keys are allowed
     */
    operator fun get(key: String): Any?

}
