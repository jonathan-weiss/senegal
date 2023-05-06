package ch.cassiamon.xml.schemagic.schemacreator

import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.facets.*
import ch.cassiamon.api.schema.ConceptSchema
import ch.cassiamon.api.schema.SchemaAccess
import ch.cassiamon.xml.schemagic.parser.CaseUtil
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

    fun createSchemagicSchemaContent(schema: SchemaAccess): String {
        val document = initializeDocument()
        val schemaElement = createMainStructure(document)
        attachRootConceptReferences(document, schemaElement, schema)
        attachAllConceptElements(document, schemaElement, schema)
        attachAllConceptAttributes(document, schemaElement, schema)
        attachConfigurationElement(document, schemaElement, schema)

        return transformDocumentToString(document)
    }

    private fun createMainStructure(document: Document): Element {
        val overallXmlSchemaName = "cassiamon-schemagic"
        val schemaElement: Element = document.createElementNS(xsdNamespace,xsdName("schema"))

        schemaElement.setAttribute("targetNamespace", "https://cassiamon.ch/$overallXmlSchemaName")
        schemaElement.setAttribute("xmlns", "https://cassiamon.ch/$overallXmlSchemaName")
        schemaElement.setAttribute( "elementFormDefault", "qualified")

        document.appendChild(schemaElement)

        return schemaElement
    }
    private fun attachComment(document: Document, schemaElement: Element, comment: String) {
        schemaElement.appendChild(document.createComment(" - - - - - - - -      $comment     - - - - - - - "))
    }

    private fun attachConfigurationElement(document: Document, schemaElement: Element, schema: SchemaAccess) {
        attachComment(document, schemaElement, " CONFIGURATION ELEMENT")
        val complexType = createAndAttachXsdElement(document, schemaElement, "complexType")
        setElementXsdAttribute(complexType, "name", "configurationType")
        schema.allConcepts().forEach { conceptNode ->
            conceptNode.inputFacets.forEach { inputFacetSchema ->
                    complexType.appendChild(createFacetAttributeReference(document, inputFacetSchema.inputFacet.facetName))
                }
        }
    }

    private fun attachRootConceptReferences(document: Document, schemaElement: Element, schema: SchemaAccess) {
        attachComment(document, schemaElement, " CONFIGURATION AND DEFINITIONS")
        val senegalElement = createAndAttachXsdElement(document, schemaElement, "element")
        setElementXsdAttribute(senegalElement, "name", "cassiamon")
        val senegalComplexType = createAndAttachXsdElement(document, senegalElement, "complexType")

        val senegalSequence = createAndAttachXsdElement(document, senegalComplexType, "sequence")
        setElementXsdAttribute(senegalSequence, "minOccurs", "1")
        setElementXsdAttribute(senegalSequence, "maxOccurs", "1")
        val configurationElement = createAndAttachXsdElement(document, senegalSequence, "element")
        setElementXsdAttribute(configurationElement, "name", "configuration")
        setElementXsdAttribute(configurationElement, "type", "configurationType")
        val definitionsElement = createAndAttachXsdElement(document, senegalSequence, "element")
        setElementXsdAttribute(definitionsElement, "name", "definitions")
        val definitionsComplexType = createAndAttachXsdElement(document, definitionsElement, "complexType")
        attachComment(document, definitionsComplexType, " ROOT CONCEPTS")
        val definitionsChoice = createAndAttachXsdElement(document, definitionsComplexType, "choice")
        setElementXsdAttribute(definitionsChoice, "minOccurs", "0")
        setElementXsdAttribute(definitionsChoice, "maxOccurs", "unbounded")
        schema.allRootConcepts().forEach { conceptSchema ->
            val conceptXmlSchemaName = conceptSchema.toXmlElementTagName()
            val element = createAndAttachXsdElement(document, definitionsChoice, "element")
            setElementXsdAttribute(element, "name", conceptXmlSchemaName)
            setElementXsdAttribute(element, "type", "${conceptXmlSchemaName}Type")
        }
    }

    private fun attachAllConceptElements(document: Document, schemaElement: Element, schema: SchemaAccess) {
        attachComment(document, schemaElement, " ALL CONCEPTS AS TYPES")
        schema.allConcepts().forEach { conceptSchema ->
            val conceptXmlSchemaName = conceptSchema.toXmlElementTagName()
            val complexType = createAndAttachXsdElement(document, schemaElement, "complexType")
            setElementXsdAttribute(complexType, "name", "${conceptXmlSchemaName}Type")
            val choice = createAndAttachXsdElement(document, complexType, "choice")
            setElementXsdAttribute(choice, "minOccurs", "0")
            setElementXsdAttribute(choice, "maxOccurs", "unbounded")
            schema.allChildrenConcepts(conceptSchema).forEach { childConceptNode ->
                val enclosedConceptXmlSchemaName = childConceptNode.toXmlElementTagName()
                val elementRef = createAndAttachXsdElement(document, choice, "element")
                setElementXsdAttribute(elementRef, "name", enclosedConceptXmlSchemaName)
                setElementXsdAttribute(elementRef, "type", "${enclosedConceptXmlSchemaName}Type")
            }
            conceptSchema.inputFacets.forEach { inputFacetSchema ->
                    complexType.appendChild(createFacetAttributeReference(document, inputFacetSchema.inputFacet.facetName))
                }
        }
    }

    private fun attachAllConceptAttributes(document: Document, schemaElement: Element, schema: SchemaAccess) {
        attachComment(document, schemaElement, " ALL ATTRIBUTES ")
        schema.allConcepts().forEach { conceptNode ->
            conceptNode.inputFacets
                .forEach { inputFacetSchema ->
                    schemaElement.appendChild(createFacetAttributeElement(document, inputFacetSchema.inputFacet.facetName, inputFacetSchema.inputFacet))
                }
        }
    }

    private fun createFacetAttributeElement(document: Document, inputFacetName: FacetName, facet: InputFacet<*>): Element {
        val attributeGroupElement = createXsdElement(document, "attributeGroup")
        setElementXsdAttribute(attributeGroupElement, "name", inputFacetName.toXmlAttributeName())

        val attributeElement = createXsdElement(document, "attribute")
        setElementXsdAttribute(attributeElement, "name", inputFacetName.toXmlAttributeName())

        when(facet) {



//            is StringEnumerationFacet -> {
//                val simpleType = createAndAttachXsdElement(document, attributeElement, "simpleType")
//                val restriction = createAndAttachXsdElement(document, simpleType, "restriction")
//                setElementXsdAttribute(restriction, "base", "$xsdNamespacePrefix:string")
//                facet.enumerationOptions.forEach { enumerationValue ->
//                    val enumerationValueElement = createAndAttachXsdElement(document, restriction, "enumeration")
//                    setElementXsdAttribute(enumerationValueElement, "value", enumerationValue.name)
//                }
//            }
            is MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet -> setElementXsdAttribute(attributeElement, "type", "$xsdNamespacePrefix:string")
            is MandatoryNumberInputAndTemplateFacet -> setElementXsdAttribute(attributeElement, "type", "$xsdNamespacePrefix:integer")
            is MandatoryTextInputAndTemplateFacet -> setElementXsdAttribute(attributeElement, "type", "$xsdNamespacePrefix:string")
            is OptionalConceptIdentifierInputAndConceptNodeTemplateFacet -> setElementXsdAttribute(attributeElement, "type", "$xsdNamespacePrefix:string")
            is MandatoryNumberInputFacet -> setElementXsdAttribute(attributeElement, "type", "$xsdNamespacePrefix:integer")
            is MandatoryTextInputFacet -> setElementXsdAttribute(attributeElement, "type", "$xsdNamespacePrefix:string")
            is OptionalNumberInputFacet -> setElementXsdAttribute(attributeElement, "type", "$xsdNamespacePrefix:integer")
        }

        attributeGroupElement.appendChild(attributeElement)
        return attributeGroupElement
    }

    private fun createFacetAttributeReference(document: Document, inputFacetName: FacetName): Element {
        val attributeElement = createXsdElement(document, "attributeGroup")
        setElementXsdAttribute(attributeElement, "ref", inputFacetName.toXmlAttributeName())
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

    private fun ConceptSchema.toXmlElementTagName(): String {
        return CaseUtil.decapitalize(this.conceptName.name)
    }

//    private fun schemaAttributeType(facet: Facet): String {
//        return when(facet) {
//            is StringFacet -> "$xsdNamespacePrefix:string"
//            // IntegerNumberFacetType -> "$xsdNamespacePrefix:integer"
//            is BooleanFacet -> "$xsdNamespacePrefix:boolean"
//            // DirectoryFacetType -> "$xsdNamespacePrefix:string"
//            // FileFacetType -> "$xsdNamespacePrefix:string"
//            else -> throw IllegalArgumentException("FacetType is not supported: $facet")
//        }
//    }

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

    private fun FacetName.toXmlAttributeName(): String {
        return CaseUtil.decapitalize(this.name)
    }


}
