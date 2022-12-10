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
    override val conceptName: ConceptName = ConceptName.of("TestEntity")
    override val enclosingConceptName: ConceptName? = null
}

object TestEntityPurpose: Purpose {
    override val purposeName: PurposeName = PurposeName.of("TestEntity")
    override val facets: Set<Facet> = setOf(TestEntityNameFacet)
}

object TestEntityNameFacet: Facet {
    override val facetName: FacetName = FacetName.of("Name")
    override val enclosingConceptName: ConceptName = TestEntityConcept.conceptName
    override val facetType: FacetType = TextFacetType
}

object TestMapperConcept: Concept {
    override val conceptName: ConceptName = ConceptName.of("TestMapperConcept")
    override val enclosingConceptName: ConceptName? = null
}

object TestEntityAttributeConcept: Concept {
    override val conceptName: ConceptName = ConceptName.of("TestEntityAttribute")
    override val enclosingConceptName: ConceptName = TestEntityConcept.conceptName
}

object TestEntityAttributePurpose: Purpose {
    override val purposeName: PurposeName = PurposeName.of("TestEntityAttribute")
    override val facets: Set<Facet> = setOf(TestEntityAttributeNameFacet, TestEntityAttributeTypeFacet)
}

object TestEntityAttributeNameFacet: Facet {
    override val facetName: FacetName = FacetName.of("Name")
    override val enclosingConceptName: ConceptName = TestEntityAttributeConcept.conceptName
    override val facetType: FacetType = TextFacetType
}

object TestEntityAttributeTypeFacet: Facet {
    override val facetName: FacetName = FacetName.of("Type")
    override val enclosingConceptName: ConceptName = TestEntityAttributeConcept.conceptName
    override val facetType: FacetType = EnumerationFacetType(listOf(
        FacetTypeEnumerationValue("TEXT"),
        FacetTypeEnumerationValue("NUMBER"),
        FacetTypeEnumerationValue("BOOLEAN")
    ))
}


object TestKotlinModelPurpose: Purpose {
    override val purposeName: PurposeName = PurposeName.of("TestKotlinModel")
    override val facets: Set<Facet> = setOf(TestClassnameFacet, TestPackageFacet)
}

object TestClassnameFacet: Facet {
    override val facetName: FacetName = FacetName.of("Classname")
    override val enclosingConceptName: ConceptName = TestEntityConcept.conceptName
    override val facetType: FacetType = TextFacetType
}

object TestPackageFacet: Facet {
    override val facetName: FacetName = FacetName.of("Package")
    override val enclosingConceptName: ConceptName = TestEntityConcept.conceptName
    override val facetType: FacetType = TextFacetType
}

object TestKotlinFieldPurpose: Purpose {
    override val purposeName: PurposeName = PurposeName.of("TestKotlinField")
    override val facets: Set<Facet> = setOf(TestFieldNameFacet, TestFieldTypeFacet)
}

object TestFieldNameFacet: Facet {
    override val facetName: FacetName = FacetName.of("Name")
    override val enclosingConceptName: ConceptName = TestEntityAttributeConcept.conceptName
    override val facetType: FacetType = TextFacetType
}

object TestFieldTypeFacet: Facet {
    override val facetName: FacetName = FacetName.of("Type")
    override val enclosingConceptName: ConceptName = TestEntityAttributeConcept.conceptName
    override val facetType: FacetType = EnumerationFacetType(listOf(
        FacetTypeEnumerationValue("kotlin.String"),
        FacetTypeEnumerationValue("kotlin.Int"),
        FacetTypeEnumerationValue("kotlin.Boolean")
    ))

}

