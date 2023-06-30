package ch.cassiamon.engine.domain

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.annotations.ChildConcepts
import ch.cassiamon.api.annotations.Concept
import ch.cassiamon.api.annotations.InputFacet
import ch.cassiamon.api.annotations.Schema
import ch.cassiamon.engine.domain.schema.MalformedSchemaException
import ch.cassiamon.engine.domain.schema.SchemaCreator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SchemaCreatorTest {
    interface UnannotatedSchemaDefinitionClass

    @Test
    fun `test unannotated class should throw an exception`() {
        Assertions.assertThrows(MalformedSchemaException::class.java) {
            SchemaCreator.createSchemaFromSchemaDefinitionClass(UnannotatedSchemaDefinitionClass::class.java)
        }
    }

    @Schema
    class NonInterfaceSchemaDefinitionClass

    @Test
    fun `test non-interface class should throw an exception`() {
        Assertions.assertThrows(MalformedSchemaException::class.java) {
            SchemaCreator.createSchemaFromSchemaDefinitionClass(NonInterfaceSchemaDefinitionClass::class.java)
        }
    }

    @Schema
    interface EmptySchemaDefinitionClass

    @Test
    fun `test create an empty schema from an empty schema interface`() {
        val schema = SchemaCreator.createSchemaFromSchemaDefinitionClass(EmptySchemaDefinitionClass::class.java)
        Assertions.assertEquals(0, schema.numberOfConcepts())
    }

    @Schema
    interface SimpleSchemaDefinitionClass {
        @ChildConcepts(FooConcept::class)
        fun getChildrenConceptsOfFoo(): List<FooConcept>

    }

    @Concept("FooConcept")
    interface FooConcept {
        @InputFacet("Bar")
        fun getBarFacet(): String
    }

    @Test
    fun `test with single root concept`() {
        val schema = SchemaCreator.createSchemaFromSchemaDefinitionClass(SimpleSchemaDefinitionClass::class.java)
        Assertions.assertEquals(1, schema.numberOfConcepts())
        val fooConcept = schema.conceptByConceptName(ConceptName.of("FooConcept"))
        Assertions.assertEquals(ConceptName.of("FooConcept"), fooConcept.conceptName)
        Assertions.assertNull(fooConcept.parentConceptName)
        Assertions.assertTrue(fooConcept.facetNames.contains(FacetName.of("Bar")))
    }

    @Schema
    interface SimpleSchemaDefinitionClassWithUnannotatedMethods {
        @ChildConcepts(FooConcept::class)
        fun getChildrenConceptsOfFoo(): List<FooConcept>

        fun getOtherChildren(): List<FooConcept>
    }

    @Test
    fun `test schema with unannotated methods should throw an exception`() {
        Assertions.assertThrows(MalformedSchemaException::class.java) {
            SchemaCreator.createSchemaFromSchemaDefinitionClass(SimpleSchemaDefinitionClassWithUnannotatedMethods::class.java)
        }
    }

    @Schema
    interface SimpleSchemaDefinitionClassButNotAList {
        @ChildConcepts(FooConcept::class)
        fun getChildrenConceptsOfFoo(): FooConcept

    }

    @Test
    fun `test schema with a child concept annotation not returning a List should throw an exception`() {
        Assertions.assertThrows(MalformedSchemaException::class.java) {
            SchemaCreator.createSchemaFromSchemaDefinitionClass(SimpleSchemaDefinitionClassButNotAList::class.java)
        }
    }

    @Schema
    interface SimpleSchemaDefinitionClassButNotAListOfConcept {
        @ChildConcepts(NotConcept::class)
        fun getChildrenConceptsOfFoo(): List<NotConcept>

    }

    interface NotConcept
    @Test
    fun `test schema with a method not returning an interface annotated with @Concept should throw an exception`() {
        Assertions.assertThrows(MalformedSchemaException::class.java) {
            SchemaCreator.createSchemaFromSchemaDefinitionClass(SimpleSchemaDefinitionClassButNotAListOfConcept::class.java)
        }
    }

    @Schema
    interface SchemaConceptWithDuplicateFacetsDefinitionClass {
        @ChildConcepts(ConceptWithDuplicateFacets::class)
        fun getChildrenConcepts(): List<ConceptWithDuplicateFacets>

    }

    @Concept("ConceptWithDuplicateFacets")
    interface ConceptWithDuplicateFacets {
        @InputFacet("MyDuplicateFacet")
        fun getDuplicateFacet(): String

        @InputFacet("MyDuplicateFacet")
        fun getSecondDuplicateFacet(): String
    }

    @Test
    fun `test concept with duplicate facet names should throw an exception`() {
        Assertions.assertThrows(MalformedSchemaException::class.java) {
            SchemaCreator.createSchemaFromSchemaDefinitionClass(SchemaConceptWithDuplicateFacetsDefinitionClass::class.java)
        }
    }

    @Schema
    interface NestedSchemaDefinitionClass {
        @ChildConcepts(SubConcept1::class)
        fun getChildrenConcepts1(): List<SubConcept1>

        @ChildConcepts(SubConcept2::class)
        fun getChildrenConcepts2(): List<SubConcept2>

    }

    @Concept("SubConcept1")
    interface SubConcept1 {
        @ChildConcepts(SubSubConcept1::class)
        fun getChildrenConceptsOfChild(): List<SubSubConcept1>

        @InputFacet("SubConcept1Facet1")
        fun getSubConcept1Facet1(): String

        @InputFacet("SubConcept1Facet2")
        fun getSubConcept1Facet2(): String


    }

    @Concept("SubSubConcept1")
    interface SubSubConcept1 {
        @ChildConcepts(SubSubSubConcept1::class)
        fun getChildrenConceptsOfChild(): List<SubSubSubConcept1>

    }


    @Concept("SubSubSubConcept1")
    interface SubSubSubConcept1

    @Concept("SubConcept2")
    interface SubConcept2 {
        @ChildConcepts(SubSubConcept2::class)
        fun getChildrenConceptsOfChild(): List<SubSubConcept2>

        @InputFacet("SubConcept2Facet")
        fun getSubConcept2Facet(): String


    }

    @Concept("SubSubConcept2")
    interface SubSubConcept2

    @Test
    fun `test schema with two nested concepts`() {
        val schema = SchemaCreator.createSchemaFromSchemaDefinitionClass(NestedSchemaDefinitionClass::class.java)
        Assertions.assertEquals(5, schema.numberOfConcepts())

        val subConcept1 = schema.conceptByConceptName(ConceptName.of("SubConcept1"))
        Assertions.assertEquals(ConceptName.of("SubConcept1"), subConcept1.conceptName)
        Assertions.assertNull(subConcept1.parentConceptName)
        Assertions.assertEquals(2, subConcept1.facetNames.size)
        Assertions.assertTrue(subConcept1.facetNames.contains(FacetName.of("SubConcept1Facet1")))
        Assertions.assertTrue(subConcept1.facetNames.contains(FacetName.of("SubConcept1Facet2")))


        val subSubConcept1 = schema.conceptByConceptName(ConceptName.of("SubSubConcept1"))
        Assertions.assertEquals(ConceptName.of("SubSubConcept1"), subSubConcept1.conceptName)
        Assertions.assertEquals(ConceptName.of("SubConcept1"), subSubConcept1.parentConceptName)
        Assertions.assertEquals(0, subSubConcept1.facetNames.size)

        val subSubSubConcept1 = schema.conceptByConceptName(ConceptName.of("SubSubSubConcept1"))
        Assertions.assertEquals(ConceptName.of("SubSubSubConcept1"), subSubSubConcept1.conceptName)
        Assertions.assertEquals(ConceptName.of("SubSubConcept1"), subSubSubConcept1.parentConceptName)
        Assertions.assertEquals(0, subSubSubConcept1.facetNames.size)


        val subConcept2 = schema.conceptByConceptName(ConceptName.of("SubConcept2"))
        Assertions.assertEquals(ConceptName.of("SubConcept2"), subConcept2.conceptName)
        Assertions.assertNull(subConcept2.parentConceptName)
        Assertions.assertEquals(1, subConcept2.facetNames.size)
        Assertions.assertTrue(subConcept2.facetNames.contains(FacetName.of("SubConcept2Facet")))

        val subSubConcept2 = schema.conceptByConceptName(ConceptName.of("SubSubConcept2"))
        Assertions.assertEquals(ConceptName.of("SubSubConcept2"), subSubConcept2.conceptName)
        Assertions.assertEquals(ConceptName.of("SubConcept2"), subSubConcept2.parentConceptName)
        Assertions.assertEquals(0, subSubConcept2.facetNames.size)
    }

    @Schema
    interface DirectCyclicSchemaDefinitionClass {
        @ChildConcepts(DirectCyclicConcept::class)
        fun getCyclicChildrenConcept(): List<DirectCyclicConcept>

    }

    @Concept("DirectCyclicConcept")
    interface DirectCyclicConcept {
        @ChildConcepts(DirectCyclicConcept::class)
        fun getChildrenConcepts(): List<DirectCyclicConcept>

    }

    @Test
    fun `test schema with root schema inside a child concept throw exception`() {
        Assertions.assertThrows(MalformedSchemaException::class.java) {
            SchemaCreator.createSchemaFromSchemaDefinitionClass(DirectCyclicSchemaDefinitionClass::class.java)
        }
    }


    @Schema
    interface IndirectCyclicSchemaDefinitionClass {
        @ChildConcepts(IndirectCyclicConcept::class)
        fun getCyclicChildrenConcept(): List<IndirectCyclicConcept>

    }

    @Concept("IndirectCyclicConcept")
    interface IndirectCyclicConcept {
        @ChildConcepts(IndirectCyclicSubConcept::class)
        fun getSubChildrenConcepts(): List<IndirectCyclicSubConcept>

    }

    @Concept("IndirectCyclicSubConcept")
    interface IndirectCyclicSubConcept {
        @ChildConcepts(IndirectCyclicConcept::class)
        fun getChildrenConcepts(): List<IndirectCyclicConcept>

    }

    @Test
    fun `test multiple concepts in cyclic hierarchy throws exception`() {
        Assertions.assertThrows(MalformedSchemaException::class.java) {
            SchemaCreator.createSchemaFromSchemaDefinitionClass(IndirectCyclicSchemaDefinitionClass::class.java)
        }
    }

    @Schema
    interface SchemaDefinitionWithDuplicateRootConceptClass {
        @ChildConcepts(DuplicateRootConcept::class)
        fun getChildrenConcepts(): List<DuplicateRootConcept>

        @ChildConcepts(DuplicateRootConcept::class)
        fun getAnotherChildrenConcepts(): List<DuplicateRootConcept>

    }

    @Concept("DuplicateRootConcept")
    interface DuplicateRootConcept

    @Test
    fun `test with duplicate root concept`() {
        val schema = SchemaCreator.createSchemaFromSchemaDefinitionClass(SchemaDefinitionWithDuplicateRootConceptClass::class.java)
        Assertions.assertEquals(1, schema.numberOfConcepts())
        val fooConcept = schema.conceptByConceptName(ConceptName.of("DuplicateRootConcept"))
        Assertions.assertEquals(ConceptName.of("DuplicateRootConcept"), fooConcept.conceptName)
        Assertions.assertNull(fooConcept.parentConceptName)
    }


}
