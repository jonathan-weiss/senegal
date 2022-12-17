package ch.senegal.engine.plugin

import ch.senegal.plugin.*
import ch.senegal.plugin.model.ModelNode
import java.nio.file.Path

object TestPluginFinder {
    fun findAllTestPlugins(): Set<Plugin> {
        return setOf(
            TestEntityConcept, TestEntityAttributeConcept, TestMapperConcept,
            TestEntityPurpose,
            TestKotlinModelPurpose, TestKotlinFieldPurpose,
        )
    }
}

object TestEntityConcept: Concept {
    override val conceptName: ConceptName = ConceptName.of("TestEntity")
    override val enclosingConceptName: ConceptName? = null
}

object TestEntityAttributeConcept: Concept {
    override val conceptName: ConceptName = ConceptName.of("TestEntityAttribute")
    override val enclosingConceptName: ConceptName = TestEntityConcept.conceptName
}

object TestMapperConcept: Concept {
    override val conceptName: ConceptName = ConceptName.of("TestMapperConcept")
    override val enclosingConceptName: ConceptName? = null
}



object TestEntityPurpose: Purpose {
    override val purposeName: PurposeName = PurposeName.of("TestEntity")
    override val facets: Set<Facet> = setOf(TestEntityNameFacet, TestEntityAttributeNameFacet, TestEntityAttributeTypeFacet)
}

object TestEntityNameFacet: Facet {
    override val facetName: FacetName = FacetName.of("Name")
    override val enclosingConceptName: ConceptName = TestEntityConcept.conceptName
    override val facetType: FacetType = TextFacetType
}

object TestEntityAttributeNameFacet: Facet {
    override val facetName: FacetName = FacetName.of("AttributeName")
    override val enclosingConceptName: ConceptName = TestEntityAttributeConcept.conceptName
    override val facetType: FacetType = TextFacetType
}

object TestEntityAttributeTypeFacet: Facet {
    override val facetName: FacetName = FacetName.of("AttributeType")
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
    override fun createTemplateTargets(modelNode: ModelNode, defaultOutputPath: Path): Set<TemplateTarget> {
        val facetValue = modelNode.getFacetValue(purposeName, TestClassnameFacet.facetName)
        val classname = requireNotNull(facetValue).value as String
        return setOf(
            TemplateTarget(
                targetFile = defaultOutputPath.resolve("$classname.kt"),
                templateClasspath = "/ch/senegal/engine/freemarker/template-model-node-template.ftl"
            )
        )
    }
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

