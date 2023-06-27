package ch.cassiamon.example

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.DomainUnitName
import ch.cassiamon.api.model.ConceptIdentifier
import ch.cassiamon.api.model.ConceptModelGraph
import ch.cassiamon.api.model.ConceptModelNode
import ch.cassiamon.api.model.facets.TextFacets
import ch.cassiamon.api.parameter.ParameterAccess
import ch.cassiamon.api.registration.*
import ch.cassiamon.api.template.TargetGeneratedFileWithModel
import ch.cassiamon.api.template.TemplateRenderer
import ch.cassiamon.api.template.helper.StringContentByteIterator
import ch.cassiamon.domain.example.ExampleExtensions
import ch.cassiamon.domain.example.ExampleTemplate
import ch.cassiamon.engine.EngineProcess
import ch.cassiamon.engine.ProcessSession
import ch.cassiamon.engine.parameters.ParameterSource
import ch.cassiamon.engine.parameters.StaticParameterSource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Path
import java.nio.file.Paths

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
            - TestEntityName: MeinTestkonzeptName
        Properties:
            - TestEntityName: MeinZweitesTestkonzeptName
        Properties:
            - TestEntityName: Person
        SubNode-Properties:
            - TestEntityAttributeName: firstname
        SubNode-Properties:
            - TestEntityAttributeName: lastname
        SubNode-Properties:
            - TestEntityAttributeName: nickname
        Properties:
            - TestEntityName: Address
        SubNode-Properties:
            - TestEntityAttributeName: street
        SubNode-Properties:
            - TestEntityAttributeName: zip
    """.trimIndent()


    private val loggingConfigurationClasspath = "/cassiamon-logging.properties"
    private val loggingConfiguration = """
            handlers= java.util.logging.ConsoleHandler
            .level= INFO
            java.util.logging.ConsoleHandler.level = FINE
            java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
            java.util.logging.SimpleFormatter.format=%5${'$'}s%n
    """.trimIndent()

    private val definitionDirectory = TestDomainUnit.xmlDefinitionDirectory
    private val xmlFilename = TestDomainUnit.xmlFilename
    private val defaultOutputDirectory = TestDomainUnit.defaultOutputDirectory
    private val definitionXmlFile = definitionDirectory.resolve(xmlFilename)

    private val classpathResourcesWithContent: Map<String, String> = mapOf(
        loggingConfigurationClasspath to loggingConfiguration
    )
    private val filePathsWithContent: Map<Path, String> = mapOf(
        definitionXmlFile to testXmlDefinitionFileContent
    )

    private val parameterMap: Map<String, String> = emptyMap()



    @Test
    fun `run test domainUnit`() {

        val domainUnits = listOf(TestDomainUnit())
        val fileSystemAccess = StringBasedFileSystemAccess(classpathResourcesWithContent, filePathsWithContent)
        val parameterSources: List<ParameterSource> = listOf(StaticParameterSource(parameterMap))
        val processSession = ProcessSession(
            domainUnits = domainUnits,
            fileSystemAccess = fileSystemAccess,
            parameterSources = parameterSources
        )

        val process = EngineProcess(processSession)

        process.runProcess()

        Assertions.assertTrue(fileSystemAccess.fileExists(defaultOutputDirectory.resolve("index.txt")))
        Assertions.assertTrue(fileSystemAccess.fileExists(defaultOutputDirectory.resolve("Person.txt")))
        Assertions.assertTrue(fileSystemAccess.fileExists(defaultOutputDirectory.resolve("Address.txt")))


        Assertions.assertEquals(
            expectedTemplateOutput,
            fileSystemAccess.fetchFileContent(defaultOutputDirectory.resolve("index.txt"))
        )

    }

    interface TestSchema {
        fun getEntities(): List<TestEntityConcept>
    }

    interface TestEntityConcept {
        fun getEntityName(): String
        fun getAttributes(): List<TestEntityAttributeConcept>

    }

    interface TestEntityAttributeConcept {
        fun getAttributeName(): String

    }

    class TestDomainUnit: DefaultDomainUnit<TestSchema>(
        domainUnitName = DomainUnitName.of("TestProject"),
        schemaDefinitionClass = TestSchema::class.java
    ) {
        companion object {
            val xmlDefinitionDirectory: Path = Paths.get("definition/directory")
            const val xmlFilename = "definition-file.xml"
            val defaultOutputDirectory: Path = Paths.get("default/output/directory")

        }

        override val defaultXmlPaths: Set<Path> = setOf(xmlDefinitionDirectory.resolve(xmlFilename))

        private val testEntityConceptName = ConceptName.of("TestEntity")
        private val testEntityNameInputFacet = TextFacets.ofMandatoryInputAndTemplate("TestEntityName")

        override fun collectInputData(
            parameterAccess: ParameterAccess,
            extensionAccess: InputSourceExtensionAccess,
            dataCollector: InputSourceDataCollector
        ) {

            dataCollector
                .newConceptData(testEntityConceptName, ConceptIdentifier.of("MeinTestkonzept"))
                .addFacetValue(testEntityNameInputFacet.facetValue( "MeinTestkonzeptName"))
                .attach()

            dataCollector
                .newConceptData(testEntityConceptName, ConceptIdentifier.of("MeinZweitesTestkonzept"))
                .addFacetValue(testEntityNameInputFacet.facetValue( "MeinZweitesTestkonzeptName"))
                .attach()

            super.collectInputData(parameterAccess, extensionAccess, dataCollector)


        }

        override fun collectTargetFiles(
            parameterAccess: ParameterAccess,
            schemaInstance: TestSchema,
            targetFilesCollector: TargetFilesCollector
        ) {
            schemaInstance
                .getEntities()
                .forEach { entity ->
                    val targetFile = defaultOutputDirectory.resolve("${entity.getEntityName()}.txt")
                    targetFilesCollector.addFile(targetFile, entityContent(listOf(entity)))
                }

            targetFilesCollector.addFile(defaultOutputDirectory.resolve("index.txt"), entityContent(schemaInstance.getEntities()))
        }

        private fun entityContent(entities: List<TestEntityConcept>): String {
            var content = ""

            entities.forEach { entity ->
                content += """
                    
                    Properties:
                        - TestEntityName: ${entity.getEntityName()}
                    """.trimIndent()

                entity.getAttributes().forEach { childModel ->
                    content += """
                        
                        SubNode-Properties:
                            - TestEntityAttributeName: ${childModel.getAttributeName()}
                    """.trimIndent()
                }
            }

            return content
        }
    }
}
