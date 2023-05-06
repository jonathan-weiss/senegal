package ch.cassiamon.api.model.exceptions


class ConceptCyclicLoopDetectedModelException(entriesDescription: String): ModelException(
    """The following entries could not be resolved due to a cyclic loop: [$entriesDescription]. 
    """.trimMargin()
) {

}
