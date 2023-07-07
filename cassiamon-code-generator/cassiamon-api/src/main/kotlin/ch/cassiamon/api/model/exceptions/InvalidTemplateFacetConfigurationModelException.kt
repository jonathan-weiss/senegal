package ch.cassiamon.api.model.exceptions

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.ConceptIdentifier


class InvalidTemplateFacetConfigurationModelException(val conceptName: ConceptName,
                                                      val conceptIdentifier: ConceptIdentifier,
                                                      val facetName: FacetName,
                                                      reason: String,
    ): ModelException(
    "The entry with the identifier '${conceptIdentifier.code}' ('${conceptName.name}') " +
            "has an invalid template facet value for facet '${facetName.name}: $reason'. "
) {

}
