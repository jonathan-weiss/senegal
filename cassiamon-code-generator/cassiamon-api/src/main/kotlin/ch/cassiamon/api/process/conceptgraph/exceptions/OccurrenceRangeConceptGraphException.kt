package ch.cassiamon.api.process.conceptgraph.exceptions

import ch.cassiamon.api.process.schema.ConceptName


class OccurrenceRangeConceptGraphException(
    val concept: ConceptName,
    minOccurrence: Int,
    maxOccurrence: Int,
    conceptDescriptions: List<String>, ): ConceptGraphException(
    "Wrong number of '${concept.name}' concepts found. " +
            "Expected range is ${minOccurrence}..$maxOccurrence, but found ${conceptDescriptions.size}. " +
            "Concepts: $conceptDescriptions"
)
