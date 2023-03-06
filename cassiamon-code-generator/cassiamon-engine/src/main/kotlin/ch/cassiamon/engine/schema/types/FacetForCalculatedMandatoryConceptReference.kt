package ch.cassiamon.engine.schema.types

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.NameOfMandatoryConceptReferenceFacet
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNode


class FacetForCalculatedMandatoryConceptReference(
    conceptName: ConceptName,
    facetName: NameOfMandatoryConceptReferenceFacet,
    val facetCalculationFunction: (conceptModelNode: ConceptModelNode) -> ConceptIdentifier,
    val referencedConceptName: ConceptName,
) : CalculatedFacet(
    conceptName = conceptName,
    facetName = facetName,
)
