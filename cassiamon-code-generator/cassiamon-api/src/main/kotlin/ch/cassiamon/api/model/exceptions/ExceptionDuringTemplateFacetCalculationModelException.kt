package ch.cassiamon.api.model.exceptions

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName
import ch.cassiamon.api.process.schema.ConceptIdentifier


class ExceptionDuringTemplateFacetCalculationModelException(val conceptName: ConceptName,
                                                            val conceptIdentifier: ConceptIdentifier,
                                                            val facetName: FacetName,
                                                            cause: Exception,
    ): ModelException(
    "Exception during the calculation of a template facet. The entry with the identifier '${conceptIdentifier.code}' ('${conceptName.name}') " +
            "throw an exception during the calculation of the facet '${facetName.name}: ${cause.message}'", cause
) {

}
