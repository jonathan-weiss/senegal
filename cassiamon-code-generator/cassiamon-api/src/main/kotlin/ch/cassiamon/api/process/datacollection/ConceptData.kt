package ch.cassiamon.api.process.datacollection

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName
import ch.cassiamon.api.process.schema.ConceptIdentifier
import kotlin.jvm.Throws

interface ConceptData {
    val conceptName: ConceptName
    val conceptIdentifier: ConceptIdentifier

    val parentConceptIdentifier: ConceptIdentifier?
    fun setParentConceptIdentifier(parentConceptIdentifier: ConceptIdentifier?): ConceptData

    fun allFacets(): Map<FacetName, Any?>
    fun hasFacet(facetName: FacetName):Boolean
    @Throws(NoSuchElementException::class)
    fun getFacet(facetName: FacetName):Any?
    fun getFacetNames(): Set<FacetName>

    fun addOrReplaceFacetValues(facetValues: Map<FacetName, Any?>): ConceptData
    fun addOrReplaceFacetValue(facetName: FacetName, facetValue: Any?): ConceptData

}
