package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNode

typealias MandatoryTextFacetType = String
typealias OptionalTextFacetType = MandatoryTextFacetType?

typealias MandatoryNumberFacetType = Long
typealias OptionalNumberFacetType = MandatoryNumberFacetType?

typealias MandatoryConceptReferenceFacetType = ConceptIdentifier
typealias OptionalConceptReferenceFacetType = MandatoryConceptReferenceFacetType?

typealias MandatoryConceptFacetType = ConceptModelNode
typealias OptionalConceptFacetType = MandatoryConceptFacetType?


