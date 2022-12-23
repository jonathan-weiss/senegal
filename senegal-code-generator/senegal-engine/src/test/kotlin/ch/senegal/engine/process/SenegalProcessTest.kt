package ch.senegal.engine.process

import ch.senegal.engine.parameters.ParameterSource
import ch.senegal.engine.parameters.PathConfigParameterName
import ch.senegal.engine.plugin.TestPluginFinder
import ch.senegal.engine.plugin.finder.PluginFinder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.pathString

class SenegalProcessTest {

    private val testXmlDefinitionFileContent = """
        <?xml version="1.0" encoding="utf-8" ?>
        <senegal xmlns="https://senegal.ch/senegal"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="https://senegal.ch/senegal ./schema/senegal-schema.xsd">
            <configuration testKotlinModelPackage="ch.senegal.entities"/>
            <definitions>
                <testEntity testEntityName="Person" testKotlinModelClassname="Person">
                    <testEntityAttribute testEntityAttributeName="firstname" testEntityAttributeType="TEXT" testKotlinFieldType="kotlin.String" />
                    <testEntityAttribute testEntityAttributeName="lastname" testEntityAttributeType="NUMBER" testKotlinFieldType="kotlin.Int"/>
                    <testEntityAttribute testEntityAttributeName="nickname" testEntityAttributeType="BOOLEAN" testKotlinFieldType="kotlin.Boolean"/>
                </testEntity>
                <testEntity testEntityName="Address" testKotlinModelClassname="Address">
                    <testEntityAttribute testEntityAttributeName="street" testEntityAttributeType="TEXT"/>
                    <testEntityAttribute testEntityAttributeName="zip" testEntityAttributeType="TEXT"/>
                </testEntity>
            </definitions>
        </senegal>
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

    private val definitionDirectory = Paths.get("definition/directory")
    private val defaultOutputDirectory = Paths.get("default/output/directory")
    private val definitionXmlFile = definitionDirectory.resolve("definition-file.xml")

    private val classpathResourcesWithContent: Map<String, String> = mapOf()
    private val filePathsWithContent: Map<Path, String> = mapOf(
        definitionXmlFile to testXmlDefinitionFileContent
    )
    private val parameterMap: Map<String, String> = mapOf(
        PathConfigParameterName.DefinitionDirectory.propertyName to definitionDirectory.pathString,
        PathConfigParameterName.DefaultOutputDirectory.propertyName to defaultOutputDirectory.pathString,
        PathConfigParameterName.XmlDefinitionFile.propertyName to definitionXmlFile.pathString,
    )


    @Test
    fun runSenegalEngine() {
        val virtualFileSystem = StaticStringBasedVirtualFileSystem(classpathResourcesWithContent, filePathsWithContent)
        val pluginFinder: PluginFinder = TestPluginFinder
        val parameterSources: List<ParameterSource> = listOf(StaticParameterSource(parameterMap))

        val process = SenegalProcess(
            pluginFinder = pluginFinder,
            virtualFileSystem = virtualFileSystem,
            parameterSources = parameterSources
        )

        process.runSenegalEngine()

        assertEquals(expectedTemplateOutput, virtualFileSystem.fetchFileContent(defaultOutputDirectory.resolve("Person.kt")))
        assertEquals(expectedTemplateOutput, virtualFileSystem.fetchFileContent(defaultOutputDirectory.resolve("Address.kt")))
    }
}
