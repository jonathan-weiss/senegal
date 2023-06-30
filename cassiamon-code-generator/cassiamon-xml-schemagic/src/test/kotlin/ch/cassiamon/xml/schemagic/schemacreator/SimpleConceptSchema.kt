package ch.cassiamon.xml.schemagic.schemacreator

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.schema.ConceptSchema
import ch.cassiamon.api.schema.FacetSchema

class SimpleConceptSchema(
    override val conceptName: ConceptName,
    override val parentConceptName: ConceptName?,
    override val facets: List<FacetSchema>
) : ConceptSchema
