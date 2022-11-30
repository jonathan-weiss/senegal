package ch.senegal.engine.xml.schemacreator

import ch.senegal.engine.plugin.*
import ch.senegal.engine.plugin.tree.ConceptNode
import ch.senegal.engine.plugin.tree.PluginTree
import ch.senegal.engine.util.CaseUtil
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult


object XmlDomSchemaCreator {
    const val xsdNamespace = "http://www.w3.org/2001/XMLSchema"
    const val xsdNamespacePrefix = "xs"

    fun createPluginTreeSchema(pluginTree: PluginTree): String {
        val document = initializeDocument()
        val schemaElement = createMainStructure(document)
        attachRootConceptReferences(document, schemaElement, pluginTree)
        attachAllConceptElements(document, schemaElement, pluginTree)
        return transformDocumentToString(document)
    }

    private fun createMainStructure(document: Document): Element {
        val overallXmlSchemaName = "senegal"
        val schemaElement: Element = document.createElementNS(xsdNamespace,xsdName("schema"))

        schemaElement.setAttribute("targetNamespace", "https://senegal.ch/$overallXmlSchemaName")
        schemaElement.setAttribute("xmlns", "https://senegal.ch/$overallXmlSchemaName")
        schemaElement.setAttribute( "elementFormDefault", "qualified")

        document.appendChild(schemaElement)

        return schemaElement
    }

    private fun attachRootConceptReferences(document: Document, schemaElement: Element, pluginTree: PluginTree) {
        val elementSenegal = createAndAttachXsdElement(document, schemaElement, "element")
        setElementXsdAttribute(elementSenegal, "name", "senegal")
        val complexType = createAndAttachXsdElement(document, elementSenegal, "complexType")
        val sequence = createAndAttachXsdElement(document, complexType, "sequence")
        setElementXsdAttribute(sequence, "minOccurs", "0")
        setElementXsdAttribute(sequence, "maxOccurs", "unbounded")

        pluginTree.rootConceptNodes.forEach {
            val conceptXmlSchemaName = schemaTagName(it)
            val element = createAndAttachXsdElement(document, sequence, "element")
            setElementXsdAttribute(element, "ref", conceptXmlSchemaName)
        }
    }

    private fun attachAllConceptElements(document: Document, schemaElement: Element, pluginTree: PluginTree) {
        pluginTree.allConceptNodes.values.forEach { conceptNode ->
            val conceptXmlSchemaName = schemaTagName(conceptNode)
            val element = createAndAttachXsdElement(document, schemaElement, "element")
            setElementXsdAttribute(element, "name", conceptXmlSchemaName)
            val complexType = createAndAttachXsdElement(document, element, "complexType")
            val sequence = createAndAttachXsdElement(document, complexType, "sequence")
            setElementXsdAttribute(sequence, "minOccurs", "0")
            setElementXsdAttribute(sequence, "maxOccurs", "unbounded")
            conceptNode.enclosedConcepts.forEach { enclosedConceptNode ->
                val enclosedConceptXmlSchemaName = schemaTagName(enclosedConceptNode)
                val elementRef = createAndAttachXsdElement(document, sequence, "element")
                setElementXsdAttribute(elementRef, "ref", enclosedConceptXmlSchemaName)
            }

            conceptNode.concept.conceptDecors.forEach { conceptDecor ->
                val conceptDecorAttributeName = schemaAttributeName(conceptDecor.conceptDecorName.name)
                complexType.appendChild(createDecorAttributeElement(document, conceptDecorAttributeName, conceptDecor.conceptDecorType))
            }
            conceptNode.enclosedPurposes.forEach { purpose ->
                purpose.purposeDecors.forEach { decor ->
                    val purposeAttributeName = schemaAttributeName(purpose.purposeName.name)
                    val purposeDecorAttributeName = schemaAttributeName(decor.purposeDecorName.name)
                    complexType.appendChild(createDecorAttributeElement(document, "$purposeAttributeName-$purposeDecorAttributeName", decor.purposeDecorType))
                }
            }
        }
    }

    private fun createDecorAttributeElement(document: Document, decorAttributeName: String, decorType: DecorType): Element {
        val attributeElement = createXsdElement(document, "attribute")
        setElementXsdAttribute(attributeElement, "name", decorAttributeName)

        if(decorType is EnumerationDecorType) {
            val simpleType = createAndAttachXsdElement(document, attributeElement, "simpleType")
            val restriction = createAndAttachXsdElement(document, simpleType, "restriction")
            setElementXsdAttribute(restriction, "base", "xs:string")
            decorType.enumerationValues.forEach { enumerationValue ->
                val enumerationValueElement = createAndAttachXsdElement(document, restriction, "enumeration")
                setElementXsdAttribute(enumerationValueElement, "value", enumerationValue.name)
            }
        } else {
            val decorTypeXsd = schemaAttributeType(decorType)
            setElementXsdAttribute(attributeElement, "type", decorTypeXsd)
        }
        return attributeElement
    }

    private fun initializeDocument(): Document {
        val docFactory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
        val docBuilder: DocumentBuilder = docFactory.newDocumentBuilder()
        return docBuilder.newDocument()
    }

    private fun transformDocumentToString(doc: Document): String {
        val transformerFactory: TransformerFactory = TransformerFactory.newInstance()
        transformerFactory.setAttribute("indent-number", 4);
        val transformer: Transformer = transformerFactory.newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes") // pretty print XML
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8")
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no")

        val domSource = DOMSource(doc)

        StringWriter().use { writer ->
            val result = StreamResult(writer)
            transformer.transform(domSource, result)
            return@transformDocumentToString writer.toString()
        }
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

    private fun xsdName(attributeName:String): String {
        return "$xsdNamespacePrefix:$attributeName"
    }

    private fun createXsdElement(document: Document, elementName: String): Element {
        return document.createElementNS(xsdNamespace, xsdName(elementName))
    }

    private fun createAndAttachXsdElement(document: Document, parentElement: Element, elementName: String): Element {
        val element = createXsdElement(document, elementName)
        parentElement.appendChild(element)
        return element
    }

    private fun setElementXsdAttribute(element: Element, attributeName: String, attributeValue: String) {
        element.setAttribute(attributeName, attributeValue)
    }

}
