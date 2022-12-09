package ch.senegal.engine.process

import ch.senegal.engine.xml.schemacreator.XmlSchemaInitializer

object SenegalProcess {

    fun runSenegalEngine() {
        XmlSchemaInitializer.initializeXmlSchemas()

        // TODO liste der sourcefiles sammeln über Parameter (ArgumentReader)
    }
}
