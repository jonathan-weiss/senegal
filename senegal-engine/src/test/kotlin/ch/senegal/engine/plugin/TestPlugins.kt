package ch.senegal.engine.plugin

import ch.senegal.plugin.*

object TestPluginFinder {
    fun findAllTestPlugins(): Set<Plugin> {
        return setOf(
            TestEntityConcept, TestEntityAttributeConcept,
            TestEntityPurpose, TestEntityAttributePurpose,
            TestKotlinModelPurpose, TestKotlinFieldPurpose,
        )
    }
}

object TestEntityConcept: Concept {
    override val conceptName: ConceptName = ConceptName("TestEntity")
    override val enclosingConceptName: ConceptName? = null
}

object TestEntityPurpose: Purpose {
    override val purposeName: PurposeName = PurposeName("TestEntity")
    override val enclosingConceptName: ConceptName = TestEntityConcept.conceptName
    override val purposeDecors: Set<PurposeDecor> = setOf(TestEntityNamePurposeDecor)
}

object TestEntityNamePurposeDecor: PurposeDecor {
    override val purposeDecorName: PurposeDecorName = PurposeDecorName("Name")
    override val purposeDecorType: DecorType = TextDecorType
}

object TestMapperConcept: Concept {
    override val conceptName: ConceptName = ConceptName("TestMapperConcept")
    override val enclosingConceptName: ConceptName? = null
}

object TestEntityAttributeConcept: Concept {
    override val conceptName: ConceptName = ConceptName("TestEntityAttribute")
    override val enclosingConceptName: ConceptName = TestEntityConcept.conceptName
}

object TestEntityAttributePurpose: Purpose {
    override val purposeName: PurposeName = PurposeName("TestEntityAttribute")
    override val enclosingConceptName: ConceptName = TestEntityAttributeConcept.conceptName
    override val purposeDecors: Set<PurposeDecor> = setOf(TestEntityAttributeTypeDecor)
}

object TestEntityAttributeTypeDecor: PurposeDecor {
    override val purposeDecorName: PurposeDecorName = PurposeDecorName("Type")
    override val purposeDecorType: DecorType = EnumerationDecorType(listOf(
        DecorTypeEnumerationValue("TEXT"),
        DecorTypeEnumerationValue("NUMBER"),
        DecorTypeEnumerationValue("BOOLEAN")
    ))
}


object TestKotlinModelPurpose: Purpose {
    override val purposeName: PurposeName = PurposeName("TestKotlinModel")
    override val enclosingConceptName: ConceptName = TestEntityConcept.conceptName
    override val purposeDecors: Set<PurposeDecor> = setOf(TestClassnameDecor, TestPackageDecor)
}

object TestClassnameDecor: PurposeDecor {
    override val purposeDecorName: PurposeDecorName = PurposeDecorName("Classname")
    override val purposeDecorType: DecorType = TextDecorType
}

object TestPackageDecor: PurposeDecor {
    override val purposeDecorName: PurposeDecorName = PurposeDecorName("Package")
    override val purposeDecorType: DecorType = TextDecorType
}

object TestKotlinFieldPurpose: Purpose {
    override val purposeName: PurposeName = PurposeName("TestKotlinField")
    override val enclosingConceptName: ConceptName = TestEntityAttributeConcept.conceptName
    override val purposeDecors: Set<PurposeDecor> = setOf(TestFieldTypeDecor)
}

object TestFieldTypeDecor: PurposeDecor {
    override val purposeDecorName: PurposeDecorName = PurposeDecorName("Type")
    override val purposeDecorType: DecorType = EnumerationDecorType(listOf(
        DecorTypeEnumerationValue("kotlin.String"),
        DecorTypeEnumerationValue("kotlin.Int"),
        DecorTypeEnumerationValue("kotlin.Boolean")
    ))

}

