package ch.cassiamon.engine.inputsource

import ch.cassiamon.api.model.ConceptIdentifier
import ch.cassiamon.api.*
import ch.cassiamon.api.model.facets.InputFacetValue
import ch.cassiamon.api.registration.*
import ch.cassiamon.engine.domain.datacollection.FacetValueCollector

class ModelInputDataCollector: InputSourceDataCollector {
    @Deprecated("Old input facets") private val entries: MutableList<ModelConceptInputDataEntry> = mutableListOf()
    private val conceptEntryList: MutableList<ConceptEntry> = mutableListOf()

    @Deprecated("Old input facets")
    fun provideModelInputData(): ModelInputData {
        return ModelInputData(entries.toList())
    }

    fun provideConceptEntries(): ConceptEntries {
        return ConceptEntries(conceptEntryList.toList())
    }

    override fun newConceptData(conceptName: ConceptName, conceptIdentifier: ConceptIdentifier, parentConceptIdentifier: ConceptIdentifier?): ModelConceptInputDataEntryBuilder {
        return ModelConceptInputDataEntryBuilder(conceptName, conceptIdentifier, parentConceptIdentifier)
    }

    inner class ModelConceptInputDataEntryBuilder(
        private val conceptName: ConceptName,
        private val conceptIdentifier: ConceptIdentifier,
        private val parentConceptIdentifier: ConceptIdentifier?,
        
    ): InputSourceConceptFacetValueBuilder {
        @Deprecated("Old input facets") private val deprecatedFacetValueCollector: InputFacetValueCollector = InputFacetValueCollector()
        private val facetValueCollector: FacetValueCollector = FacetValueCollector()

        private var isAttached: Boolean = false

        override fun <T> addFacetValue(facetValue: InputFacetValue<T>): ModelConceptInputDataEntryBuilder {
            deprecatedFacetValueCollector.addFacetValue(facetValue)
            addFacetValue(facetValue.inputFacet.facetName, facetValue.facetValue) // backup
            return this
        }

        override fun addFacetValue(facetName: FacetName, facetValue: Any?): InputSourceConceptFacetValueBuilder {
            facetValueCollector.addFacetValue(facetName, facetValue)
            return this
        }

        override fun attach() {
            if(isAttached) {
                throw IllegalStateException("Can not attach this ModelConceptInputDataEntry to collection, as already attached.")
            }
            isAttached = true

            val entry =  ConceptEntry(
                conceptName = conceptName,
                conceptIdentifier = conceptIdentifier,
                parentConceptIdentifier = parentConceptIdentifier,
                facetValues = facetValueCollector.getFacetValues(),
            )
            this@ModelInputDataCollector.conceptEntryList.add(entry)

            attachAsModelConceptInputDataEntry()

        }

        @Deprecated("old facet style")
        private fun attachAsModelConceptInputDataEntry() {
            val entry =  ModelConceptInputDataEntry(
                conceptName = conceptName,
                conceptIdentifier = conceptIdentifier,
                parentConceptIdentifier = parentConceptIdentifier,
                inputFacetValueAccess = deprecatedFacetValueCollector
            )
            this@ModelInputDataCollector.entries.add(entry)

        }

    }
}
