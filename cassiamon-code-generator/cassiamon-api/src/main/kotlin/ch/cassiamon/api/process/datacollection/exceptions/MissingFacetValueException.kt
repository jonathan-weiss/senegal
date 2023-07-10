package ch.cassiamon.api.process.datacollection.exceptions

import ch.cassiamon.api.process.schema.ConceptIdentifier
import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName


class MissingFacetValueException(
    concept: ConceptName,
    conceptIdentifier: ConceptIdentifier,
    facetName: FacetName,
): SchemaValidationException(
    "The entry with the identifier '${conceptIdentifier.name}' ('${concept.name}') " +
    "is missing a value for facet '${facetName.name}'. "
)
