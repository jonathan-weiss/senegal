package ch.cassiamon.engine.template

import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.FacetValues
import ch.cassiamon.pluginapi.template.TemplateNode
import ch.cassiamon.pluginapi.template.TemplateNodeFacetValues
import ch.cassiamon.pluginapi.template.exceptions.TemplateException

class MutableTemplateNodeFacetValues(private val facetValues: FacetValues): TemplateNodeFacetValues {

    private var referenceTemplateNodeFacetValues: Map<FacetName, TemplateNode> = emptyMap()
    fun assignTemplateNodeFacetValues(referenceTemplateNodeFacetValues: Map<FacetName, TemplateNode>) {
        this.referenceTemplateNodeFacetValues = referenceTemplateNodeFacetValues
    }

    override fun asString(facetName: FacetName): String {
        return facetValues.asString(facetName)
    }

    override fun asInt(facetName: FacetName): Int {
        return facetValues.asInt(facetName)
    }

    override fun asConceptIdentifier(facetName: FacetName): ConceptIdentifier {
        return facetValues.asConceptIdentifier(facetName)
    }

    override fun asReferencedTemplateNode(facetName: FacetName): TemplateNode {
        return referenceTemplateNodeFacetValues[facetName]
            // TODO add information to node to the exception
            ?: throw TemplateException("Referenced template node for facet '$facetName' not found.")
    }

    override fun get(key: String): Any? {
        TODO("Not yet implemented")
    }
}
