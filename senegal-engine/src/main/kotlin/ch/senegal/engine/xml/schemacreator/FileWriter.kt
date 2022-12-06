package ch.senegal.engine.xml.schemacreator

import java.nio.file.Path

object FileWriter {

    fun writeFile(file: Path, fileContent: String) {
        file.toFile().writeText(fileContent)
    }

}
