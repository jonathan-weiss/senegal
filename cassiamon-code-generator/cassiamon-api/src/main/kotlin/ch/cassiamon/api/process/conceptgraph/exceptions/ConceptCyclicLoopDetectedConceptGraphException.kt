package ch.cassiamon.api.process.conceptgraph.exceptions


class ConceptCyclicLoopDetectedConceptGraphException(entriesDescription: String): ConceptGraphException(
    """The following entries could not be resolved due to a cyclic loop: [$entriesDescription]. 
    """.trimMargin()
) {

}
