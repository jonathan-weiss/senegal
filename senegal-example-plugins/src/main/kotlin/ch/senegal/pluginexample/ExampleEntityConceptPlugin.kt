package ch.senegal.pluginexample

import ch.senegal.engine.plugin.*
import ch.senegal.plugin.*

object EntityConceptPlugin: Concept {
    override val conceptName: ConceptName = ConceptName("Entity")
    override val enclosingConceptName: ConceptName? = null
    override val conceptDecors: Set<ConceptDecor> = setOf(EntityNameDecor)
}

object EntityNameDecor: ConceptDecor {
    override val conceptDecorName: ConceptDecorName = ConceptDecorName("name")
    override val conceptDecorType: DecorType = TextDecorType
}

