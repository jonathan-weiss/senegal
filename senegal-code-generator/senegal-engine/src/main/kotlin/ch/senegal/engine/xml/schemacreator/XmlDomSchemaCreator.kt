package ch.senegal.engine.xml.schemacreator

import ch.senegal.engine.plugin.resolver.ResolvedConcept
import ch.senegal.engine.plugin.resolver.ResolvedPlugins
import ch.senegal.engine.util.CaseUtil
import ch.senegal.plugin.*
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
    private const val xsdNamespace = "http://www.w3.org/2001/XMLSchema"
    private const val xsdNamespacePrefix = "xsd"

    fun createPluginSchema(resolvedPlugins: ResolvedPlugins): String {
        val document = initializeDocument()
        val schemaElement = createMainStructure(document)
        attachRootConceptReferences(document, schemaElement, resolvedPlugins)
        attachAllConceptElements(document, schemaElement, resolvedPlugins)
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

    private fun attachRootConceptReferences(document: Document, schemaElement: Element, resolvedPlugins: ResolvedPlugins) {
        val elementSenegal = createAndAttachXsdElement(document, schemaElement, "element")
        setElementXsdAttribute(elementSenegal, "name", "senegal")
        val complexType = createAndAttachXsdElement(document, elementSenegal, "complexType")
        val sequence = createAndAttachXsdElement(document, complexType, "sequence")
        setElementXsdAttribute(sequence, "minOccurs", "0")
        setElementXsdAttribute(sequence, "maxOccurs", "unbounded")

        resolvedPlugins.resolvedRootConcepts.forEach {
            val conceptXmlSchemaName = schemaTagName(it)
            val element = createAndAttachXsdElement(document, sequence, "element")
            setElementXsdAttribute(element, "ref", conceptXmlSchemaName)
        }
    }

    private fun attachAllConceptElements(document: Document, schemaElement: Element, resolvedPlugins: ResolvedPlugins) {
        resolvedPlugins.allResolvedConcepts.forEach { conceptNode ->
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
            conceptNode.enclosedPurposes.forEach { purpose ->
                purpose.facets
                    .filter { !it.isOnlyCalculated }
                    .forEach { facet ->
                    val purposeXmlAttributeNamePart = schemaAttributeName(purpose)
                    val facetXmlAttributeNamePart = facet.facetName.name
                    complexType.appendChild(createFacetAttributeElement(document, "$purposeXmlAttributeNamePart$facetXmlAttributeNamePart", facet.facetType))
                }
            }
        }
    }

    private fun createFacetAttributeElement(document: Document, purposeFacetAttributeName: String, facetType: FacetType): Element {
        val attributeElement = createXsdElement(document, "attribute")
        setElementXsdAttribute(attributeElement, "name", purposeFacetAttributeName)

        if(facetType is EnumerationFacetType) {
            val simpleType = createAndAttachXsdElement(document, attributeElement, "simpleType")
            val restriction = createAndAttachXsdElement(document, simpleType, "restriction")
            setElementXsdAttribute(restriction, "base", "$xsdNamespacePrefix:string")
            facetType.enumerationValues.forEach { enumerationValue ->
                val enumerationValueElement = createAndAttachXsdElement(document, restriction, "enumeration")
                setElementXsdAttribute(enumerationValueElement, "value", enumerationValue.name)
            }
        } else {
            val facetTypeXsd = schemaAttributeType(facetType)
            setElementXsdAttribute(attributeElement, "type", facetTypeXsd)
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

    private fun schemaTagName(resolvedConcept: ResolvedConcept): String {
        return toXmlName(resolvedConcept.concept.conceptName.name)
    }

    private fun schemaAttributeName(purpose: Purpose): String {
        return toXmlName(purpose.purposeName.name)
    }

    private fun schemaAttributeName(facet: Facet): String {
        return toXmlName(facet.facetName.name)
    }


    private fun toXmlName(value: String): String {
        return CaseUtil.decapitalize(value)
    }

    private fun schemaAttributeType(facetType: FacetType): String {
        return when(facetType) {
            TextFacetType -> "$xsdNamespacePrefix:string"
            IntegerNumberFacetType -> "$xsdNamespacePrefix:integer"
            BooleanFacetType -> "$xsdNamespacePrefix:boolean"
            DirectoryFacetType -> "$xsdNamespacePrefix:string"
            FileFacetType -> "$xsdNamespacePrefix:string"
            else -> throw IllegalArgumentException("FacetType is not supported: $facetType")
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
