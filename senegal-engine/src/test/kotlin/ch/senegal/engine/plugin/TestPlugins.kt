package ch.senegal.engine.plugin

object TestEntityConcept: Concept {
    override val conceptName: ConceptName = ConceptName("TestEntity")
    override val enclosingConceptName: ConceptName? = null
    override val conceptDecors: Set<ConceptDecor> = setOf(TestEntityConceptNameDecor)
}

object TestEntityConceptNameDecor: ConceptDecor {
    override val conceptDecorName: ConceptDecorName = ConceptDecorName("EntityName")
    override val conceptDecorType: DecorType = TextDecorType
}

object TestMapperConcept: Concept {
    override val conceptName: ConceptName = ConceptName("TestMapperConcept")
    override val enclosingConceptName: ConceptName? = null
    override val conceptDecors: Set<ConceptDecor> = emptySet()
}


object TestEntityAttributeConcept: Concept {
    override val conceptName: ConceptName = ConceptName("TestEntityAttribute")
    override val enclosingConceptName: ConceptName = TestEntityConcept.conceptName
    override val conceptDecors: Set<ConceptDecor> = setOf(TestEntityConceptTypeDecor)
}

object TestEntityConceptTypeDecor: ConceptDecor {
    override val conceptDecorName: ConceptDecorName = ConceptDecorName("EntityAttributeType")
    override val conceptDecorType: DecorType = EnumerationDecorType(listOf(
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
    override val purposeDecorName: PurposeDecorName = PurposeDecorName("FieldType")
    override val purposeDecorType: DecorType = EnumerationDecorType(listOf(
        DecorTypeEnumerationValue("kotlin.String"),
        DecorTypeEnumerationValue("kotlin.Int"),
        DecorTypeEnumerationValue("kotlin.Boolean")
    ))

}

