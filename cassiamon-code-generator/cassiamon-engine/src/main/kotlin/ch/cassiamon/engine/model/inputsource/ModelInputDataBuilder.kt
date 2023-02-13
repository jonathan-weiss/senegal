package ch.cassiamon.engine.model.inputsource

import ch.cassiamon.engine.model.types.ConceptIdentifier
import ch.cassiamon.engine.model.types.FacetValue
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName

class ModelInputDataBuilder {
    private val entries: MutableMap<ConceptIdentifier, ModelConceptInputDataEntry> = mutableMapOf()

    fun provideModelInputData(): ModelInputData {
        return ModelInputData(entries.values.toList())
    }

    fun attachConceptData(conceptName: ConceptName, conceptIdentifier: ConceptIdentifier, vararg facetValues: Pair<FacetName, FacetValue>) {
        // TODO check if conceptIdentifier already exists
        entries[conceptIdentifier] =  ModelConceptInputDataEntry(
            conceptName = conceptName,
            conceptIdentifier = conceptIdentifier,
            facetValues = facetValues.toMap()
        )
    }

}
