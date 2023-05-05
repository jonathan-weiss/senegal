package ch.cassiamon.example

import ch.cassiamon.engine.EngineProcess
import ch.cassiamon.engine.EngineProcessHelpers
import ch.cassiamon.engine.parameters.ParameterSource
import ch.cassiamon.engine.parameters.StaticParameterSource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.pathString

class EngineProcessTest {

    private val testXmlDefinitionFileContent = """
        <?xml version="1.0" encoding="utf-8" ?>
        <cassiamon xmlns="https://cassiamon.ch/cassiamon-schemagic"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="https://cassiamon.ch/cassiamon-schemagic ./schema/cassiamon-schemagic-schema.xsd">
            <configuration/>
            <definitions>
                <testEntity testEntityName="Person">
                    <testEntityAttribute testEntityAttributeName="firstname"/>
                    <testEntityAttribute testEntityAttributeName="lastname"/>
                    <testEntityAttribute testEntityAttributeName="nickname"/>
                </testEntity>
                <testEntity testEntityName="Address">
                    <testEntityAttribute testEntityAttributeName="street"/>
                    <testEntityAttribute testEntityAttributeName="zip"/>
                </testEntity>
            </definitions>
        </cassiamon>
    """.trimIndent()

    private val expectedTemplateOutput = """
            Properties:
                TestEntityName: Person
                TestKotlinModelClassname: Person
                TestKotlinModelPackage: ch.senegal.entities
            direct access: Person

            SubNodes:

                Properties:
                    TestEntityAttributeName: firstname
                    TestEntityAttributeType: TEXT
                    TestKotlinFieldType: kotlin.String

                Properties:
                    TestEntityAttributeName: lastname
                    TestEntityAttributeType: NUMBER
                    TestKotlinFieldType: kotlin.Int

                Properties:
                    TestEntityAttributeName: nickname
                    TestEntityAttributeType: BOOLEAN
                    TestKotlinFieldType: kotlin.Boolean

            Properties:
                TestEntityName: Address
                TestKotlinModelClassname: Address
                TestKotlinModelPackage: ch.senegal.entities
            direct access: Address

            SubNodes:

                Properties:
                    TestEntityAttributeName: street
                    TestEntityAttributeType: TEXT

                Properties:
                    TestEntityAttributeName: zip
                    TestEntityAttributeType: TEXT


    """.trimIndent()


    private val loggingConfigurationClasspath = "/cassiamon-logging.properties"
    private val loggingConfiguration = """
            handlers= java.util.logging.ConsoleHandler
            .level= INFO
            java.util.logging.ConsoleHandler.level = FINE
            java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
            java.util.logging.SimpleFormatter.format=%5${'$'}s%n
    """.trimIndent()

    private val definitionDirectory = ExampleRegistrar.xmlDefinitionDirectory
    private val xmlFilename = ExampleRegistrar.xmlFilename
    private val defaultOutputDirectory = Paths.get("default/output/directory")
    private val definitionXmlFile = definitionDirectory.resolve(xmlFilename)

    private val classpathResourcesWithContent: Map<String, String> = mapOf(
        loggingConfigurationClasspath to loggingConfiguration
    )
    private val filePathsWithContent: Map<Path, String> = mapOf(
        definitionXmlFile to testXmlDefinitionFileContent
    )

    private val parameterMap: Map<String, String> = mapOf(
        "DefinitionDirectory" to definitionDirectory.pathString,
        "DefaultOutputDirectory" to defaultOutputDirectory.pathString,
        "XmlDefinitionFile" to definitionXmlFile.pathString,
    )



    @Test
    //@Disabled
    fun `run example registrar`() {
        val fileSystemAccess = StringBasedFileSystemAccess(classpathResourcesWithContent, filePathsWithContent)
        val parameterSources: List<ParameterSource> = listOf(StaticParameterSource(parameterMap))
        val engineProcessHelpers = EngineProcessHelpers(
            fileSystemAccess = fileSystemAccess,
            parameterSources = parameterSources
        )
        val registrars = listOf(TestRegistrar())
        println("Registrars: [${registrars.joinToString { it.projectName.name }}]")

        val process = EngineProcess(registrars, engineProcessHelpers)

        process.runProcess()

//        Assertions.assertEquals(
//            expectedTemplateOutput,
//            fileSystemAccess.fetchFileContent(defaultOutputDirectory.resolve("Person.kt"))
//        )
//        Assertions.assertEquals(
//            expectedTemplateOutput,
//            fileSystemAccess.fetchFileContent(defaultOutputDirectory.resolve("Address.kt"))
//        )

    }

    private fun byteIteratorAsString(byteIterator: ByteIterator): String {
        val byteList : MutableList<Byte> = mutableListOf()
        byteIterator.forEach { byte: Byte -> byteList.add(byte) }
        return byteList.toByteArray().toString(Charset.defaultCharset())
    }


}
