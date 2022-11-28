package ch.senegal.engine.xml

import org.junit.jupiter.api.Test
import javax.xml.XMLConstants
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

internal class SenegalSaxParserHandlerTest {

    @Test
    fun testSaxParser() {
        val factory: SAXParserFactory = SAXParserFactory.newInstance()
        factory.isNamespaceAware = true
        factory.isValidating = true
        println("Factory: isNamespaceAware:${factory.isNamespaceAware}, isValidating:${factory.isValidating}, isXIncludeAware:${factory.isXIncludeAware}")
        val saxParser: SAXParser = factory.newSAXParser()
        val saxParserHandler = SenegalSaxParserHandler()

        saxParser.parse("/Users/jweiss/Documents/Privat/senegal/senegal-engine/src/test/resources/ch.senegal.engine.xml/example.xml", saxParserHandler);
    }
}
