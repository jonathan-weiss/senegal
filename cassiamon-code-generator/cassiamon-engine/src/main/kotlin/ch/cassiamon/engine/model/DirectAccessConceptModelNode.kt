package ch.cassiamon.engine.model

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNode
import ch.cassiamon.pluginapi.model.ConceptModelNodeTemplateFacetValues
import ch.cassiamon.pluginapi.model.InputFacetValueAccess

class DirectAccessConceptModelNode(
    private val calculationAndValidationData: CalculationAndValidationData,
    override val conceptName: ConceptName,
    override val conceptIdentifier: ConceptIdentifier,
    private val parentConceptIdentifier: ConceptIdentifier?,
    inputFacetValues: InputFacetValueAccess
) : ConceptModelNode {

    override val templateFacetValues = DirectAccessConceptModelNodeTemplateFacetValues(
        conceptModelNode = this,
        calculationAndValidationData = calculationAndValidationData,
        inputFacetValues = inputFacetValues,
    )
    override fun parent(): ConceptModelNode? {
        return parentConceptIdentifier?.let { calculationAndValidationData.conceptModelNodePool.getConcept(it) }
    }

    override fun allChildren(): List<ConceptModelNode> {
        return calculationAndValidationData.conceptModelNodePool.allConceptModelNodes().filter { it.parent()?.conceptIdentifier == conceptIdentifier }
    }

    override fun children(conceptName: ConceptName): List<ConceptModelNode> {
        return allChildren().filter { it.conceptName == conceptName }
    }


    override fun get(key: String): Any? {
        TODO("Not yet implemented")
    }


}
