package ch.cassiamon.xml.schemagic.schemacreator

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.ConceptSchema
import ch.cassiamon.api.process.schema.FacetSchema

class SimpleConceptSchema(
    override val conceptName: ConceptName,
    override val conceptClass: Class<*>,
    override val parentConceptName: ConceptName?,
    override val facets: List<FacetSchema>,
    override val minOccurrence: Int = 0,
    override val maxOccurrence: Int = Int.MAX_VALUE
) : ConceptSchema
