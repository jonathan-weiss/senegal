package ch.cassiamon.engine.model.validator

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.ConceptIdentifier
import ch.cassiamon.api.model.exceptions.CircularDependencyOnTemplateFacetModelException

class CircularFacetDependencyDetector {

    private val calculationStepStack: MutableList<CalculationStep> = mutableListOf()
    private val calculationsInProgress: MutableSet<CalculationStep> = mutableSetOf()

    private var circularDependencyDetected = false

    data class CalculationStep (val conceptName: ConceptName,
                               val conceptIdentifier: ConceptIdentifier,
                               val facetName: FacetName)

    fun startFacetCalculation(conceptName: ConceptName,
                              conceptIdentifier: ConceptIdentifier,
                              facetName: FacetName): CalculationStep {
        if(circularDependencyDetected) {
            throw IllegalStateException("A circular dependency has already been detected, can not continue.")
        }

        return initializeCalculationStep(CalculationStep(conceptName, conceptIdentifier, facetName))
    }

    fun endFacetCalculation(calculationStep: CalculationStep) {
        if(circularDependencyDetected) {
            return
        }


        releaseCalculationStep(calculationStep)
    }

    private fun initializeCalculationStep(calculationStep: CalculationStep): CalculationStep {
        checkCircularDependency(calculationStep)
        calculationsInProgress.add(calculationStep)
        calculationStepStack.add(calculationStep)

        return calculationStep
    }

    private fun checkCircularDependency(calculationStep: CalculationStep) {
        if(calculationsInProgress.contains(calculationStep)) {
            circularDependencyDetected = true
            val reason = calculationStepStack.joinToString(separator = " -> ") { step -> step.toString() }
            throw CircularDependencyOnTemplateFacetModelException(
                conceptName = calculationStep.conceptName,
                conceptIdentifier = calculationStep.conceptIdentifier,
                facetName = calculationStep.facetName,
                reason = reason
            )
        }
    }

    private fun releaseCalculationStep(calculationStep: CalculationStep) {
        if(!calculationsInProgress.contains(calculationStep)) {
            throw IllegalStateException("Calculation ended multiple times or never started: $calculationStep")
        }

        if(calculationStepStack.last() != calculationStep) {
            throw IllegalStateException("Wrong order calls for calculation step: $calculationStep")
        }

        calculationsInProgress.remove(calculationStep)
        calculationStepStack.removeLast()
    }
}
