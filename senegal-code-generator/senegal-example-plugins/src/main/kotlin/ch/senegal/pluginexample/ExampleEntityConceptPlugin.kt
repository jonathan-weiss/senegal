package ch.senegal.pluginexample

import ch.senegal.plugin.Concept
import ch.senegal.plugin.ConceptName

object EntityConceptPlugin : Concept {
    override val conceptName: ConceptName = ConceptName.of("Entity")
    override val enclosingConceptName: ConceptName? = null
}


