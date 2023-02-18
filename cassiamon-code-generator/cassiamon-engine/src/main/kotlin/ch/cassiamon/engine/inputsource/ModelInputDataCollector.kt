package ch.cassiamon.engine.inputsource

import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.engine.model.types.FacetValue
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.exceptions.DuplicateConceptIdentifierFoundModelException

class ModelInputDataCollector {
    private val entries: MutableMap<ConceptIdentifier, ModelConceptInputDataEntry> = mutableMapOf()

    fun provideModelInputData(): ModelInputData {
        return ModelInputData(entries.values.toList())
    }

    fun attachConceptData(conceptName: ConceptName, conceptIdentifier: ConceptIdentifier, parentConceptIdentifier: ConceptIdentifier?, vararg facetValues: Pair<FacetName, FacetValue>) {
        if(entries.containsKey(conceptIdentifier)) {
            throw DuplicateConceptIdentifierFoundModelException(conceptName, conceptIdentifier)
        }
        entries[conceptIdentifier] =  ModelConceptInputDataEntry(
            conceptName = conceptName,
            conceptIdentifier = conceptIdentifier,
            parentConceptIdentifier = parentConceptIdentifier,
            facetValuesMap = facetValues.toMap()
        )
    }

}
