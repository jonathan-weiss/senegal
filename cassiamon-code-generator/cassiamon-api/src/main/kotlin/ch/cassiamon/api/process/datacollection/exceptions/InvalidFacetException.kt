package ch.cassiamon.api.process.datacollection.exceptions

import ch.cassiamon.api.process.schema.ConceptIdentifier
import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName


class InvalidFacetException(val conceptName: ConceptName,
                            val conceptIdentifier: ConceptIdentifier,
                            val facetName: FacetName,
                            reason: String,
    ): SchemaValidationException(
    "The entry with the identifier '${conceptIdentifier.code}' ('${conceptName.name}') " +
            "has an invalid facet configuration for facet '${facetName.name}: $reason'. "
) {

}
