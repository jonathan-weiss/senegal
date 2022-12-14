package ch.senegal.pluginexample

import ch.senegal.plugin.Concept
import ch.senegal.plugin.ConceptName

object EntityAttributeConceptPlugin : Concept {
    override val conceptName: ConceptName = ConceptName.of("EntityAttribute")
    override val enclosingConceptName: ConceptName = EntityConceptPlugin.conceptName
}
