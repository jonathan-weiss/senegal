package ch.cassiamon.engine.model

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.model.ConceptIdentifier
import ch.cassiamon.api.model.ConceptModelNode
import ch.cassiamon.api.model.InputFacetValueAccess

class ReactiveConceptModelNode(
    private val calculationAndValidationData: CalculationAndValidationData,
    override val conceptName: ConceptName,
    override val conceptIdentifier: ConceptIdentifier,
    private val parentConceptIdentifier: ConceptIdentifier?,
    inputFacetValues: InputFacetValueAccess
) : ConceptModelNode {

    override val templateFacetValues = ReactiveConceptModelNodeTemplateFacetValues(
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
