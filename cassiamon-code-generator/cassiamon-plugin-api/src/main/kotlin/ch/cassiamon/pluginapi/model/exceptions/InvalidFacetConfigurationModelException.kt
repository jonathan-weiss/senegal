package ch.cassiamon.pluginapi.model.exceptions

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptIdentifier


class InvalidFacetConfigurationModelException(val conceptName: ConceptName,
                                              val conceptIdentifier: ConceptIdentifier,
                                              val facetName: FacetName,
                                              reason: String,
    ): ModelException(
    "The entry with the identifier '${conceptIdentifier.code}' ('${conceptName.name}') " +
            "has an invalid facet configuration for facet '${facetName.name}: $reason'. "
) {

}
