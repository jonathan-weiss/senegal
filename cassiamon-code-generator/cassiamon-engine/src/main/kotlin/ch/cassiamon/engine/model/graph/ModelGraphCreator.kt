package ch.cassiamon.engine.model.graph

import ch.cassiamon.engine.model.inputsource.ModelConceptInputDataEntry
import ch.cassiamon.engine.model.inputsource.ModelInputData
import ch.cassiamon.engine.model.types.ConceptReferenceFacetValue
import ch.cassiamon.engine.schema.types.CalculatedFacet
import ch.cassiamon.engine.schema.types.Facet
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.engine.schema.types.Schema
import ch.cassiamon.pluginapi.model.exceptions.ConceptCyclicLoopDetectedModelException
import ch.cassiamon.pluginapi.registration.types.TextFacetCalculationFunction

object ModelGraphCreator {

    fun calculateGraph(schema: Schema, modelInputData: ModelInputData): ModelGraph {
        modelInputData.entries.forEach { ModelNodeValidator.validateSingleEntry(schema, it) }

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
                    resolvedEntries[entry.conceptIdentifier] = calculateModelNode(schema, entry, resolvedEntries)
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

        return entry.facetValuesMap
            .filter { it.value is ConceptReferenceFacetValue }
            .map { Pair(it.key, it.value as ConceptReferenceFacetValue) }
            .all { resolvedKeys.contains(it.second.conceptReference)}
    }

    private fun calculateModelNode(
        schema: Schema,
        entry: ModelConceptInputDataEntry,
        otherResolvedEntries: Map<ConceptIdentifier, ModelConceptNode>
    ): ModelConceptNode {

        val schemaConcept = schema.conceptByConceptName(entry.conceptName)

        val facetValues = FacetValuesImpl(entry.facetValuesMap)

        schemaConcept.facets
            .map { calculatedFacet -> calc(calculatedFacet, facetValues) }


        // TODO calculate all the facets
        return ModelConceptNode(
            conceptName = entry.conceptName,
            conceptIdentifier = entry.conceptIdentifier,
            parentConceptIdentifier = entry.parentConceptIdentifier,
            facetValues = facetValues
        )
    }

    private fun calc(schemaFacet: Facet, facetValues: FacetValuesImpl, ) {
        // schemaFacet.facetCalculationFunction
    }
}
