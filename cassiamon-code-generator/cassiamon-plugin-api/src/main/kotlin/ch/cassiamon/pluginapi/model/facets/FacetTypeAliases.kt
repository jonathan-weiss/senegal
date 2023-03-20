package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNode

typealias TextFacetKotlinType = String
typealias NumberFacetKotlinType = Long
typealias ConceptReferenceFacetKotlinType = ConceptIdentifier
typealias ConceptFacetKotlinType = ConceptModelNode


// facet types
sealed interface FacetType<C> {
    val isMandatory: Boolean
}
sealed interface MandatoryFacetType<C: Any>: FacetType<C> {
    override val isMandatory: Boolean
        get() = true
}
sealed interface OptionalFacetType<C: Any?>: FacetType<C> {
    override val isMandatory: Boolean
        get() = false

}
object MandatoryTextFacetType: MandatoryFacetType<TextFacetKotlinType>
object OptionalTextFacetType: OptionalFacetType<TextFacetKotlinType?>
object MandatoryNumberFacetType: MandatoryFacetType<NumberFacetKotlinType>
object OptionalNumberFacetType: OptionalFacetType<NumberFacetKotlinType?>

object MandatoryConceptReferenceFacetType: MandatoryFacetType<ConceptReferenceFacetKotlinType>
object OptionalConceptReferenceFacetType: OptionalFacetType<ConceptReferenceFacetKotlinType?>

object MandatoryConceptFacetType: MandatoryFacetType<ConceptFacetKotlinType>
object OptionalConceptFacetType: OptionalFacetType<ConceptFacetKotlinType?>

