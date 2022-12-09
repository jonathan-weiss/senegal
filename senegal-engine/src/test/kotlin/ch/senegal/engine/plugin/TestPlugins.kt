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
    override val purposeDecors: Set<PurposeDecor> = setOf(TestEntityNamePurposeDecor)
}

object TestEntityNamePurposeDecor: PurposeDecor {
    override val decorName: DecorName = DecorName("Name")
    override val enclosingConceptName: ConceptName = TestEntityConcept.conceptName
    override val decorType: DecorType = TextDecorType
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
    override val purposeDecors: Set<PurposeDecor> = setOf(TestEntityAttributeNameDecor, TestEntityAttributeTypeDecor)
}

object TestEntityAttributeNameDecor: PurposeDecor {
    override val decorName: DecorName = DecorName("Name")
    override val enclosingConceptName: ConceptName = TestEntityAttributeConcept.conceptName
    override val decorType: DecorType = TextDecorType
}

object TestEntityAttributeTypeDecor: PurposeDecor {
    override val decorName: DecorName = DecorName("Type")
    override val enclosingConceptName: ConceptName = TestEntityAttributeConcept.conceptName
    override val decorType: DecorType = EnumerationDecorType(listOf(
        DecorTypeEnumerationValue("TEXT"),
        DecorTypeEnumerationValue("NUMBER"),
        DecorTypeEnumerationValue("BOOLEAN")
    ))
}


object TestKotlinModelPurpose: Purpose {
    override val purposeName: PurposeName = PurposeName("TestKotlinModel")
    override val purposeDecors: Set<PurposeDecor> = setOf(TestClassnameDecor, TestPackageDecor)
}

object TestClassnameDecor: PurposeDecor {
    override val decorName: DecorName = DecorName("Classname")
    override val enclosingConceptName: ConceptName = TestEntityConcept.conceptName
    override val decorType: DecorType = TextDecorType
}

object TestPackageDecor: PurposeDecor {
    override val decorName: DecorName = DecorName("Package")
    override val enclosingConceptName: ConceptName = TestEntityConcept.conceptName
    override val decorType: DecorType = TextDecorType
}

object TestKotlinFieldPurpose: Purpose {
    override val purposeName: PurposeName = PurposeName("TestKotlinField")
    override val purposeDecors: Set<PurposeDecor> = setOf(TestFieldTypeDecor)
}

object TestFieldTypeDecor: PurposeDecor {
    override val decorName: DecorName = DecorName("Type")
    override val enclosingConceptName: ConceptName = TestEntityAttributeConcept.conceptName
    override val decorType: DecorType = EnumerationDecorType(listOf(
        DecorTypeEnumerationValue("kotlin.String"),
        DecorTypeEnumerationValue("kotlin.Int"),
        DecorTypeEnumerationValue("kotlin.Boolean")
    ))

}

