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
import ch.cassiamon.pluginapi.model.facets.MandatoryTextInputFacet
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
    fun `run example registrar`() {

        val fileSystemAccess = StringBasedFileSystemAccess(classpathResourcesWithContent, filePathsWithContent)
        val parameterSources: List<ParameterSource> = listOf(StaticParameterSource(parameterMap))
        val engineProcessHelpers = EngineProcessHelpers(
            fileSystemAccess = fileSystemAccess,
            parameterSources = parameterSources
        )
        val registrars = listOf(TestRegistrar())

        val process = EngineProcess(registrars, engineProcessHelpers)

        process.runProcess()

        Assertions.assertTrue(
            fileSystemAccess.fetchFileContent(defaultOutputDirectory.resolve("index.json")).isNotBlank()
        )

        Assertions.assertTrue(
            fileSystemAccess.fetchFileContent(defaultOutputDirectory.resolve("Person.json")).isNotBlank()
        )

        Assertions.assertTrue(
            fileSystemAccess.fetchFileContent(defaultOutputDirectory.resolve("Address.json")).isNotBlank()
        )


//        Assertions.assertEquals(
//            expectedTemplateOutput,
//            fileSystemAccess.fetchFileContent(defaultOutputDirectory.resolve("index.json"))
//        )

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
        private val testEntityAttributeNameInputFacet = MandatoryTextInputFacet.of("TestEntityAttributeName")


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
                            return@map TargetGeneratedFileWithModel(defaultOutputDirectory.resolve("$entityName.json"), listOf(templateNode))
                        }.toSet()


                    return@newTemplate TemplateRenderer(targetFiles) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
                        return@TemplateRenderer StringContentByteIterator(
                            "content of ${targetGeneratedFileWithModel.targetFile} is ${targetGeneratedFileWithModel.model}"
                        )
                    }
                }

                // test a single node file generation
                newTemplate { templateNodesProvider ->

                    val templateNodes = templateNodesProvider
                        .conceptModelNodesByConceptName(testEntityConceptName)

                    return@newTemplate TemplateRenderer(setOf(TargetGeneratedFileWithModel(defaultOutputDirectory.resolve("index.json"), templateNodes))) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
                        return@TemplateRenderer StringContentByteIterator(
                            "content of ${targetGeneratedFileWithModel.targetFile} is ${targetGeneratedFileWithModel.model}"
                        )
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
