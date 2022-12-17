package ch.senegal.engine.xml.schemacreator

import ch.senegal.engine.plugin.TestPluginFinder
import ch.senegal.engine.plugin.resolver.PluginResolver
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


internal class XmlDomSchemaCreatorTest {

    private val expectedXml = """
        <?xml version="1.0" encoding="UTF-8" standalone="no"?>
        <xsd:schema xmlns="https://senegal.ch/senegal" elementFormDefault="qualified" targetNamespace="https://senegal.ch/senegal" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
            <!-- - - - - - - - -       ROOT CONCEPTS     - - - - - - - -->
            <xsd:element name="senegal">
                <xsd:complexType>
                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                        <xsd:element ref="testEntity"/>
                        <xsd:element ref="testMapperConcept"/>
                    </xsd:sequence>
                </xsd:complexType>
            </xsd:element>
            <!-- - - - - - - - -       ALL CONCEPTS AS ELEMENTS     - - - - - - - -->
            <xsd:element name="testEntity">
                <xsd:complexType>
                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                        <xsd:element ref="testEntityAttribute"/>
                    </xsd:sequence>
                    <xsd:attributeGroup ref="testEntityName"/>
                    <xsd:attributeGroup ref="testKotlinModelClassname"/>
                    <xsd:attributeGroup ref="testKotlinModelPackage"/>
                </xsd:complexType>
            </xsd:element>
            <xsd:element name="testEntityAttribute">
                <xsd:complexType>
                    <xsd:sequence maxOccurs="unbounded" minOccurs="0"/>
                    <xsd:attributeGroup ref="testEntityAttributeName"/>
                    <xsd:attributeGroup ref="testEntityAttributeType"/>
                    <xsd:attributeGroup ref="testKotlinFieldName"/>
                    <xsd:attributeGroup ref="testKotlinFieldType"/>
                </xsd:complexType>
            </xsd:element>
            <xsd:element name="testMapperConcept">
                <xsd:complexType>
                    <xsd:sequence maxOccurs="unbounded" minOccurs="0"/>
                </xsd:complexType>
            </xsd:element>
            <!-- - - - - - - - -       ALL ATTRIBUTES      - - - - - - - -->
            <xsd:attributeGroup name="testEntityName">
                <xsd:attribute name="testEntityName" type="xsd:string"/>
            </xsd:attributeGroup>
            <xsd:attributeGroup name="testKotlinModelClassname">
                <xsd:attribute name="testKotlinModelClassname" type="xsd:string"/>
            </xsd:attributeGroup>
            <xsd:attributeGroup name="testKotlinModelPackage">
                <xsd:attribute name="testKotlinModelPackage" type="xsd:string"/>
            </xsd:attributeGroup>
            <xsd:attributeGroup name="testEntityAttributeName">
                <xsd:attribute name="testEntityAttributeName" type="xsd:string"/>
            </xsd:attributeGroup>
            <xsd:attributeGroup name="testEntityAttributeType">
                <xsd:attribute name="testEntityAttributeType">
                    <xsd:simpleType>
                        <xsd:restriction base="xsd:string">
                            <xsd:enumeration value="TEXT"/>
                            <xsd:enumeration value="NUMBER"/>
                            <xsd:enumeration value="BOOLEAN"/>
                        </xsd:restriction>
                    </xsd:simpleType>
                </xsd:attribute>
            </xsd:attributeGroup>
            <xsd:attributeGroup name="testKotlinFieldName">
                <xsd:attribute name="testKotlinFieldName" type="xsd:string"/>
            </xsd:attributeGroup>
            <xsd:attributeGroup name="testKotlinFieldType">
                <xsd:attribute name="testKotlinFieldType">
                    <xsd:simpleType>
                        <xsd:restriction base="xsd:string">
                            <xsd:enumeration value="kotlin.String"/>
                            <xsd:enumeration value="kotlin.Int"/>
                            <xsd:enumeration value="kotlin.Boolean"/>
                        </xsd:restriction>
                    </xsd:simpleType>
                </xsd:attribute>
            </xsd:attributeGroup>
        </xsd:schema>

    """.trimIndent()


    @Test
    fun testXmlDomSchemaCreator() {
        val plugins = TestPluginFinder.findAllTestPlugins()
        val resolvedPlugins = PluginResolver.resolvePlugins(plugins)
        val schemaResult = XmlDomSchemaCreator.createPluginSchema(resolvedPlugins)
        assertEquals(expectedXml, schemaResult)
    }
}
