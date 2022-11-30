package ch.senegal.engine.xml.schemacreator

import ch.senegal.engine.plugin.Concept
import ch.senegal.engine.plugin.Purpose
import java.nio.file.Path

object FileWriter {

    fun writeFile(file: Path, fileContent: String) {
        file.toFile().writeText(fileContent)
    }

}
