package ch.cassiamon.api.model.exceptions

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.ConceptIdentifier


abstract class ConceptRelatedModelException(val conceptName: ConceptName,
                                            val conceptIdentifier: ConceptIdentifier,
                                            msg: String
    ): ModelException(msg) {

}
