package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNode

typealias MandatoryTextFacetKotlinType = String
typealias OptionalTextFacetKotlinType = MandatoryTextFacetKotlinType?

typealias MandatoryNumberFacetKotlinType = Long
typealias OptionalNumberFacetKotlinType = MandatoryNumberFacetKotlinType?

typealias MandatoryConceptReferenceFacetKotlinType = ConceptIdentifier
typealias OptionalConceptReferenceFacetKotlinType = MandatoryConceptReferenceFacetKotlinType?

typealias MandatoryConceptFacetKotlinType = ConceptModelNode
typealias OptionalConceptFacetKotlinType = MandatoryConceptFacetKotlinType?


// facet types
sealed interface FacetType<C> {
    val isMandatory: Boolean
}
sealed interface MandatoryFacetType<C>: FacetType<C> {
    override val isMandatory: Boolean
        get() = true
}
sealed interface OptionalFacetType<C>: FacetType<C> {
    override val isMandatory: Boolean
        get() = false

}
object MandatoryTextFacetType: MandatoryFacetType<MandatoryTextFacetKotlinType>
object OptionalTextFacetType: OptionalFacetType<OptionalTextFacetKotlinType>
object MandatoryNumberFacetType: MandatoryFacetType<MandatoryNumberFacetKotlinType>
object OptionalNumberFacetType: OptionalFacetType<OptionalNumberFacetKotlinType>

object MandatoryConceptReferenceFacetType: MandatoryFacetType<MandatoryConceptReferenceFacetKotlinType>
object OptionalConceptReferenceFacetType: OptionalFacetType<OptionalConceptReferenceFacetKotlinType>

object MandatoryConceptFacetType: MandatoryFacetType<MandatoryConceptFacetKotlinType>
object OptionalConceptFacetType: OptionalFacetType<OptionalConceptFacetKotlinType>

