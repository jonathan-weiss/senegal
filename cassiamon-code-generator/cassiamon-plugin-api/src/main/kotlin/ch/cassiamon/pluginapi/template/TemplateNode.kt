package ch.cassiamon.pluginapi.template

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.ConceptIdentifier

interface TemplateNode {
    val conceptName: ConceptName
    val conceptIdentifier: ConceptIdentifier
    val facetValues: TemplateNodeFacetValues

    fun parent(): TemplateNode?
    fun allChildren(): List<TemplateNode>
    fun children(conceptName: ConceptName): List<TemplateNode>

    val asTemplateNodeBag: TemplateNodeBag
        get() = TemplateNodeBag(this)

    /**
      Support for template engines
      TODO document which keys are allowed
     */
    operator fun get(key: String): Any?

}
