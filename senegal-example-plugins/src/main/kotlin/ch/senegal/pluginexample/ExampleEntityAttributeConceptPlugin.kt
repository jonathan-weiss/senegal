package ch.senegal.pluginexample

import ch.senegal.engine.plugin.*
import ch.senegal.plugin.*

object EntityAttributeConceptPlugin: Concept {
    override val conceptName: ConceptName = ConceptName("EntityAttribute")
    override val enclosingConceptName: ConceptName = EntityConceptPlugin.conceptName
    override val conceptDecors: Set<ConceptDecor> = setOf(EntityAttributeNameDecor, EntityAttributeTypeDecor)
}

object EntityAttributeTypeDecor: ConceptDecor {
    override val conceptDecorName: ConceptDecorName = ConceptDecorName("type")
    override val conceptDecorType: DecorType = EnumerationDecorType(listOf(
        DecorTypeEnumerationValue("TEXT"),
        DecorTypeEnumerationValue("NUMBER"),
        DecorTypeEnumerationValue("BOOLEAN")
        )
    )

}

object EntityAttributeNameDecor: ConceptDecor {
    override val conceptDecorName: ConceptDecorName = ConceptDecorName("name")
    override val conceptDecorType: DecorType = TextDecorType
}
