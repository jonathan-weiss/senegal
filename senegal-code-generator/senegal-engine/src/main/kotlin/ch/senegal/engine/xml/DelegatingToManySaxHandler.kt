package ch.senegal.engine.xml

import org.xml.sax.*
import org.xml.sax.helpers.DefaultHandler

class DelegatingToManySaxHandler(private val delegates: List<DefaultHandler>) : DefaultHandler() {
    init {
        require(delegates.isNotEmpty()) {
            "There must be at least one delegate in the list of delegates."
        }
    }

    @Throws(SAXException::class)
    override fun characters(ch: CharArray?, start: Int, length: Int) {
        delegates.forEach { it.characters(ch, start, length) }
    }


    override fun notationDecl(name: String?, publicId: String?, systemId: String?) {
        delegates.forEach { it.notationDecl(name, publicId, systemId) }
    }

    override fun processingInstruction(target: String?, data: String?) {
        delegates.forEach { it.processingInstruction(target, data)  }
    }

    @Throws(SAXException::class)
    override fun startDocument() {
        delegates.forEach { it.startDocument() }
    }
    @Throws(SAXException::class)
    override fun endDocument() {
        delegates.forEach { it.endDocument() }
    }

    override fun startPrefixMapping(prefix: String?, uri: String?) {
        delegates.forEach { it.startPrefixMapping(prefix, uri) }
    }

    override fun endPrefixMapping(prefix: String?) {
        delegates.forEach { it.endPrefixMapping(prefix) }
    }

    override fun setDocumentLocator(locator: Locator) {
        delegates.forEach { it.setDocumentLocator(locator) }
    }

    override fun skippedEntity(name: String?) {
        delegates.forEach { it.skippedEntity(name) }
    }


    @Throws(SAXException::class)
    override fun startElement(uri: String?, localName: String?, qName: String?, attr: Attributes?) {
        delegates.forEach { it.startElement(uri, localName, qName, attr) }
    }



    @Throws(SAXException::class)
    override fun endElement(uri: String?, localName: String?, qName: String?) {
        delegates.forEach { it.endElement(uri, localName, qName) }
    }

    override fun resolveEntity(publicId: String?, systemId: String?): InputSource {
        val inputSource = delegates.first().resolveEntity(publicId, systemId)
        delegates.filterIndexed { index, _ ->
            index > 0
        }.forEach {
            it.resolveEntity(publicId, systemId)
        }
        return inputSource
    }

    override fun unparsedEntityDecl(name: String?, publicId: String?, systemId: String?, notationName: String?) {
        delegates.forEach { it.unparsedEntityDecl(name, publicId, systemId, notationName) }
    }

    override fun ignorableWhitespace(ch: CharArray?, start: Int, length: Int) {
        delegates.forEach { it.ignorableWhitespace(ch, start, length) }
    }

    override fun warning(e: SAXParseException?) {
        delegates.forEach { it.warning(e) }
    }

    override fun error(e: SAXParseException?) {
        delegates.forEach { it.error(e) }
    }

    override fun fatalError(e: SAXParseException?) {
        delegates.forEach { it.fatalError(e) }
    }
}
