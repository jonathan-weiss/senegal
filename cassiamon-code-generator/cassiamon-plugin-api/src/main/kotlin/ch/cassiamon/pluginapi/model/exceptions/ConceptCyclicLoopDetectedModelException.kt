package ch.cassiamon.pluginapi.model.exceptions

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.ConceptIdentifier


class ConceptCyclicLoopDetectedModelException(entriesDescription: String): ModelException(
    """The following entries could not be resolved due to a cyclic loop: [$entriesDescription]. 
    """.trimMargin()
) {

}
