package ch.cassiamon.engine.inputsource

import ch.cassiamon.engine.model.facets.ManualFacetValueCollector
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.*

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


        private val facetValueCollector: ManualFacetValueCollector = ManualFacetValueCollector()
        private var isAttached: Boolean = false

        fun addFacetValue(facet: ManualOptionalTextFacetDescriptor, value: String?): ModelConceptInputDataEntryBuilder {
            facetValueCollector.addTextFacetValue(facet, value)
            return this
        }
        fun addFacetValue(facet: ManualMandatoryTextFacetDescriptor, value: String): ModelConceptInputDataEntryBuilder {
            facetValueCollector.addTextFacetValue(facet, value)
            return this
        }
        fun addFacetValue(facet: ManualOptionalIntegerNumberFacetDescriptor, value: Int?): ModelConceptInputDataEntryBuilder {
            facetValueCollector.addIntegerNumberFacetValue(facet, value)
            return this
        }
        fun addFacetValue(facet: ManualMandatoryIntegerNumberFacetDescriptor, value: Int): ModelConceptInputDataEntryBuilder {
            facetValueCollector.addIntegerNumberFacetValue(facet, value)
            return this
        }
        fun addFacetValue(facet: ManualOptionalConceptReferenceFacetDescriptor, value: ConceptIdentifier?): ModelConceptInputDataEntryBuilder {
            facetValueCollector.addConceptReferenceFacetValue(facet, value)
            return this
        }
        fun addFacetValue(facet: ManualMandatoryConceptReferenceFacetDescriptor, value: ConceptIdentifier): ModelConceptInputDataEntryBuilder {
            facetValueCollector.addConceptReferenceFacetValue(facet, value)
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
                facetValueAccess = facetValueCollector
            )
            this@ModelInputDataCollector.entries.add(entry)
        }

    }




}
