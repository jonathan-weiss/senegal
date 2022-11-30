package ch.senegal.engine.pluginexample

import ch.senegal.engine.plugin.*

object EntityConceptPlugin: Concept {
    override val conceptName: ConceptName = ConceptName("Entity")
    override val enclosingConceptName: ConceptName? = null
    override val conceptDecors: Set<ConceptDecor> = setOf(EntityNameDecor)
}

object EntityNameDecor: ConceptDecor {
    override val conceptDecorName: ConceptDecorName = ConceptDecorName("name")
}

