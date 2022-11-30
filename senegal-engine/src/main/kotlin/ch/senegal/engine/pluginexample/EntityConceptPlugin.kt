package ch.senegal.engine.pluginexample

import ch.senegal.engine.plugin.Concept
import ch.senegal.engine.plugin.ConceptName

class EntityConceptPlugin: Concept {

    companion object {
        val entityConceptName: ConceptName = ConceptName("Entity")
    }


    override val conceptName: ConceptName = entityConceptName
}
