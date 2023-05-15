package ch.cassiamon.xml.schemagic.schemacreator

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.model.facets.*
import ch.cassiamon.api.schema.ConceptSchema
import ch.cassiamon.api.schema.InputFacetSchema
import ch.cassiamon.api.schema.SchemaAccess
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


internal class XmlDomSchemaCreatorTest {

    private val expectedXml = """
        <?xml version="1.0" encoding="UTF-8" standalone="no"?>
        <xsd:schema xmlns="https://cassiamon.ch/cassiamon-schemagic" elementFormDefault="qualified" targetNamespace="https://cassiamon.ch/cassiamon-schemagic" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
            <!-- - - - - - - - -       CONFIGURATION AND DEFINITIONS     - - - - - - - -->
            <xsd:element name="cassiamon">
                <xsd:complexType>
                    <xsd:sequence maxOccurs="1" minOccurs="1">
                        <xsd:element name="configuration" type="configurationType"/>
                        <xsd:element name="definitions">
                            <xsd:complexType>
                                <!-- - - - - - - - -       ROOT CONCEPTS     - - - - - - - -->
                                <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                    <xsd:element name="testEntity" type="testEntityType"/>
                                </xsd:choice>
                            </xsd:complexType>
                        </xsd:element>
                    </xsd:sequence>
                </xsd:complexType>
            </xsd:element>
            <!-- - - - - - - - -       ALL CONCEPTS AS TYPES     - - - - - - - -->
            <xsd:complexType name="testEntityType">
                <xsd:choice maxOccurs="unbounded" minOccurs="0">
                    <xsd:element name="testEntityAttribute" type="testEntityAttributeType"/>
                </xsd:choice>
                <xsd:attributeGroup ref="testEntityName"/>
            </xsd:complexType>
            <xsd:complexType name="testEntityAttributeType">
                <xsd:choice maxOccurs="unbounded" minOccurs="0"/>
                <xsd:attributeGroup ref="testEntityAttributeName"/>
            </xsd:complexType>
            <!-- - - - - - - - -       ALL ATTRIBUTES      - - - - - - - -->
            <xsd:attributeGroup name="testEntityName">
                <xsd:attribute name="testEntityName" type="xsd:string"/>
            </xsd:attributeGroup>
            <xsd:attributeGroup name="testEntityAttributeName">
                <xsd:attribute name="testEntityAttributeName" type="xsd:string"/>
            </xsd:attributeGroup>
            <!-- - - - - - - - -       CONFIGURATION ELEMENT     - - - - - - - -->
            <xsd:complexType name="configurationType">
                <xsd:attributeGroup ref="testEntityName"/>
                <xsd:attributeGroup ref="testEntityAttributeName"/>
            </xsd:complexType>
        </xsd:schema>

    """.trimIndent()


    @Test
    fun testXmlDomSchemaCreator() {
        val schema: SchemaAccess = createSchema()
        val schemaContent = XmlDomSchemaCreator.createSchemagicSchemaContent(schema)
        assertEquals(expectedXml, schemaContent)
    }

    private val testEntityConceptName: ConceptName = ConceptName.of("TestEntity")
    private val testEntityNameInputFacet: MandatoryInputFacet<TextFacetKotlinType> = TextFacets.ofMandatoryInput("TestEntityName")

    private val testEntityAttributeConceptName: ConceptName = ConceptName.of("TestEntityAttribute")
    private val testEntityAttributeNameInputFacet: MandatoryInputFacet<TextFacetKotlinType> = TextFacets.ofMandatoryInput("TestEntityAttributeName")

    private fun createSchema(): SchemaAccess {
        val testEntityConcept: ConceptSchema = SimpleConceptSchema(
            conceptName = testEntityConceptName,
            parentConceptName = null,
            inputFacets = listOf(
                InputFacetSchema(testEntityConceptName, testEntityNameInputFacet),
            )
        )

        val testEntityAttributeConcept: ConceptSchema = SimpleConceptSchema(
            conceptName = testEntityAttributeConceptName,
            parentConceptName = testEntityConceptName,
            inputFacets = listOf(
                InputFacetSchema(testEntityAttributeConceptName, testEntityAttributeNameInputFacet),
            )
        )

        return SimpleSchema(listOf(testEntityConcept, testEntityAttributeConcept))
    }

}
