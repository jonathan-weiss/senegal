package ch.cassiamon.api.registration

import ch.cassiamon.api.DomainUnitName
import ch.cassiamon.api.parameter.ParameterAccess

abstract class DomainUnit<S: Any>(val domainUnitName: DomainUnitName, private val schemaDefinitionClass: Class<S>) {
    fun processDomainUnit(domainUnitProcessHelper: DomainUnitProcessHelper) {
        val domainUnitProcessData = domainUnitProcessHelper.createDomainUnitProcessData(schemaDefinitionClass)
        collectInputData(
            parameterAccess = domainUnitProcessHelper.getParameterAccess(),
            extensionAccess = domainUnitProcessData.getInputDataExtensionAccess(),
            dataCollector = domainUnitProcessData.getDataCollector()
        )
        collectTargetFiles(
            parameterAccess = domainUnitProcessHelper.getParameterAccess(),
            schemaInstance = domainUnitProcessData.getSchemaInstance(),
            targetFilesCollector = domainUnitProcessData.getTargetFilesCollector()
        )
    }

    abstract fun collectInputData(parameterAccess: ParameterAccess, extensionAccess: InputSourceExtensionAccess, dataCollector: InputSourceDataCollector)

    abstract fun collectTargetFiles(parameterAccess: ParameterAccess, schemaInstance: S, targetFilesCollector: TargetFilesCollector)
}
