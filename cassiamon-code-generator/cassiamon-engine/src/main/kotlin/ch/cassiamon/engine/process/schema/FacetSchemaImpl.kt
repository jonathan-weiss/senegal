package ch.cassiamon.engine.process.schema

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName
import ch.cassiamon.api.process.schema.FacetSchema
import ch.cassiamon.api.process.schema.FacetTypeEnum
import kotlin.reflect.KClass

data class FacetSchemaImpl(
    override val facetName: FacetName,
    override val facetType: FacetTypeEnum,
    override val mandatory: Boolean,
    override val referencingConcept: ConceptName?,
    override val enumerationType: KClass<*>?
) : FacetSchema {
    override fun enumerationValues(): List<Enum<*>> {
        val enumerationType = enumerationType ?: return emptyList()
        return enumerationType.java.enumConstants.filterIsInstance(Enum::class.java)

    }
}
