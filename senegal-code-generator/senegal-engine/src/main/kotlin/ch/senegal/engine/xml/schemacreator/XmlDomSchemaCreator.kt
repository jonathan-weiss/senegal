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
        attachAllConceptAttributes(document, schemaElement, resolvedPlugins)

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
    private fun attachComment(document: Document, schemaElement: Element, comment: String) {
        //schemaElement.appendChild(document.createTextNode("\n\n"))
        schemaElement.appendChild(document.createComment(" - - - - - - - -      $comment     - - - - - - - "))
        //schemaElement.appendChild(document.createTextNode("\n\n"))
    }


    private fun attachRootConceptReferences(document: Document, schemaElement: Element, resolvedPlugins: ResolvedPlugins) {
        attachComment(document, schemaElement, " ROOT CONCEPTS")
        val elementSenegal = createAndAttachXsdElement(document, schemaElement, "element")
        setElementXsdAttribute(elementSenegal, "name", "senegal")
        val complexType = createAndAttachXsdElement(document, elementSenegal, "complexType")
        val sequence = createAndAttachXsdElement(document, complexType, "sequence")
        setElementXsdAttribute(sequence, "minOccurs", "0")
        setElementXsdAttribute(sequence, "maxOccurs", "unbounded")

        resolvedPlugins.resolvedRootConcepts.forEach {
            val conceptXmlSchemaName = it.toXmlElementTagName()
            val element = createAndAttachXsdElement(document, sequence, "element")
            setElementXsdAttribute(element, "ref", conceptXmlSchemaName)
        }
    }

    private fun attachAllConceptElements(document: Document, schemaElement: Element, resolvedPlugins: ResolvedPlugins) {
        attachComment(document, schemaElement, " ALL CONCEPTS AS ELEMENTS")
        resolvedPlugins.allResolvedConcepts.forEach { conceptNode ->
            val conceptXmlSchemaName = conceptNode.toXmlElementTagName()
            val element = createAndAttachXsdElement(document, schemaElement, "element")
            setElementXsdAttribute(element, "name", conceptXmlSchemaName)
            val complexType = createAndAttachXsdElement(document, element, "complexType")
            val sequence = createAndAttachXsdElement(document, complexType, "sequence")
            setElementXsdAttribute(sequence, "minOccurs", "0")
            setElementXsdAttribute(sequence, "maxOccurs", "unbounded")
            conceptNode.enclosedConcepts.forEach { enclosedConceptNode ->
                val enclosedConceptXmlSchemaName = enclosedConceptNode.toXmlElementTagName()
                val elementRef = createAndAttachXsdElement(document, sequence, "element")
                setElementXsdAttribute(elementRef, "ref", enclosedConceptXmlSchemaName)
            }
            conceptNode.enclosedFacets
                .filter { !it.facet.isOnlyCalculated }
                .forEach { resolvedFacet ->
                    complexType.appendChild(createFacetAttributeReference(document, resolvedFacet.purposeFacetName))
                }
        }
    }

    private fun attachAllConceptAttributes(document: Document, schemaElement: Element, resolvedPlugins: ResolvedPlugins) {
        attachComment(document, schemaElement, " ALL ATTRIBUTES ")
        resolvedPlugins.allResolvedConcepts.forEach { conceptNode ->
            conceptNode.enclosedFacets
                .filter { !it.facet.isOnlyCalculated }
                .forEach { resolvedFacet ->
                    schemaElement.appendChild(createFacetAttributeElement(document, resolvedFacet.purposeFacetName, resolvedFacet.facet.facetType))
                }
        }
    }

    private fun createFacetAttributeElement(document: Document, purposeFacetAttributeName: PurposeFacetCombinedName, facetType: FacetType): Element {
        val attributeGroupElement = createXsdElement(document, "attributeGroup")
        setElementXsdAttribute(attributeGroupElement, "name", purposeFacetAttributeName.toXmlAttributeName())

        val attributeElement = createXsdElement(document, "attribute")
        setElementXsdAttribute(attributeElement, "name", purposeFacetAttributeName.toXmlAttributeName())

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

        attributeGroupElement.appendChild(attributeElement)
        return attributeGroupElement
    }

    private fun createFacetAttributeReference(document: Document, purposeFacetAttributeName: PurposeFacetCombinedName): Element {
        val attributeElement = createXsdElement(document, "attributeGroup")
        setElementXsdAttribute(attributeElement, "ref", purposeFacetAttributeName.toXmlAttributeName())
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

    private fun ResolvedConcept.toXmlElementTagName(): String {
        return CaseUtil.decapitalize(this.concept.conceptName.name)
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

    private fun PurposeFacetCombinedName.toXmlAttributeName(): String {
        return CaseUtil.decapitalize(this.name)
    }


}
