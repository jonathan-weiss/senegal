package ch.senegal.engine.xml.schemacreator

import ch.senegal.engine.plugin.*
import ch.senegal.engine.plugin.tree.ConceptNode
import ch.senegal.engine.plugin.tree.PluginTree
import ch.senegal.engine.util.CaseUtil

@Deprecated("Use DOM-based schema creator to have better ident support")
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
        val conceptXmlSchemaTags = conceptNode.enclosedConcepts.joinToString("\n") { createConceptReferenceEntry(it) }
        val conceptXmlSchemaAttributes =
            conceptNode.concept.conceptDecors.joinToString("\n") { createConceptAttribute(it) }
        val purposeXmlSchemaAttributes = conceptNode.enclosedPurposes
            .flatMap { purpose -> purpose.purposeDecors.map { decor -> createPurposeAttribute(purpose, decor) } }
            .joinToString("\n")

        return """
                <xs:element name="$conceptXmlSchemaName" >
                    <xs:complexType>
                        <xs:sequence minOccurs="0" maxOccurs="unbounded">
                            ${addIdent(conceptXmlSchemaTags, 12)}
                        </xs:sequence>
                        ${addIdent(conceptXmlSchemaAttributes, 8)}
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

    private fun createConceptAttribute(conceptDecor: ConceptDecor): String {
        val attributeName = schemaAttributeName(conceptDecor.conceptDecorName.name)
        return createAttribute(attributeName, conceptDecor.conceptDecorType)
    }


    private fun createPurposeAttribute(purpose: Purpose, decor: PurposeDecor): String {
        val purposeAttributeName = schemaAttributeName(purpose.purposeName.name)
        val decorAttributeName = schemaAttributeName(decor.purposeDecorName.name)
        val attributeName = "$purposeAttributeName-$decorAttributeName"
        return createAttribute(attributeName, decor.purposeDecorType)
    }

    private fun createAttribute(attributeName: String, decorType: DecorType): String {
        if(decorType is EnumerationDecorType) {
            val enumerationTags = decorType.enumerationValues
                .map { createEnumerationElementSchema(it) }
                .joinToString("")

            return """
                <xs:attribute name="$attributeName">
                    <xs:simpleType>
                        <xs:restriction base="xs:string">
                            $enumerationTags
                        </xs:restriction>
                    </xs:simpleType>
                </xs:attribute>
            """.trimIndent()
        } else {
            val decorTypeXsd = schemaAttributeType(decorType)
            return """
            <xs:attribute name="$attributeName" type="$decorTypeXsd"/>
            """.trimIndent()

        }
    }

    private fun createEnumerationElementSchema(enumerationValue: DecorTypeEnumerationValue): String {
        return """<xs:enumeration value="${enumerationValue.name}"/>"""
    }

    private fun schemaTagName(conceptNode: ConceptNode): String {
        return CaseUtil.camelToDashCase(conceptNode.concept.conceptName.name)
    }

    private fun schemaAttributeName(value: String): String {
        return CaseUtil.camelToDashCase(value)
    }

    private fun schemaAttributeType(decorType: DecorType): String {
        return when(decorType) {
            TextDecorType -> "xs:string"
            IntegerNumberDecorType -> "xs:integer"
            BooleanDecorType -> "xs:boolean"
            else -> throw IllegalArgumentException("DecorType is not supported: $decorType")
        }
    }

}
