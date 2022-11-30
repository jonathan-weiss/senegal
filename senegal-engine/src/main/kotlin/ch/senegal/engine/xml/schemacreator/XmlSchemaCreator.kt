package ch.senegal.engine.xml.schemacreator

import ch.senegal.engine.plugin.Purpose
import ch.senegal.engine.plugin.tree.ConceptNode
import ch.senegal.engine.plugin.tree.PluginTree
import ch.senegal.engine.util.CaseUtil

object XmlSchemaCreator {

    fun createPluginTreeSchema(pluginTree: PluginTree): String {
        val overallXmlSchemaName = "senegal"

        val refListOfRootConcepts = pluginTree.rootConceptNodes
            .map { createConceptReferenceEntry(it) }
            .joinToString("")
        val conceptXmlSchemaTags = pluginTree.allConceptNodes.values
            .map { createConceptElementSchema(it) }
            .joinToString("")
        return """
            <?xml version="1.0"?>
            <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
                       targetNamespace="https://senegal.ch/$overallXmlSchemaName"
                       xmlns="https://senegal.ch/$overallXmlSchemaName"
                       elementFormDefault="qualified">

                <xs:element name="$overallXmlSchemaName">
                    <xs:complexType>
                        <xs:sequence minOccurs="0" maxOccurs="unbounded">
                            ${addIdent(refListOfRootConcepts, 28)}
                        </xs:sequence>
                    </xs:complexType>
                </xs:element>

                ${addIdent(conceptXmlSchemaTags, 16)}

                <xs:simpleType name="identifier">
                    <xs:restriction base="xs:string">
                        <xs:pattern value="([a-zA-Z])([a-zA-Z0-9_])*"/>
                    </xs:restriction>
                </xs:simpleType>

            </xs:schema>
        """.trimIndent()
    }

    private fun addIdent(multilineValue: String, ident: Int, skipFirstLine: Boolean = true): String {
        return multilineValue
            .split("\n")
            .mapIndexed { index, it -> if(skipFirstLine && index == 0) it else addIdentToLine(it, ident) }
            .joinToString("\n")
    }

    private fun addIdentToLine(line: String, ident: Int): String {
        return " ".repeat(ident) + line
    }

    private fun createConceptElementSchema(conceptNode: ConceptNode): String {
        val conceptXmlSchemaName = schemaTagName(conceptNode)
        val conceptXmlSchemaTags = conceptNode.enclosedConcepts
            .map { createConceptReferenceEntry(it) }
            .joinToString("\n")
        val purposeXmlSchemaAttributes = conceptNode.enclosedPurposes
            .map { createPurposeAttribute(it) }
            .joinToString("\n")

        return """
                <xs:element name="$conceptXmlSchemaName" >
                    <xs:complexType>
                        <xs:sequence minOccurs="0" maxOccurs="unbounded">
                            ${addIdent(conceptXmlSchemaTags, 12)}
                        </xs:sequence>
                        ${addIdent(purposeXmlSchemaAttributes, 8)}
                    </xs:complexType>
                </xs:element>
        
        
        """.trimIndent()
    }

    private fun createConceptReferenceEntry(conceptNode: ConceptNode): String {
        val conceptXmlSchemaName = schemaTagName(conceptNode)
        return """
            <xs:element ref="$conceptXmlSchemaName"/>
        """.trimIndent()
    }

    private fun createPurposeAttribute(purpose: Purpose): String {
        val attributeName = schemaAttributeName(purpose)
        return """
            <xs:attribute name="$attributeName" type="xs:string"/>
        """.trimIndent()
    }


    private fun schemaTagName(conceptNode: ConceptNode): String {
        return CaseUtil.camelToDashCase(conceptNode.concept.conceptName.name)
    }

    private fun schemaAttributeName(purpose: Purpose): String {
        return CaseUtil.camelToDashCase(purpose.purposeName.name)
    }

}
