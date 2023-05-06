package ch.cassiamon.example

import ch.cassiamon.engine.EngineProcess
import ch.cassiamon.engine.ProcessSession
import ch.cassiamon.engine.parameters.ParameterSource
import ch.cassiamon.engine.parameters.StaticParameterSource
import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.DomainUnitName
import ch.cassiamon.api.model.ConceptIdentifier
import ch.cassiamon.api.model.ConceptModelGraph
import ch.cassiamon.api.model.facets.MandatoryTextInputAndTemplateFacet
import ch.cassiamon.api.registration.*
import ch.cassiamon.api.template.TargetGeneratedFileWithModel
import ch.cassiamon.api.template.TemplateRenderer
import ch.cassiamon.api.template.helper.StringContentByteIterator
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

    class TestDomainUnit: DomainUnit {
        companion object {
            val xmlDefinitionDirectory: Path = Paths.get("definition/directory")
            const val xmlFilename = "definition-file.xml"
            val defaultOutputDirectory: Path = Paths.get("default/output/directory")

        }

        private val testEntityConceptName = ConceptName.of("TestEntity")
        private val testEntityAttributeConceptName = ConceptName.of("TestEntityAttribute")
        private val testEntityNameInputFacet = MandatoryTextInputAndTemplateFacet.of("TestEntityName")
        private val testEntityAttributeNameInputFacet = MandatoryTextInputAndTemplateFacet.of("TestEntityAttributeName")

        override val domainUnitName: DomainUnitName
            get() = DomainUnitName.of("TestProject")

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

        override fun configureDataCollector(registration: InputSourceRegistrationApi) {
            registration {
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

        override fun configureSchema(registration: SchemaRegistrationApi) {
            registration {
                newRootConcept(testEntityConceptName) {

                    addFacet(testEntityNameInputFacet)

                    newChildConcept(testEntityAttributeConceptName) {
                        addFacet(testEntityAttributeNameInputFacet)
                    }
                }
            }
        }

        override fun configureTemplates(registration: TemplatesRegistrationApi) {
            registration {
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
        }


    }


}
