package ch.cassiamon.pluginapi.model.exceptions

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptIdentifier


class ExceptionDuringTemplateFacetCalculationModelException(val conceptName: ConceptName,
                                                            val conceptIdentifier: ConceptIdentifier,
                                                            val facetName: FacetName,
                                                            cause: Exception,
    ): ModelException(
    "Exception during the calculation of a template facet. The entry with the identifier '${conceptIdentifier.code}' ('${conceptName.name}') " +
            "throw an exception during the calculation of the facet '${facetName.name}: ${cause.message}'", cause
) {

}
