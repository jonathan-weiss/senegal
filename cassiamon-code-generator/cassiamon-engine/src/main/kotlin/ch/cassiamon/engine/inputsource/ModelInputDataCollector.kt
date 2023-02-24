package ch.cassiamon.engine.inputsource

import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.engine.model.types.FacetValue
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName

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


        private val facetValueList: MutableList<ModelInputFacetAndValue> = mutableListOf()
        private var isAttached: Boolean = false

        private fun Pair<FacetName, FacetValue>.fromPair(): ModelInputFacetAndValue {
            return ModelInputFacetAndValue(this.first, this.second)
        }
        fun withFacetValues(vararg facetValues: Pair<FacetName, FacetValue>): ModelConceptInputDataEntryBuilder {
            facetValueList.addAll(facetValues.map {it.fromPair() })
            return this
        }

        fun withFacetValue(facetName: FacetName, facetValue: FacetValue): ModelConceptInputDataEntryBuilder {
            facetValueList.add(ModelInputFacetAndValue(facetName, facetValue))
            return this
        }

        fun withFacetValueIfNotNull(facetName: FacetName, facetValue: FacetValue?): ModelConceptInputDataEntryBuilder {
            if(facetValue != null) {
                facetValueList.add(ModelInputFacetAndValue(facetName, facetValue))
            }
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
                facetValues = facetValueList
            )
            this@ModelInputDataCollector.entries.add(entry)
        }

    }




}
