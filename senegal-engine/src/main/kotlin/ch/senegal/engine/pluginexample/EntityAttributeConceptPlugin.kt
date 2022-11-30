package ch.senegal.engine.pluginexample

import ch.senegal.engine.plugin.Concept
import ch.senegal.engine.plugin.ConceptName

class EntityAttributeConceptPlugin: Concept {

    companion object {
        val entityAttributeConceptName: ConceptName = ConceptName("EntityAttribute")
        val entityConceptName: ConceptName = ConceptName("Entity")
    }


    override val conceptName: ConceptName = entityAttributeConceptName

    override val enclosingConceptName: ConceptName = entityConceptName
}
