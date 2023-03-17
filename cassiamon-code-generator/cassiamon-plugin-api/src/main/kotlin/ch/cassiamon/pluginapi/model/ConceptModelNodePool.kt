package ch.cassiamon.pluginapi.model

import ch.cassiamon.pluginapi.model.exceptions.ConceptNotFoundModelException
import kotlin.jvm.Throws

interface ConceptModelNodePool {

    fun allConceptModelNodes(): List<ConceptModelNode>
    fun containsConcept(conceptIdentifier: ConceptIdentifier): Boolean
    @Throws(ConceptNotFoundModelException::class)
    fun getConcept(conceptIdentifier: ConceptIdentifier): ConceptModelNode
}
