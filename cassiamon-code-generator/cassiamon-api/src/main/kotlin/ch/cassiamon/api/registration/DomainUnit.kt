package ch.cassiamon.api.registration

import ch.cassiamon.api.DomainUnitName
import ch.cassiamon.api.parameter.ParameterAccess

abstract class DomainUnit<S: Any>(val domainUnitName: DomainUnitName, private val schemaDefinitionClass: Class<S>) {
    fun processDomainUnitInputData(parameterAccess: ParameterAccess, domainUnitProcessInputDataHelper: DomainUnitProcessInputDataHelper): DomainUnitProcessInputData {
        val domainUnitProcessInputData = domainUnitProcessInputDataHelper.createDomainUnitProcessInputData(schemaDefinitionClass)

        collectInputData(
            parameterAccess = parameterAccess,
            extensionAccess = domainUnitProcessInputData.getInputDataExtensionAccess(),
            dataCollector = domainUnitProcessInputData.getDataCollector()
        )
        return domainUnitProcessInputData
    }

    fun processDomainUnitTargetFiles(parameterAccess: ParameterAccess, domainUnitProcessTargetFilesHelper: DomainUnitProcessTargetFilesHelper): TargetFilesCollector {
        val domainUnitProcessTargetFilesData = domainUnitProcessTargetFilesHelper.createDomainUnitProcessTargetFilesData(schemaDefinitionClass)
        collectTargetFiles(
            parameterAccess = parameterAccess,
            schemaInstance = domainUnitProcessTargetFilesData.getSchemaInstance(),
            targetFilesCollector = domainUnitProcessTargetFilesData.getTargetFilesCollector()
        )
        return domainUnitProcessTargetFilesData.getTargetFilesCollector()
    }

    abstract fun collectInputData(parameterAccess: ParameterAccess, extensionAccess: InputSourceExtensionAccess, dataCollector: InputSourceDataCollector)

    abstract fun collectTargetFiles(parameterAccess: ParameterAccess, schemaInstance: S, targetFilesCollector: TargetFilesCollector)
}
