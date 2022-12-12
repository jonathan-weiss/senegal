package ch.senegal.engine.xml

import ch.senegal.engine.model.MutableModelTree
import ch.senegal.engine.plugin.resolver.ResolvedPlugins
import java.nio.file.Path
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory
import kotlin.io.path.inputStream

object XmlFileParser {

    fun validateAndReadXmlFile(resolvedPlugins: ResolvedPlugins, xmlDefinitionFile: Path): MutableModelTree {
        val factory: SAXParserFactory = SAXParserFactory.newInstance()
        factory.isNamespaceAware = true
        factory.isValidating = true
        val saxParser: SAXParser = factory.newSAXParser()
        val modelTree = MutableModelTree(resolvedPlugins)
        val senegalSaxParser = SenegalSaxParserHandler(resolvedPlugins, modelTree)
        val saxParserHandler = DelegatingToManySaxHandler(listOf(PrinterHelperSaxHandler(), senegalSaxParser))


        xmlDefinitionFile.inputStream().use {
            saxParser.parse(it, saxParserHandler)
        }
        return modelTree
    }
}
