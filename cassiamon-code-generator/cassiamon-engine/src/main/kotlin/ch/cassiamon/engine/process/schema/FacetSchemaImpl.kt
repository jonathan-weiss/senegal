package ch.cassiamon.engine.process.schema

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName
import ch.cassiamon.api.process.schema.FacetSchema
import ch.cassiamon.api.process.schema.FacetTypeEnum

class FacetSchemaImpl(
    override val facetName: FacetName,
    override val facetType: FacetTypeEnum,
    override val mandatory: Boolean,
    override val referencingConcept: ConceptName?
) : FacetSchema
