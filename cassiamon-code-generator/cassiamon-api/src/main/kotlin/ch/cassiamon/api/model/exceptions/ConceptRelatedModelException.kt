package ch.cassiamon.api.model.exceptions

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.ConceptIdentifier


abstract class ConceptRelatedModelException(val conceptName: ConceptName,
                                            val conceptIdentifier: ConceptIdentifier,
                                            msg: String
    ): ModelException(msg) {

}
