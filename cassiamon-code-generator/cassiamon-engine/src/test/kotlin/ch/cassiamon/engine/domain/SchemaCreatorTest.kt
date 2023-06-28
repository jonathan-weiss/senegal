package ch.cassiamon.engine.domain

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.annotations.ChildConcepts
import ch.cassiamon.api.annotations.Concept
import ch.cassiamon.api.annotations.Schema
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SchemaCreatorTest {
    interface UnannotatedSchemaDefinitionClass

    @Test
    fun createSchemaFromUnannotatedClassShouldThrowException() {
        Assertions.assertThrows(MalformedSchemaException::class.java) {
            SchemaCreator.createSchemaFromSchemaDefinitionClass(UnannotatedSchemaDefinitionClass::class.java)
        }
    }

    @Schema
    class NonInterfaceSchemaDefinitionClass

    @Test
    fun createSchemaFromNonInterfaceClassShouldThrowException() {
        Assertions.assertThrows(MalformedSchemaException::class.java) {
            SchemaCreator.createSchemaFromSchemaDefinitionClass(NonInterfaceSchemaDefinitionClass::class.java)
        }
    }

    @Schema
    interface EmptySchemaDefinitionClass

    @Test
    fun createSchemaFromEmptyClass() {
        val schema = SchemaCreator.createSchemaFromSchemaDefinitionClass(EmptySchemaDefinitionClass::class.java)
        Assertions.assertEquals(0, schema.numberOfConcepts())
    }

    @Schema
    interface SimpleSchemaDefinitionClass {
        @ChildConcepts(FooConcept::class)
        fun getChildrenConceptsOfFoo(): List<FooConcept>

    }

    @Concept("FooConcept")
    interface FooConcept

    @Test
    fun createValidSchemaFromSimpleSchema() {
        val schema = SchemaCreator.createSchemaFromSchemaDefinitionClass(SimpleSchemaDefinitionClass::class.java)
        Assertions.assertEquals(1, schema.numberOfConcepts())
        val fooConcept = schema.conceptByConceptName(ConceptName.of("FooConcept"))
        Assertions.assertEquals(ConceptName.of("FooConcept"), fooConcept.conceptName)
        Assertions.assertNull(fooConcept.parentConceptName)
    }


    @Schema
    interface SimpleSchemaDefinitionClassWithUnannotatedMethods {
        @ChildConcepts(FooConcept::class)
        fun getChildrenConceptsOfFoo(): List<FooConcept>

        fun getOtherChildren(): List<FooConcept>
    }

    @Test
    fun createSchemaWithUnannotatedMethods() {
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
    fun createSchemaWithMethodNotReturningAList() {
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
    fun createSchemaWithMethodReturningNotAConceptClass() {
        Assertions.assertThrows(MalformedSchemaException::class.java) {
            SchemaCreator.createSchemaFromSchemaDefinitionClass(SimpleSchemaDefinitionClassButNotAListOfConcept::class.java)
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

    }

    @Concept("SubSubConcept1")
    interface SubSubConcept1

    @Concept("SubConcept2")
    interface SubConcept2 {
        @ChildConcepts(SubSubConcept2::class)
        fun getChildrenConceptsOfChild(): List<SubSubConcept2>

    }

    @Concept("SubSubConcept2")
    interface SubSubConcept2

    @Test
    fun createValidSchemaFromNestedSchema() {
        val schema = SchemaCreator.createSchemaFromSchemaDefinitionClass(NestedSchemaDefinitionClass::class.java)
        Assertions.assertEquals(4, schema.numberOfConcepts())

        val subConcept1 = schema.conceptByConceptName(ConceptName.of("SubConcept1"))
        Assertions.assertEquals(ConceptName.of("SubConcept1"), subConcept1.conceptName)
        Assertions.assertNull(subConcept1.parentConceptName)

        val subSubConcept1 = schema.conceptByConceptName(ConceptName.of("SubSubConcept1"))
        Assertions.assertEquals(ConceptName.of("SubSubConcept1"), subSubConcept1.conceptName)
        Assertions.assertEquals(ConceptName.of("SubConcept1"), subSubConcept1.parentConceptName)


        val subConcept2 = schema.conceptByConceptName(ConceptName.of("SubConcept2"))
        Assertions.assertEquals(ConceptName.of("SubConcept2"), subConcept2.conceptName)
        Assertions.assertNull(subConcept2.parentConceptName)

        val subSubConcept2 = schema.conceptByConceptName(ConceptName.of("SubSubConcept2"))
        Assertions.assertEquals(ConceptName.of("SubSubConcept2"), subSubConcept2.conceptName)
        Assertions.assertEquals(ConceptName.of("SubConcept2"), subSubConcept2.parentConceptName)
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
    fun createSchemaWithDirectCyclicDependency() {
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
    fun createSchemaWithIndirectCyclicDependency() {
        Assertions.assertThrows(MalformedSchemaException::class.java) {
            SchemaCreator.createSchemaFromSchemaDefinitionClass(IndirectCyclicSchemaDefinitionClass::class.java)
        }
    }


}
