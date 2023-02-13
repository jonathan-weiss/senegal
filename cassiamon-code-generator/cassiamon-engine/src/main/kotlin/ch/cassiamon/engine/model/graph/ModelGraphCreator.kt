package ch.cassiamon.engine.model.graph

import ch.cassiamon.engine.model.inputsource.ModelConceptInputDataEntry
import ch.cassiamon.engine.model.inputsource.ModelInputData
import ch.cassiamon.engine.model.types.ConceptIdentifier
import ch.cassiamon.engine.schema.types.Schema

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
                    println("Entry $entry is resolvable")
                    resolvedEntries[entry.conceptIdentifier] = ModelFacetsCalculator.linkAndCalculateModelNode(entry, resolvedEntries)
                    unresolvedEntriesIterator.remove()
                } else {
                    println("Entry $entry is NOT resolvable")
                }
            }
        } while (unresolvedEntries.size > 0 && hasNoCyclicDependencies(unresolvedEntries, unresolvedEntriesSizeBefore))

        return ModelGraph(resolvedEntries)
    }

    private fun hasNoCyclicDependencies(unresolvedEntries: List<ModelConceptInputDataEntry>, sizeBefore: Int): Boolean {
        if(unresolvedEntries.size == sizeBefore) {
            throw RuntimeException("Endless cycle dedected: $unresolvedEntries") // TODO use correct exception
        }
        return true
    }

    private fun allReferencesResolvable(entry: ModelConceptInputDataEntry, resolvedKeys: Set<ConceptIdentifier>): Boolean {
        return true // TODO implement
    }

    private fun validateSingleEntry(schema: Schema, entry: ModelConceptInputDataEntry) {
        // TODO validate the entry for his own
    }
}
