package ch.cassiamon.example

import ch.cassiamon.engine.EngineProcess
import ch.cassiamon.engine.EngineProcessHelpers
import ch.cassiamon.engine.parameters.ParameterSource
import ch.cassiamon.engine.parameters.StaticParameterSource
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.ProjectName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelGraph
import ch.cassiamon.pluginapi.model.facets.MandatoryTextInputAndTemplateFacet
import ch.cassiamon.pluginapi.registration.Registrar
import ch.cassiamon.pluginapi.registration.RegistrationApi
import ch.cassiamon.pluginapi.template.TargetGeneratedFileWithModel
import ch.cassiamon.pluginapi.template.TemplateRenderer
import ch.cassiamon.pluginapi.template.helper.StringContentByteIterator
import ch.cassiamon.xml.schemagic.XmlSchemagicFactory
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

    private val definitionDirectory = TestRegistrar.xmlDefinitionDirectory
    private val xmlFilename = TestRegistrar.xmlFilename
    private val defaultOutputDirectory = TestRegistrar.defaultOutputDirectory
    private val definitionXmlFile = definitionDirectory.resolve(xmlFilename)

    private val classpathResourcesWithContent: Map<String, String> = mapOf(
        loggingConfigurationClasspath to loggingConfiguration
    )
    private val filePathsWithContent: Map<Path, String> = mapOf(
        definitionXmlFile to testXmlDefinitionFileContent
    )

    private val parameterMap: Map<String, String> = emptyMap()



    @Test
    fun `run test registrar`() {

        val registrars = listOf(TestRegistrar())
        val fileSystemAccess = StringBasedFileSystemAccess(classpathResourcesWithContent, filePathsWithContent)
        val parameterSources: List<ParameterSource> = listOf(StaticParameterSource(parameterMap))
        val engineProcessHelpers = EngineProcessHelpers(
            registrars = registrars,
            fileSystemAccess = fileSystemAccess,
            parameterSources = parameterSources
        )

        val process = EngineProcess(engineProcessHelpers)

        process.runProcess()

        Assertions.assertTrue(fileSystemAccess.fileExists(defaultOutputDirectory.resolve("index.txt")))
        Assertions.assertTrue(fileSystemAccess.fileExists(defaultOutputDirectory.resolve("Person.txt")))
        Assertions.assertTrue(fileSystemAccess.fileExists(defaultOutputDirectory.resolve("Address.txt")))


        Assertions.assertEquals(
            expectedTemplateOutput,
            fileSystemAccess.fetchFileContent(defaultOutputDirectory.resolve("index.txt"))
        )

    }

    class TestRegistrar: Registrar(ProjectName.of("TestProject")) {
        companion object {
            val xmlDefinitionDirectory: Path = Paths.get("definition/directory")
            const val xmlFilename = "definition-file.xml"
            val defaultOutputDirectory: Path = Paths.get("default/output/directory")

        }

        private val testEntityConceptName = ConceptName.of("TestEntity")
        private val testEntityAttributeConceptName = ConceptName.of("TestEntityAttribute")
        private val testEntityNameInputFacet = MandatoryTextInputAndTemplateFacet.of("TestEntityName")
        private val testEntityAttributeNameInputFacet = MandatoryTextInputAndTemplateFacet.of("TestEntityAttributeName")

        private fun modelDescriptionContent(targetGeneratedFileWithModel: TargetGeneratedFileWithModel): String {
            var content = ""

            targetGeneratedFileWithModel.model.forEach {model ->
                content += """
                    
                    Properties:
                        - TestEntityName: ${model.templateFacetValues.facetValue(testEntityNameInputFacet)}
                    """.trimIndent()

                    model.children(testEntityAttributeConceptName).forEach { childModel ->
                        content += """
                        
                        SubNode-Properties:
                            - TestEntityAttributeName: ${childModel.templateFacetValues.facetValue(testEntityAttributeNameInputFacet)}
                    """.trimIndent()
                    }
            }

            return content
        }

        override fun configure(registrationApi: RegistrationApi) {
            registrationApi.configureSchema {
                newRootConcept(testEntityConceptName) {

                    addFacet(testEntityNameInputFacet)

                    newChildConcept(testEntityAttributeConceptName) {
                        addFacet(testEntityAttributeNameInputFacet)
                    }
                }
            }

            registrationApi.configureTemplates {

                // test a file generation per node
                newTemplate { conceptModelGraph: ConceptModelGraph ->
                    val targetFiles: Set<TargetGeneratedFileWithModel> = conceptModelGraph
                        .conceptModelNodesByConceptName(testEntityConceptName)
                        .map { templateNode ->
                            val entityName = templateNode.templateFacetValues.facetValue(testEntityNameInputFacet)
                            return@map TargetGeneratedFileWithModel(defaultOutputDirectory.resolve("$entityName.txt"), listOf(templateNode))
                        }.toSet()


                    return@newTemplate TemplateRenderer(targetFiles) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
                        return@TemplateRenderer StringContentByteIterator(modelDescriptionContent(targetGeneratedFileWithModel))
                    }
                }

                // test a single node file generation
                newTemplate { templateNodesProvider ->

                    val templateNodes = templateNodesProvider
                        .conceptModelNodesByConceptName(testEntityConceptName)

                    return@newTemplate TemplateRenderer(setOf(TargetGeneratedFileWithModel(defaultOutputDirectory.resolve("index.txt"), templateNodes))) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
                        return@TemplateRenderer StringContentByteIterator(modelDescriptionContent(targetGeneratedFileWithModel))
                    }
                }

            }

            registrationApi.configureDataCollector {
                val dataCollector = receiveDataCollector()

                dataCollector
                    .newConceptData(testEntityConceptName, ConceptIdentifier.of("MeinTestkonzept"))
                    .addFacetValue(testEntityNameInputFacet.facetValue( "MeinTestkonzeptName"))
                    .attach()

                dataCollector
                    .newConceptData(testEntityConceptName, ConceptIdentifier.of("MeinZweitesTestkonzept"))
                    .addFacetValue(testEntityNameInputFacet.facetValue( "MeinZweitesTestkonzeptName"))
                    .attach()

                XmlSchemagicFactory.parseXml(receiveSchema(), dataCollector, xmlDefinitionDirectory, xmlFilename, receiveFileSystemAccess(), receiveLoggerFacade(), receiveParameterAccess())

            }
        }
    }


}
