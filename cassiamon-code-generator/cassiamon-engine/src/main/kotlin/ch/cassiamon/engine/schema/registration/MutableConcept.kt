package ch.cassiamon.engine.schema.registration

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.registration.*
import ch.cassiamon.pluginapi.registration.types.ConceptReferenceFacetFunction
import ch.cassiamon.pluginapi.registration.types.IntegerNumberFacetFunction
import ch.cassiamon.pluginapi.registration.types.TextFacetFunction

class MutableConcept(val conceptName: ConceptName,
                     val parentConceptName: ConceptName?,
                     val facets: MutableList<MutableFacet> = mutableListOf()
    ):
    ConceptRegistration
{
    override fun newTextFacet(facetName: FacetName, textFacetRegistration: TextFacetRegistration.() -> Unit) {
        println("ConceptRegistrationDefaultImpl.newTextFacet $facetName")

        val mutableFacet = MutableFacet(
            concept = this,
            facetName = facetName,
            facetType = FacetType.TEXT
        )
        textFacetRegistration(mutableFacet)
        attachFacet(mutableFacet)
    }

    override fun withExistingTextFacet(facetName: FacetName, textFacetRegistration: TextFacetRegistration.() -> Unit) {
        println("ConceptRegistrationDefaultImpl.withExistingTextFacet $facetName")
        val mutableFacet = findExistingFacet(facetName, FacetType.TEXT)
        textFacetRegistration(mutableFacet)
    }

    override fun newIntegerNumberFacet(
        facetName: FacetName,
        integerNumberFacetRegistration: IntegerNumberFacetRegistration.() -> Unit
    ) {
        println("ConceptRegistrationDefaultImpl.newIntegerNumberFacet $facetName")
        val mutableFacet = MutableFacet(
            concept = this,
            facetName = facetName,
            facetType = FacetType.INTEGER_NUMBER,
            )
        integerNumberFacetRegistration(mutableFacet)
        attachFacet(mutableFacet)
    }

    override fun withExistingIntegerNumberFacet(
        facetName: FacetName,
        integerNumberFacetRegistration: IntegerNumberFacetRegistration.() -> Unit
    ) {
        println("ConceptRegistrationDefaultImpl.withExistingIntegerNumberFacet $facetName")
        val mutableFacet = findExistingFacet(facetName, FacetType.TEXT)
        integerNumberFacetRegistration(mutableFacet)
    }

    override fun newConceptReferenceFacet(
        facetName: FacetName,
        referencedConcept: ConceptName,
        conceptReferenceFacetRegistration: ConceptReferenceFacetRegistration.() -> Unit
    ) {
        println("ConceptRegistrationDefaultImpl.newConceptReferenceFacet $facetName -> $referencedConcept")
        val mutableFacet = MutableFacet(
            concept = this,
            facetName = facetName,
            facetType = FacetType.CONCEPT_REFERENCE,
            referencedFacetConcept = referencedConcept)
        conceptReferenceFacetRegistration(mutableFacet)
        attachFacet(mutableFacet)

    }

    override fun withExistingConceptReferenceFacet(
        facetName: FacetName,
        referencedConcept: ConceptName,
        conceptReferenceFacetRegistration: ConceptReferenceFacetRegistration.() -> Unit
    ) {
        println("ConceptRegistrationDefaultImpl.withExistingConceptReferenceFacet $facetName -> $referencedConcept")
        val mutableFacet = findExistingFacet(facetName, FacetType.CONCEPT_REFERENCE)
        conceptReferenceFacetRegistration(mutableFacet)
    }

    private fun attachFacet(mutableFacet: MutableFacet) {
        facets.add(mutableFacet)
    }

    private fun findExistingFacet(facetName: FacetName, type: FacetType): MutableFacet {
        throw RuntimeException("Facet with name ${facetName.name} not found.")
    }


}
