package ch.cassiamon.engine.process.schema

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName
import ch.cassiamon.api.process.schema.FacetTypeEnum
import ch.cassiamon.api.process.schema.MalformedSchemaException
import ch.cassiamon.api.process.schema.annotations.ChildConcepts
import ch.cassiamon.api.process.schema.annotations.Concept
import ch.cassiamon.api.process.schema.annotations.Facet
import ch.cassiamon.api.process.schema.annotations.Schema
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SchemaCreatorFacetTest {

    @Schema
    interface SchemaDefinitionWithDifferentTypes {
        @ChildConcepts(DifferentTypeConcept::class)
        fun getChildrenConcepts(): List<DifferentTypeConcept>

    }

    @Concept("DifferentTypeConcept")
    interface DifferentTypeConcept {

        @Facet("TextFacet")
        fun getTextFacet(): String

        @Facet("NumberFacet")
        fun getNumberFacet(): Int

        @Facet("BooleanFacet")
        fun getBooleanFacet(): Boolean

    }

    @Test
    fun `test valid concept with different types`() {
        val textFacetName = FacetName.of("TextFacet")
        val numberFacetName = FacetName.of("NumberFacet")
        val booleanFacetName = FacetName.of("BooleanFacet")
        val schema = SchemaCreator.createSchemaFromSchemaDefinitionClass(SchemaDefinitionWithDifferentTypes::class.java)
        Assertions.assertEquals(1, schema.numberOfConcepts())
        val concept = schema.conceptByConceptName(ConceptName.of("DifferentTypeConcept"))


        Assertions.assertTrue(concept.hasFacet(textFacetName))
        Assertions.assertTrue(concept.hasFacet(numberFacetName))
        Assertions.assertTrue(concept.hasFacet(booleanFacetName))

        Assertions.assertEquals(FacetTypeEnum.TEXT, concept.facetByName(textFacetName).facetType)
        Assertions.assertEquals(true, concept.facetByName(textFacetName).mandatory)

        Assertions.assertEquals(FacetTypeEnum.NUMBER, concept.facetByName(numberFacetName).facetType)
        Assertions.assertEquals(true, concept.facetByName(numberFacetName).mandatory)

        Assertions.assertEquals(FacetTypeEnum.BOOLEAN, concept.facetByName(booleanFacetName).facetType)
        Assertions.assertEquals(true, concept.facetByName(booleanFacetName).mandatory)
    }

    @Schema
    interface SchemaConceptWithDuplicateFacetsDefinitionClass {
        @ChildConcepts(ConceptWithWrongFacetType::class)
        fun getChildrenConcepts(): List<ConceptWithWrongFacetType>

    }

    @Concept("ConceptWithDuplicateFacets")
    interface ConceptWithDuplicateFacets {
        @Facet("MyDuplicateFacet")
        fun getDuplicateFacet(): String

        @Facet("MyDuplicateFacet")
        fun getSecondDuplicateFacet(): String
    }

    @Test
    fun `test concept with duplicate facet names should throw an exception`() {
        Assertions.assertThrows(MalformedSchemaException::class.java) {
            SchemaCreator.createSchemaFromSchemaDefinitionClass(SchemaConceptWithDuplicateFacetsDefinitionClass::class.java)
        }
    }

    @Schema
    interface SchemaConceptWithWrongFacetTypeDefinitionClass {
        @ChildConcepts(ConceptWithWrongFacetType::class)
        fun getChildrenConcepts(): List<ConceptWithWrongFacetType>

    }

    @Concept("ConceptWithWrongFacetType")
    interface ConceptWithWrongFacetType {
        @Facet("MyFacetWithWrongType")
        fun getWrongTypeFacet(): Double
    }

    @Test
    fun `test concept with wrong facet type should throw an exception`() {
        Assertions.assertThrows(MalformedSchemaException::class.java) {
            SchemaCreator.createSchemaFromSchemaDefinitionClass(SchemaConceptWithWrongFacetTypeDefinitionClass::class.java)
        }
    }

    @Schema
    interface SchemaConceptWithMandatoryFacetsAndOptionalFacetsDefinitionClass {
        @ChildConcepts(ConceptWithMandatoryAndOptionalFacets::class)
        fun getChildrenConcepts(): List<ConceptWithMandatoryAndOptionalFacets>

    }

    @Concept("ConceptWithMandatoryAndOptionalFacets")
    interface ConceptWithMandatoryAndOptionalFacets {
        @Facet(facetName = "DefaultMandatoryFacet")
        fun getDefaultMandatoryFacet(): Boolean

        @Facet(facetName = "MandatoryFacet", mandatory = true)
        fun getMandatoryFacet(): Int

        @Facet(facetName = "OptionalFacet", mandatory = false)
        fun getOptionalFacet(): Boolean
    }

    @Test
    fun `test concept with mandatory and optional facets`() {
        val defaultMandatoryFacetName = FacetName.of("DefaultMandatoryFacet")
        val mandatoryFacetName = FacetName.of("MandatoryFacet")
        val optionalFacetName = FacetName.of("OptionalFacet")
        val schema = SchemaCreator.createSchemaFromSchemaDefinitionClass(SchemaConceptWithMandatoryFacetsAndOptionalFacetsDefinitionClass::class.java)
        Assertions.assertEquals(1, schema.numberOfConcepts())
        val concept = schema.conceptByConceptName(ConceptName.of("ConceptWithMandatoryAndOptionalFacets"))

        Assertions.assertTrue(concept.hasFacet(defaultMandatoryFacetName))
        Assertions.assertTrue(concept.hasFacet(mandatoryFacetName))
        Assertions.assertTrue(concept.hasFacet(optionalFacetName))

        Assertions.assertEquals(true, concept.facetByName(defaultMandatoryFacetName).mandatory)
        Assertions.assertEquals(true, concept.facetByName(mandatoryFacetName).mandatory)
        Assertions.assertEquals(false, concept.facetByName(optionalFacetName).mandatory)
    }

}
