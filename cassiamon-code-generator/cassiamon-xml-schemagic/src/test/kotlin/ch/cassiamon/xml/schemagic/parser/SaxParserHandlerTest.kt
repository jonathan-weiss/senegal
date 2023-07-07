package ch.cassiamon.xml.schemagic.parser

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName
import ch.cassiamon.api.process.schema.annotations.ChildConcepts
import ch.cassiamon.api.process.schema.annotations.Concept
import ch.cassiamon.api.process.schema.annotations.InputFacet
import ch.cassiamon.api.process.schema.annotations.Schema
import ch.cassiamon.api.process.schema.SchemaAccess
import ch.cassiamon.engine.process.schema.SchemaCreator
import ch.cassiamon.engine.process.datacollection.ConceptDataCollector
import ch.cassiamon.engine.filesystem.PhysicalFilesFileSystemAccess
import ch.cassiamon.engine.logger.JavaUtilLoggerFacade
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Paths
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

private const val testEntityConceptNameConst = "TestEntity"
private const val testEntityNameFacetNameConst = "TestEntityName"
private const val testEntityKotlinModelClassnameFacetNameConst = "TestKotlinModelClassname"
private const val testEntityKotlinModelPackageFacetNameConst = "TestKotlinModelPackage"
private const val testEntityAttributeConceptNameConst = "TestEntityAttribute"
private const val testEntityAttributeNameFacetNameConst = "TestEntityAttributeName"
private const val testEntityAttributeTypeFacetNameConst = "TestEntityAttributeType"
private const val testKotlinFieldTypeFacetNameConst = "TestKotlinFieldType"

internal class SaxParserHandlerTest {

    private val testEntityConceptName = ConceptName.of(testEntityConceptNameConst)
    private val testEntityNameFacetName = FacetName.of(testEntityNameFacetNameConst)
    private val testEntityKotlinModelClassnameFacetName = FacetName.of(testEntityKotlinModelClassnameFacetNameConst)
    private val testEntityKotlinModelPackageFacetName = FacetName.of(testEntityKotlinModelPackageFacetNameConst)
    private val testEntityAttributeConceptName = ConceptName.of(testEntityAttributeConceptNameConst)
    private val testEntityAttributeNameFacetName = FacetName.of(testEntityAttributeNameFacetNameConst)
    private val testEntityAttributeTypeFacetName = FacetName.of(testEntityAttributeTypeFacetNameConst)
    private val testKotlinFieldTypeFacetName = FacetName.of(testKotlinFieldTypeFacetNameConst)

    private val testXml = """
        <?xml version="1.0" encoding="utf-8" ?>
        <senegal xmlns="https://senegal.ch/senegal"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="https://senegal.ch/senegal senegal-schema.xsd">
            <configuration testKotlinModelPackage="ch.senegal.entities"/>
            <definitions>
                <testEntity testEntityName="Person" testKotlinModelClassname="Person">
                    <testEntityAttribute testEntityAttributeName="firstname" testEntityAttributeType="TEXT" testKotlinFieldType="kotlin.String" />
                    <testEntityAttribute testEntityAttributeName="lastname" testEntityAttributeType="NUMBER" testKotlinFieldType="kotlin.Int"/>
                    <testEntityAttribute testEntityAttributeName="nickname" testEntityAttributeType="BOOLEAN" testKotlinFieldType="kotlin.Boolean"/>
                </testEntity>
                <testEntity testEntityName="Address">
                    <testEntityAttribute testEntityAttributeName="street" testEntityAttributeType="TEXT"/>
                    <testEntityAttribute testEntityAttributeName="zip" testEntityAttributeType="TEXT"/>
                </testEntity>
            </definitions>
        </senegal>
    """.trimIndent()

    @Test
    fun testSaxParser() {
        val virtualFileSystem = PhysicalFilesFileSystemAccess()
        val logger = JavaUtilLoggerFacade(virtualFileSystem)
        val factory: SAXParserFactory = SAXParserFactory.newInstance()
        factory.isNamespaceAware = true
        factory.isValidating = false // turn of validation as schema is not found
        val saxParser: SAXParser = factory.newSAXParser()
        val schema = createSchema()
        val dataCollector = ConceptDataCollector(schema)

        val saxParserHandler = SaxParserHandler(schema, dataCollector, emptyMap(), Paths.get("."), virtualFileSystem, logger)

        testXml.byteInputStream().use {
            saxParser.parse(it, saxParserHandler)
        }

        val conceptDataList = dataCollector.provideConceptData()

        assertEquals(7, conceptDataList.size)

        val personRootNode = conceptDataList[0]
        assertEquals(testEntityConceptName, personRootNode.conceptName)
        assertEquals("Person", personRootNode.getFacet(testEntityNameFacetName))
        assertEquals("Person", personRootNode.getFacet(testEntityKotlinModelClassnameFacetName))
        assertEquals("ch.senegal.entities", personRootNode.getFacet(testEntityKotlinModelPackageFacetName))
        val firstnameNode = conceptDataList[1]
        assertEquals(testEntityAttributeConceptName, firstnameNode.conceptName)
        assertEquals("firstname", firstnameNode.getFacet(testEntityAttributeNameFacetName))
        assertEquals("TEXT", firstnameNode.getFacet(testEntityAttributeTypeFacetName))
        assertEquals("kotlin.String", firstnameNode.getFacet(testKotlinFieldTypeFacetName))
        val addressRootNode = conceptDataList[4]
        assertEquals(testEntityConceptName, addressRootNode.conceptName)
        assertEquals("Address", addressRootNode.getFacet(testEntityNameFacetName))
        assertEquals("ch.senegal.entities", addressRootNode.getFacet(testEntityKotlinModelPackageFacetName))
    }


    @Schema
    private interface SaxParserTestSchema {
        @ChildConcepts(TestEntityConcept::class)
        fun getTestEntityChildren(): List<TestEntityConcept>
    }

    @Concept(testEntityConceptNameConst)
    private interface TestEntityConcept {
        @ChildConcepts(TestEntityAttributeConcept::class)
        fun getTestEntityAttributeChildren(): List<TestEntityAttributeConcept>

        @InputFacet(testEntityNameFacetNameConst)
        fun getName(): String
        @InputFacet(testEntityKotlinModelClassnameFacetNameConst)
        fun getKotlinModelClassname(): String
        @InputFacet(testEntityKotlinModelPackageFacetNameConst)
        fun getKotlinModelPackage(): String

    }
    @Concept(testEntityAttributeConceptNameConst)
    private interface TestEntityAttributeConcept {

        @InputFacet(testEntityAttributeNameFacetNameConst)
        fun getName(): String
        @InputFacet(testEntityAttributeTypeFacetNameConst) // TODO use enumeration as soon as available
        fun getType(): String
        @InputFacet(testKotlinFieldTypeFacetNameConst)
        fun getKotlinType(): String


    }
    private fun createSchema(): SchemaAccess {
        return SchemaCreator.createSchemaFromSchemaDefinitionClass(SaxParserTestSchema::class.java)
    }
}

