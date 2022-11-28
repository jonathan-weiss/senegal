package ch.senegal.engine.xml

import org.xml.sax.Attributes
import org.xml.sax.Locator
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler


class SenegalSaxParserHandler : DefaultHandler() {
    private val namespaces: MutableMap<String, String> = mutableMapOf();

    @Throws(SAXException::class)
    override fun characters(ch: CharArray?, start: Int, length: Int) {
        //println("characters: $ch, $start, $length -> ${ch?.slice(start .. start + length)?.joinToString("")}")
    }


    override fun notationDecl(name: String?, publicId: String?, systemId: String?) {
        println("notationDecl name=$name, publicId=$publicId, systemId=$systemId")
    }

    override fun processingInstruction(target: String?, data: String?) {
        println("processingInstruction target=$target, data=$data")
    }

    @Throws(SAXException::class)
    override fun startDocument() {
        println("startDocument")
    }
    @Throws(SAXException::class)
    override fun endDocument() {
        println("endDocument")
    }

    override fun startPrefixMapping(prefix: String?, uri: String?) {
        println("startPrefixMapping: prefix=$prefix, uri=$uri")
    }

    override fun endPrefixMapping(prefix: String?) {
        println("endPrefixMapping: prefix=$prefix")
    }

    override fun setDocumentLocator(locator: Locator) {
        super.setDocumentLocator(locator)
        println("setDocumentLocator: $locator, publicId=${locator.publicId}, systemId=${locator.systemId}, lineNumber=${locator.lineNumber}, columnNumber=${locator.columnNumber}")
    }

    override fun skippedEntity(name: String?) {
        println("skippedEntity: $name")
    }

    var elementCount = 0;

    @Throws(SAXException::class)
    override fun startElement(uri: String?, localName: String?, qName: String?, attr: Attributes?) {
        elementCount++
        println("  ${elementIdent()}startElement:$localName uri=$uri, lName=$localName, qName=$qName")
        attributeList(attr).forEach {
            println("  ${elementIdent()}+ $it")
        }
    }

    private fun attributeList(attr: Attributes?): List<Attribute> {
        if (attr == null) return emptyList()
        return (0..(attr.length - 1).coerceAtLeast(0))
            .map { index -> toAttribute(attr, index) }
    }

    private fun toAttribute(attr: Attributes, index: Int): Attribute {
        return Attribute(
            localName = attr.getLocalName(index),
            qName = attr.getQName(index),
            uri = attr.getURI(index),
            value = attr.getValue(index),
            type = attr.getType(index),
        )
    }

    private fun attributesSummary(attr: Attributes?): String {
        return attributeList(attr)
            .joinToString("\n") { it.toString() }
    }

    data class Attribute(
        val localName: String?,
        val qName: String?,
        val uri: String?,
        val value: String?,
        val type: String?,

        )

    @Throws(SAXException::class)
    override fun endElement(uri: String?, localName: String?, qName: String?) {
        elementCount--
        println("  ${elementIdent()}endElement:$localName uri=$uri, lName=$localName, qName=$qName")
    }

    private fun elementIdent(): String {
        return "    ".repeat(elementCount)
    }

}
