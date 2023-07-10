package ch.cassiamon.api.process.datacollection.exceptions

import ch.cassiamon.api.process.schema.ConceptIdentifier
import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName


class UnknownFacetNameException(val concept: ConceptName, val conceptIdentifier: ConceptIdentifier, facetName: FacetName, reason: String,): SchemaValidationException(
    "Unknown facet name ${facetName.name} found for concept identifier '${conceptIdentifier.code}' in concept '${concept.name}: $reason'."
)
