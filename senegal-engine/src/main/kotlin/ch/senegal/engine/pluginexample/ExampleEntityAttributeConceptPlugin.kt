package ch.senegal.engine.pluginexample

import ch.senegal.engine.plugin.Concept
import ch.senegal.engine.plugin.ConceptDecor
import ch.senegal.engine.plugin.ConceptDecorName
import ch.senegal.engine.plugin.ConceptName

object EntityAttributeConceptPlugin: Concept {
    override val conceptName: ConceptName = ConceptName("EntityAttribute")
    override val enclosingConceptName: ConceptName = EntityConceptPlugin.conceptName
    override val conceptDecors: Set<ConceptDecor> = setOf(EntityAttributeNameDecor, EntityAttributeTypeDecor)
}

object EntityAttributeTypeDecor: ConceptDecor {
    override val conceptDecorName: ConceptDecorName = ConceptDecorName("type")
}

object EntityAttributeNameDecor: ConceptDecor {
    override val conceptDecorName: ConceptDecorName = ConceptDecorName("name")
}
