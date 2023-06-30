package ch.cassiamon.api.registration

import ch.cassiamon.api.DomainUnitName
import ch.cassiamon.api.extensions.ExtensionName
import ch.cassiamon.api.parameter.ParameterAccess
import java.nio.file.Paths

abstract class DefaultDomainUnit<S: Any>(domainUnitName: DomainUnitName, schemaDefinitionClass: Class<S>)
    : DomainUnit<S, DefaultConceptDataCollector>(domainUnitName, schemaDefinitionClass, DefaultConceptDataCollector::class.java) {
    private val defaultInputExtensionName = ExtensionName.of("XmlSchemagicInputExtension")
    open val defaultXmlPaths = setOf(Paths.get("input-data").resolve("input-data.xml"))

    override fun collectInputData(parameterAccess: ParameterAccess, extensionAccess: InputSourceExtensionAccess, dataCollector: DefaultConceptDataCollector)
    {
        extensionAccess.dataCollectionWithFilesInputSourceExtension(
            extensionName = defaultInputExtensionName,
            inputFiles = defaultXmlPaths,
        )
    }
}
