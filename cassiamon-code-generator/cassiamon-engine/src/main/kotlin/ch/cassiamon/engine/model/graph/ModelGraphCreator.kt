package ch.cassiamon.engine.model.graph

import ch.cassiamon.engine.model.inputsource.ModelConceptInputDataEntry
import ch.cassiamon.engine.model.inputsource.ModelInputData
import ch.cassiamon.engine.model.types.ConceptReferenceFacetValue
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.engine.schema.types.Schema
import ch.cassiamon.pluginapi.model.exceptions.ConceptCyclicLoopDetectedModelException

object ModelGraphCreator {

    fun calculateGraph(schema: Schema, modelInputData: ModelInputData): ModelGraph {
        modelInputData.entries.forEach { validateSingleEntry(schema, it) }

        val resolvedEntries: MutableMap<ConceptIdentifier, ModelConceptNode> = mutableMapOf()
        val unresolvedEntries: MutableList<ModelConceptInputDataEntry> = modelInputData.entries.toMutableList()


        var passes = 0
        do {
            passes++
            println("Run pass $passes")
            val unresolvedEntriesIterator = unresolvedEntries.iterator()
            val unresolvedEntriesSizeBefore = unresolvedEntries.size
            while(unresolvedEntriesIterator.hasNext()) {
                val entry = unresolvedEntriesIterator.next()
                println("Inspecting entry $entry")
                if(allReferencesResolvable(entry, resolvedEntries.keys)) {
                    println("Entry is resolvable: $entry")
                    resolvedEntries[entry.conceptIdentifier] = linkAndCalculateModelNode(entry, resolvedEntries)
                    unresolvedEntriesIterator.remove()
                } else {
                    println("Entry is NOT resolvable: $entry")
                }
            }
        } while (unresolvedEntries.size > 0 && hasNoCyclicDependencies(unresolvedEntries, unresolvedEntriesSizeBefore))

        return ModelGraph(resolvedEntries)
    }

    private fun hasNoCyclicDependencies(unresolvedEntries: List<ModelConceptInputDataEntry>, sizeBefore: Int): Boolean {
        if(unresolvedEntries.size == sizeBefore) {
            throw ConceptCyclicLoopDetectedModelException(unresolvedEntries.toString())
        }
        return true
    }

    private fun allReferencesResolvable(entry: ModelConceptInputDataEntry, resolvedKeys: Set<ConceptIdentifier>): Boolean {
        if(entry.parentConceptIdentifier != null && !resolvedKeys.contains(entry.parentConceptIdentifier)) {
            return false;
        }

        return entry.facetValues
            .filter { it.value is ConceptReferenceFacetValue }
            .map { Pair(it.key, it.value as ConceptReferenceFacetValue) }
            .all { resolvedKeys.contains(it.second.conceptReference)}
    }

    private fun validateSingleEntry(schema: Schema, entry: ModelConceptInputDataEntry) {
        // TODO validate the entry for his own
    }

    private fun linkAndCalculateModelNode(entry: ModelConceptInputDataEntry, otherResolvedEntries: Map<ConceptIdentifier, ModelConceptNode>): ModelConceptNode {
        // TODO link the concept childs and the references together
        // TODO calculate all the facets
        return ModelConceptNode()
    }

}
