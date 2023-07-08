package ch.cassiamon.api.process.conceptgraph.exceptions

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName
import ch.cassiamon.api.process.schema.ConceptIdentifier


class CircularDependencyOnTemplateFacetConceptGraphException(val conceptName: ConceptName,
                                                             val conceptIdentifier: ConceptIdentifier,
                                                             val facetName: FacetName,
                                                             reason: String,
    ): ConceptGraphException(
    "The entry with the identifier '${conceptIdentifier.code}' ('${conceptName.name}') " +
            "has an invalid template facet value for facet '${facetName.name}: $reason'. "
) {

}
