package ch.cassiamon.pluginapi.factory

import ch.cassiamon.pluginapi.*

object ConceptSchemaFactory {

    fun createConceptSchema(conceptName: ConceptName): ConceptSchema {
        return ConceptSchemaImpl(conceptName)
    }

    private class ConceptSchemaImpl(override val conceptName: ConceptName) : ConceptSchema
}
