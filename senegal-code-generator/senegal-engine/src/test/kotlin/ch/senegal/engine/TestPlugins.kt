package ch.senegal.engine.plugin

import ch.senegal.plugin.*
import ch.senegal.plugin.factory.FacetFactory
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
    override val facets: Set<Facet> = setOf(testEntityNameFacet, testEntityAttributeNameFacet, testEntityAttributeTypeFacet)
}

val testEntityNameFacet = FacetFactory.StringFacetFactory.createFacet(
    facetName = FacetName.of("Name"),
    enclosingConceptName = TestEntityConcept.conceptName,
)

val testEntityAttributeNameFacet = FacetFactory.StringFacetFactory.createFacet(
    facetName = FacetName.of("AttributeName"),
    enclosingConceptName = TestEntityAttributeConcept.conceptName,
)

val testEntityAttributeTypeFacet = FacetFactory.StringEnumerationFacetFactory.createFacet(
    facetName = FacetName.of("AttributeType"),
    enclosingConceptName = TestEntityAttributeConcept.conceptName,
    enumerationOptions = listOf(
        StringEnumerationFacetOption("TEXT"),
        StringEnumerationFacetOption("NUMBER"),
        StringEnumerationFacetOption("BOOLEAN")
    ),
)



object TestKotlinModelPurpose: Purpose {
    override val purposeName: PurposeName = PurposeName.of("TestKotlinModel")
    override val facets: Set<Facet> = setOf(testClassnameFacet, testPackageFacet)
    override fun createTemplateTargets(modelNode: ModelNode, defaultOutputPath: Path): Set<TemplateTarget> {
        val classname = requireNotNull(modelNode.getStringFacetValue(purposeName, testClassnameFacet.facetName))
        return setOf(
            TemplateTarget(
                targetFile = defaultOutputPath.resolve("$classname.kt"),
                templateClasspath = "/ch/senegal/engine/freemarker/template-model-node-template.ftl"
            )
        )
    }
}

val testClassnameFacet = FacetFactory.StringFacetFactory.createFacet(
    facetName = FacetName.of("Classname"),
    enclosingConceptName = TestEntityConcept.conceptName,
)

val testPackageFacet = FacetFactory.StringFacetFactory.createFacet(
    facetName = FacetName.of("Package"),
    enclosingConceptName = TestEntityConcept.conceptName,
)

object TestKotlinFieldPurpose: Purpose {
    override val purposeName: PurposeName = PurposeName.of("TestKotlinField")
    override val facets: Set<Facet> = setOf(testFieldNameFacet, testFieldLengthFacet, testFieldTypeFacet)
}

val testFieldNameFacet = FacetFactory.StringFacetFactory.createFacet(
    facetName = FacetName.of("Name"),
    enclosingConceptName = TestEntityAttributeConcept.conceptName,
)

val testFieldLengthFacet = FacetFactory.IntegerFacetFactory.createFacet(
    facetName = FacetName.of("Length"),
    enclosingConceptName = TestEntityAttributeConcept.conceptName,
)

val testFieldTypeFacet = FacetFactory.StringEnumerationFacetFactory.createFacet(
    facetName = FacetName.of("Type"),
    enclosingConceptName = TestEntityAttributeConcept.conceptName,
    enumerationOptions = listOf(
        StringEnumerationFacetOption("kotlin.String"),
        StringEnumerationFacetOption("kotlin.Int"),
        StringEnumerationFacetOption("kotlin.Boolean"),
    ),
)

