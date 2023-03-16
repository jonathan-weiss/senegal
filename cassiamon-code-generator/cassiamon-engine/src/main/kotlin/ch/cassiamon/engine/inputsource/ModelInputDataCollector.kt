package ch.cassiamon.engine.inputsource

import ch.cassiamon.engine.model.facets.InputFacetValueCollector
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.facets.InputFacet

class ModelInputDataCollector {
    private val entries: MutableList<ModelConceptInputDataEntry> = mutableListOf()

    fun provideModelInputData(): ModelInputData {
        return ModelInputData(entries.toList())
    }

    fun newConceptData(conceptName: ConceptName, conceptIdentifier: ConceptIdentifier, parentConceptIdentifier: ConceptIdentifier?): ModelConceptInputDataEntryBuilder {
        return ModelConceptInputDataEntryBuilder(conceptName, conceptIdentifier, parentConceptIdentifier)
    }

    inner class ModelConceptInputDataEntryBuilder(
        private val conceptName: ConceptName,
        private val conceptIdentifier: ConceptIdentifier,
        private val parentConceptIdentifier: ConceptIdentifier?,
        
    ) {
        private val facetValueCollector: InputFacetValueCollector = InputFacetValueCollector()
        private var isAttached: Boolean = false

        fun <T> addFacetValue(facet: InputFacet<T>, value: T): ModelConceptInputDataEntryBuilder {
            facetValueCollector.addFacetValue(facet, value)
            return this
        }

        fun attach() {
            if(isAttached) {
                throw IllegalStateException("Can not attach this ModelConceptInputDataEntry to collection, as already attached.")
            }
            isAttached = true;
            val entry =  ModelConceptInputDataEntry(
                conceptName = conceptName,
                conceptIdentifier = conceptIdentifier,
                parentConceptIdentifier = parentConceptIdentifier,
                inputFacetValueAccess = facetValueCollector
            )
            this@ModelInputDataCollector.entries.add(entry)
        }

    }




}
