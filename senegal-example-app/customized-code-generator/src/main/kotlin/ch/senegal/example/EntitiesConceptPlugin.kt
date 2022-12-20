package ch.senegal.example

import ch.senegal.plugin.Concept
import ch.senegal.plugin.ConceptName

object EntitiesConceptPlugin : Concept {
    override val conceptName: ConceptName = ConceptName.of("Entities")
    override val enclosingConceptName: ConceptName? = null
}


