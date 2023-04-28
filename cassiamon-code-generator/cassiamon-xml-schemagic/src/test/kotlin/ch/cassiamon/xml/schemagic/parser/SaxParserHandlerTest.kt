package ch.cassiamon.xml.schemagic.parser

import ch.cassiamon.engine.ProcessFacades
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.engine.logger.JavaUtilLoggerFacade
import ch.cassiamon.engine.schema.registration.RegistrationApiDefaultImpl
import ch.cassiamon.engine.filesystem.PhysicalFilesFileSystemAccess
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.facets.MandatoryTextInputFacet
import ch.cassiamon.pluginapi.schema.SchemaAccess
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.nio.file.Paths
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

internal class SaxParserHandlerTest {

    private val testEntityConceptName = ConceptName.of("TestEntity")
    private val testEntityNameFacet = MandatoryTextInputFacet.of("TestEntityName")
    private val testEntityKotlinModelClassnameFacet = MandatoryTextInputFacet.of("TestKotlinModelClassname")
    private val testEntityKotlinModelPackageFacet = MandatoryTextInputFacet.of("TestKotlinModelPackage")

    private val testEntityAttributeConceptName = ConceptName.of("TestEntityAttribute")
    private val testEntityAttributeNameFacet = MandatoryTextInputFacet.of("TestEntityAttributeName")
    private val testEntityAttributeTypeFacet = MandatoryTextInputFacet.of("TestEntityAttributeType")
    private val testKotlinFieldTypeFacet = MandatoryTextInputFacet.of("TestKotlinFieldType")

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
        val dataCollector = ModelInputDataCollector()

        val saxParserHandler = SaxParserHandler(schema, dataCollector, emptyMap(), Paths.get("."), virtualFileSystem, logger)

        testXml.byteInputStream().use {
            saxParser.parse(it, saxParserHandler)
        }

        val modelInputData = dataCollector.provideModelInputData()

        assertEquals(7, modelInputData.entries.size)

        val personRootNode = modelInputData.entries[0]
        assertEquals(testEntityConceptName, personRootNode.conceptName)
        assertEquals("Person", personRootNode.inputFacetValueAccess.facetValue(testEntityNameFacet))
        assertEquals("Person", personRootNode.inputFacetValueAccess.facetValue(testEntityKotlinModelClassnameFacet))
        assertEquals("ch.senegal.entities", personRootNode.inputFacetValueAccess.facetValue(testEntityKotlinModelPackageFacet))
        val firstnameNode = modelInputData.entries[1]
        assertEquals(testEntityAttributeConceptName, firstnameNode.conceptName)
        assertEquals("firstname", firstnameNode.inputFacetValueAccess.facetValue(testEntityAttributeNameFacet))
        assertEquals("TEXT", firstnameNode.inputFacetValueAccess.facetValue(testEntityAttributeTypeFacet))
        assertEquals("kotlin.String", firstnameNode.inputFacetValueAccess.facetValue(testKotlinFieldTypeFacet))
        val addressRootNode = modelInputData.entries[4]
        assertEquals(testEntityConceptName, addressRootNode.conceptName)
        assertEquals("Address", addressRootNode.inputFacetValueAccess.facetValue(testEntityNameFacet))
        assertEquals("ch.senegal.entities", addressRootNode.inputFacetValueAccess.facetValue(testEntityKotlinModelPackageFacet))
    }

    private fun createSchema(): SchemaAccess {
        val registrationApi = RegistrationApiDefaultImpl(ProcessFacades())

        registrationApi.configureSchema {
            newRootConcept(conceptName = testEntityConceptName) {
                addFacet(testEntityNameFacet)
                addFacet(testEntityKotlinModelClassnameFacet)
                addFacet(testEntityKotlinModelPackageFacet)

                newChildConcept(conceptName = testEntityAttributeConceptName) {
                    addFacet(testEntityAttributeNameFacet)
                    addFacet(testEntityAttributeTypeFacet) // TODO use enumeration as soon as available
                    addFacet(testKotlinFieldTypeFacet)
                }
            }
        }

        return registrationApi.provideSchema()
    }
}

