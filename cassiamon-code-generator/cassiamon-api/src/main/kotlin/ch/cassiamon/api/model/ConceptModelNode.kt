package ch.cassiamon.api.model

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.model.facets.TemplateFacet

interface ConceptModelNode {
    val conceptName: ConceptName
    val conceptIdentifier: ConceptIdentifier
    val templateFacetValues: ConceptModelNodeTemplateFacetValues

    fun parent(): ConceptModelNode?
    fun allChildren(): List<ConceptModelNode>
    fun children(conceptName: ConceptName): List<ConceptModelNode>

    operator fun get(key: String): Any? {
        return when(key) {
            "conceptName" -> conceptName.name
            "conceptIdentifier" -> conceptIdentifier.code
            "parentConceptIdentifier" -> parent()?.conceptIdentifier?.code
            "allChildrenNodes" -> allChildren()
            "parentNode" -> parent()
            else -> templateFacetValues[key]
        }
    }

    operator fun <T> get(templateFacet: TemplateFacet<T>): T {
        return templateFacetValues.facetValue(templateFacet)
    }

}
