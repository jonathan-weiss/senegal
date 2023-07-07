package ch.cassiamon.api.process

import ch.cassiamon.api.process.datacollection.defaults.DefaultConceptDataCollector
import ch.cassiamon.api.process.datacollection.extensions.DataCollectionExtensionAccess
import ch.cassiamon.api.extensions.ExtensionName
import ch.cassiamon.api.parameter.ParameterAccess
import java.nio.file.Paths

abstract class DefaultDomainUnit<S: Any>(schemaDefinitionClass: Class<S>)
    : DomainUnit<S, DefaultConceptDataCollector>(schemaDefinitionClass, DefaultConceptDataCollector::class.java) {
    private val defaultDataCollectionExtensionName = ExtensionName.of("XmlSchemagicInputExtension")
    open val defaultXmlPaths = setOf(Paths.get("input-data").resolve("input-data.xml"))

    override fun collectInputData(parameterAccess: ParameterAccess, extensionAccess: DataCollectionExtensionAccess, dataCollector: DefaultConceptDataCollector)
    {
        extensionAccess.collectWithDataCollectionFromFilesExtension(
            extensionName = defaultDataCollectionExtensionName,
            inputFiles = defaultXmlPaths,
        )
    }
}
