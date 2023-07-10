package ch.cassiamon.api.process.datacollection.exceptions

import ch.cassiamon.api.process.schema.ConceptIdentifier
import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName


class WrongTypeForFacetValueException(val concept: ConceptName, val conceptIdentifier: ConceptIdentifier, facetName: FacetName, reason: String): SchemaValidationException(
    "Facet ${facetName.name} for concept identifier '${conceptIdentifier.name}' in concept '${concept.name} has a wrong type: $reason'."
)
