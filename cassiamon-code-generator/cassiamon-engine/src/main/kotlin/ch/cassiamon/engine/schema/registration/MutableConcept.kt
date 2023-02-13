package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.schema.types.*
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.registration.*
import ch.cassiamon.pluginapi.registration.exceptions.DuplicateFacetNameFoundSchemaException
import ch.cassiamon.pluginapi.registration.exceptions.FacetDependencyNotFoundSchemaException
import ch.cassiamon.pluginapi.registration.types.*


class MutableConcept(override val conceptName: ConceptName,
                     override val parentConceptName: ConceptName?,
                     private val mutableFacets: MutableList<Facet> = mutableListOf()
    ):
    ConceptRegistration, Concept
{

    override val facets: List<Facet>
        get() = mutableFacets.toList()

    override fun addTextFacet(
        facetName: FacetName,
        dependingOnFacets: Set<FacetName>,
        transformationFunction: TextFacetTransformationFunction?
    ) {
        val facet = ManualFacet(
            conceptName = conceptName,
            facetName = facetName,
            facetType = FacetType.TEXT,
            facetDependencies = dependingOnFacets,
            facetTransformationFunction = transformationFunction ?: NoOpTransformationFunctions.noOpTextTransformationFunction
        )
        validateAndAttachFacet(facet)
    }

    override fun addCalculatedTextFacet(
        facetName: FacetName,
        dependingOnFacets: Set<FacetName>,
        calculationFunction: TextFacetCalculationFunction
    ) {
        val facet = CalculatedFacet(
            conceptName = conceptName,
            facetName = facetName,
            facetType = FacetType.TEXT,
            facetDependencies = dependingOnFacets,
            facetCalculationFunction = calculationFunction
        )
        validateAndAttachFacet(facet)
    }

    override fun addIntegerNumberFacet(
        facetName: FacetName,
        dependingOnFacets: Set<FacetName>,
        transformationFunction: IntegerNumberFacetTransformationFunction?
    ) {
        val facet = ManualFacet(
            conceptName = conceptName,
            facetName = facetName,
            facetType = FacetType.INTEGER_NUMBER,
            facetDependencies = dependingOnFacets,
            facetTransformationFunction = transformationFunction ?: NoOpTransformationFunctions.noOpIntegerNumberTransformationFunction
        )
        validateAndAttachFacet(facet)
    }

    override fun addCalculatedIntegerNumberFacet(
        facetName: FacetName,
        dependingOnFacets: Set<FacetName>,
        calculationFunction: IntegerNumberFacetCalculationFunction
    ) {
        val facet = CalculatedFacet(
            conceptName = conceptName,
            facetName = facetName,
            facetType = FacetType.INTEGER_NUMBER,
            facetDependencies = dependingOnFacets,
            facetCalculationFunction = calculationFunction
        )
        validateAndAttachFacet(facet)
    }

    override fun addConceptReferenceFacet(
        facetName: FacetName,
        referencedConcept: ConceptName,
        dependingOnFacets: Set<FacetName>
    ) {
        val facet = ConceptReferenceManualFacet(
            conceptName = conceptName,
            facetName = facetName,
            facetType = FacetType.CONCEPT_REFERENCE,
            facetDependencies = dependingOnFacets,
            facetTransformationFunction = NoOpTransformationFunctions.noOpConceptReferenceTransformationFunction,
            referencedConceptName = referencedConcept
        )
        validateAndAttachFacet(facet)
    }

    override fun addCalculatedConceptReferenceFacet(
        facetName: FacetName,
        referencedConcept: ConceptName,
        dependingOnFacets: Set<FacetName>,
        calculationFunction: ConceptReferenceFacetCalculationFunction
    ) {
        val facet = ConceptReferenceCalculatedFacet(
            conceptName = conceptName,
            facetName = facetName,
            facetType = FacetType.CONCEPT_REFERENCE,
            facetDependencies = dependingOnFacets,
            facetCalculationFunction = calculationFunction,
            referencedConceptName = referencedConcept
        )
        validateAndAttachFacet(facet)
    }

    private fun validateAndAttachFacet(facet: Facet) {
        if(facetExists(facet.facetName)) {
            throw DuplicateFacetNameFoundSchemaException(facet.conceptName, facet.facetName)
        }

        val notFoundFacets = facet.facetDependencies.filter { !facetExists(it) }.toSet()
        if(notFoundFacets.isNotEmpty()) {
            throw FacetDependencyNotFoundSchemaException(facet.conceptName, notFoundFacets)
        }


        mutableFacets.add(facet)
    }

    private fun facetExists(facetName: FacetName): Boolean {
        return mutableFacets.map { it.facetName }.contains(facetName)
    }
}
