package ch.senegal.engine.xml

import org.xml.sax.Attributes
import org.xml.sax.Locator
import org.xml.sax.SAXException
import org.xml.sax.ext.DefaultHandler2
import org.xml.sax.helpers.DefaultHandler

class PrinterHelperSaxHandler : DefaultHandler2() {

    var elementLevel = 0;

    private fun printStep(message: String) {
        println(message)
    }

    @Throws(SAXException::class)
    override fun characters(ch: CharArray?, start: Int, length: Int) {
        //println("characters: $ch, $start, $length -> ${ch?.slice(start .. start + length)?.joinToString("")}")
    }


    override fun notationDecl(name: String?, publicId: String?, systemId: String?) {
        printStep("notationDecl name=$name, publicId=$publicId, systemId=$systemId")
    }

    override fun processingInstruction(target: String?, data: String?) {
        printStep("processingInstruction target=$target, data=$data")
    }

    @Throws(SAXException::class)
    override fun startDocument() {
        printStep("startDocument")
    }
    @Throws(SAXException::class)
    override fun endDocument() {
        printStep("endDocument")
    }

    override fun startPrefixMapping(prefix: String?, uri: String?) {
        printStep("startPrefixMapping: prefix=$prefix, uri=$uri")
    }

    override fun endPrefixMapping(prefix: String?) {
        printStep("endPrefixMapping: prefix=$prefix")
    }

    override fun setDocumentLocator(locator: Locator) {
        printStep("setDocumentLocator: $locator, publicId=${locator.publicId}, systemId=${locator.systemId}, lineNumber=${locator.lineNumber}, columnNumber=${locator.columnNumber}")
    }

    override fun skippedEntity(name: String?) {
        printStep("skippedEntity: $name")
    }


    @Throws(SAXException::class)
    override fun startElement(uri: String?, localName: String?, qName: String?, attr: Attributes?) {
        elementLevel++
        printStep("  ${elementIdent()}startElement:$localName uri=$uri, lName=$localName, qName=$qName")
        Attribute.attributeList(attr).forEach {
            printStep("  ${elementIdent()}+ $it")
        }
    }



    @Throws(SAXException::class)
    override fun endElement(uri: String?, localName: String?, qName: String?) {
        println("  ${elementIdent()}endElement:$localName uri=$uri, lName=$localName, qName=$qName")
        elementLevel--
    }

    private fun elementIdent(): String {
        return "    ".repeat(elementLevel)
    }

}
