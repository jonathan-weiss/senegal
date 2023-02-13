package ch.cassiamon.engine.model.graph

import ch.cassiamon.engine.model.inputsource.ModelConceptInputDataEntry
import ch.cassiamon.engine.model.types.ConceptIdentifier

object ModelFacetsCalculator {

    fun linkAndCalculateModelNode(entry: ModelConceptInputDataEntry, otherResolvedEntries: Map<ConceptIdentifier, ModelConceptNode>): ModelConceptNode {
        // TODO link the concept childs and the references together
        // TODO calculate all the facets
        return ModelConceptNode()
    }

}
