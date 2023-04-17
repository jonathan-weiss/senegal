package ch.cassiamon.engine.inputsource

import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.facets.InputFacetValue
import ch.cassiamon.pluginapi.registration.InputSourceConceptFacetValueBuilder
import ch.cassiamon.pluginapi.registration.InputSourceDataCollector

class ModelInputDataCollector: InputSourceDataCollector {
    private val entries: MutableList<ModelConceptInputDataEntry> = mutableListOf()

    fun provideModelInputData(): ModelInputData {
        return ModelInputData(entries.toList())
    }

    override fun newConceptData(conceptName: ConceptName, conceptIdentifier: ConceptIdentifier, parentConceptIdentifier: ConceptIdentifier?): ModelConceptInputDataEntryBuilder {
        return ModelConceptInputDataEntryBuilder(conceptName, conceptIdentifier, parentConceptIdentifier)
    }

    inner class ModelConceptInputDataEntryBuilder(
        private val conceptName: ConceptName,
        private val conceptIdentifier: ConceptIdentifier,
        private val parentConceptIdentifier: ConceptIdentifier?,
        
    ): InputSourceConceptFacetValueBuilder {
        private val facetValueCollector: InputFacetValueCollector = InputFacetValueCollector()
        private var isAttached: Boolean = false

        override fun <T> addFacetValue(facetValue: InputFacetValue<T>): ModelConceptInputDataEntryBuilder {
            facetValueCollector.addFacetValue(facetValue)
            return this
        }

        override fun attach() {
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
