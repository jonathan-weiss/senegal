package ch.cassiamon.xml.schemagic.schemacreator

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.schema.ConceptSchema
import ch.cassiamon.api.schema.FacetTypeEnum
import ch.cassiamon.api.schema.SchemaAccess
import ch.cassiamon.engine.domain.schema.FacetSchemaImpl
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

    private val testEntityConceptName = ConceptName.of("TestEntity")
    private val testEntityNameFacetName = FacetName.of("TestEntityName")

    private val testEntityAttributeConceptName = ConceptName.of("TestEntityAttribute")
    private val testEntityAttributeNameFacetName = FacetName.of("TestEntityAttributeName")

    private fun createSchema(): SchemaAccess {
        val testEntityConcept: ConceptSchema = SimpleConceptSchema(
            conceptName = testEntityConceptName,
            parentConceptName = null,
            facets = listOf(
                FacetSchemaImpl(testEntityNameFacetName, FacetTypeEnum.TEXT, mandatory = true),
            )
        )

        val testEntityAttributeConcept: ConceptSchema = SimpleConceptSchema(
            conceptName = testEntityAttributeConceptName,
            parentConceptName = testEntityConceptName,
            facets = listOf(
                FacetSchemaImpl(testEntityAttributeNameFacetName, FacetTypeEnum.TEXT, mandatory = true),
            )
        )

        return SimpleSchema(listOf(testEntityConcept, testEntityAttributeConcept))
    }

}
