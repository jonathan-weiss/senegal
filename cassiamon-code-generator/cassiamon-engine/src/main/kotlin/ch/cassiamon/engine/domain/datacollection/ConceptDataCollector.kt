package ch.cassiamon.engine.domain.datacollection

import ch.cassiamon.api.ConceptIdentifier
import ch.cassiamon.api.*
import ch.cassiamon.api.extensions.inputsource.ConceptAndFacetDataCollector
import ch.cassiamon.api.datacollection.ConceptData
import ch.cassiamon.api.schema.SchemaAccess

class ConceptDataCollector(private val schema: SchemaAccess, private val validateConcept: Boolean = true):
    ConceptAndFacetDataCollector, ConceptDataProvider {

    private val conceptData: MutableMap<ConceptIdentifier, ConceptData> = mutableMapOf()

    fun conceptByConceptIdentifier(conceptIdentifier: ConceptIdentifier): ConceptData {
        return conceptData[conceptIdentifier] ?: throw IllegalArgumentException("No concept with concept id '$conceptIdentifier' found.")
    }

    fun newConceptData(conceptName: ConceptName, conceptIdentifier: ConceptIdentifier, parentConceptIdentifier: ConceptIdentifier?): MutableConceptData {
        return MutableConceptData(conceptName, conceptIdentifier, parentConceptIdentifier)
    }

    override fun provideConceptData(): List<ConceptData> {
        return conceptData.values.toList()
    }

    override fun newConceptData(
        conceptName: ConceptName,
        conceptIdentifier: ConceptIdentifier,
        parentConceptIdentifier: ConceptIdentifier?,
        facetValues: Map<FacetName, Any?>
    ) {
        newConceptData(conceptName, conceptIdentifier, parentConceptIdentifier)
            .addFacetValues(facetValues)
            .attach()
    }

    inner class MutableConceptData(
        override val conceptName: ConceptName,
        override val conceptIdentifier: ConceptIdentifier,
        override val parentConceptIdentifier: ConceptIdentifier?,
    ): ConceptData {
        private val mutableFacets: MutableMap<FacetName, Any?> = mutableMapOf()
        override val facets: Map<FacetName, Any?>
            get() = mutableFacets.toMap()

        fun addFacetValues(facetValues: Map<FacetName, Any?>): MutableConceptData {
            facetValues.forEach { (facetName, facetValue) ->
                mutableFacets[facetName] = facetValue
            }
            return this
        }


        fun addFacetValue(facetName: FacetName, facetValue: Any?): MutableConceptData {
            mutableFacets[facetName] = facetValue
            return this
        }

        fun attach() {
            if(conceptData.contains(conceptIdentifier)) {
                throw IllegalStateException("Can not attach this ConceptData to collection, as already attached.")
            }
            if(validateConcept) {
                ConceptDataValidator.validateSingleEntry(schema, this)
            }

            conceptData[conceptIdentifier] = this
        }
    }
}
