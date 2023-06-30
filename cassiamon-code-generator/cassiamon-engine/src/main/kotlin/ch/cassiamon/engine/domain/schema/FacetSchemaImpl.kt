package ch.cassiamon.engine.domain.schema

import ch.cassiamon.api.FacetName
import ch.cassiamon.api.schema.FacetSchema
import ch.cassiamon.api.schema.FacetTypeEnum

class FacetSchemaImpl(
    override val facetName: FacetName,
    override val facetType: FacetTypeEnum,
    override val mandatory: Boolean,
) : FacetSchema
