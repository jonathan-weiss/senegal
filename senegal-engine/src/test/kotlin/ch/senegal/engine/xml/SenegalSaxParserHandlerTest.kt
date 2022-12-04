package ch.senegal.engine.xml

import org.junit.jupiter.api.Test
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

internal class SenegalSaxParserHandlerTest {

    private val testXml = "/ch/senegal/engine/xml/my-example.xml"

    @Test
    fun testSaxParser() {
        val factory: SAXParserFactory = SAXParserFactory.newInstance()
        factory.isNamespaceAware = true
        factory.isValidating = true
        val saxParser: SAXParser = factory.newSAXParser()
        val saxParserHandler = SenegalSaxParserHandler()


        SenegalSaxParserHandlerTest::class.java
            .getResourceAsStream(testXml)?.use { xmlFileInputStream ->
                saxParser.parse(xmlFileInputStream, saxParserHandler);
            }
    }
}
