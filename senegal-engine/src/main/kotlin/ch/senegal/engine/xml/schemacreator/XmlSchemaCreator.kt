package ch.senegal.engine.xml.schemacreator

import ch.senegal.engine.plugin.Concept

object XmlSchemaCreator {

    fun createConceptSchema(concept: Concept): String { // TODO pass whole concept and purpose tree
        val overallXmlSchemaName = "senegal"
        val conceptXmlSchemaName = concept.conceptName.name.lowercase() // TODO to valid xml Name (from SnakeCase)
        return """
            <?xml version="1.0"?>
            <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
                       targetNamespace="https://senegal.ch/$conceptXmlSchemaName"
                       xmlns="https://senegal.ch/$conceptXmlSchemaName"
                       elementFormDefault="qualified">

                <xs:element name="$overallXmlSchemaName">
                    <xs:complexType>
                        <xs:sequence minOccurs="0" maxOccurs="unbounded">
                            <xs:element ref="$conceptXmlSchemaName"/>
                        </xs:sequence>
                    </xs:complexType>
                </xs:element>

                <xs:element name="$conceptXmlSchemaName" >
                    <xs:complexType>
                        <xs:anyAttribute/>
                    </xs:complexType>
                </xs:element>

                <xs:simpleType name="identifier">
                    <xs:restriction base="xs:string">
                        <xs:pattern value="([a-zA-Z])([a-zA-Z0-9_])*"/>
                    </xs:restriction>
                </xs:simpleType>

            </xs:schema>
        """.trimIndent()
    }
}
