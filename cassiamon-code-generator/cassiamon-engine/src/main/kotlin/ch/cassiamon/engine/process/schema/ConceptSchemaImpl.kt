package ch.cassiamon.engine.process.schema

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.ConceptSchema
import ch.cassiamon.api.process.schema.FacetSchema


class ConceptSchemaImpl(override val conceptName: ConceptName,
                        override val parentConceptName: ConceptName?,
                        override val facets: List<FacetSchema>,
    ): ConceptSchema
