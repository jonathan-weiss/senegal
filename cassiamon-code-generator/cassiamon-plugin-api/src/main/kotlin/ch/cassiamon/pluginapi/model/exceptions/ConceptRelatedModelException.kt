package ch.cassiamon.pluginapi.model.exceptions

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.ConceptIdentifier


abstract class ConceptRelatedModelException(val conceptName: ConceptName,
                                   val conceptIdentifier: ConceptIdentifier,
    msg: String
    ): ModelException(msg) {

}
