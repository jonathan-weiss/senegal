package ch.cassiamon.api.registration

import ch.cassiamon.api.DomainUnitName
import ch.cassiamon.api.parameter.ParameterAccess
import ch.cassiamon.api.schema.SchemaAccess

abstract class DomainUnit<S: Any, I: Any>(val domainUnitName: DomainUnitName, private val schemaDefinitionClass: Class<S>, private val inputDefinitionClass: Class<I>) {
    fun createSchema(domainUnitSchemaHelper: DomainUnitSchemaHelper): SchemaAccess {
        return domainUnitSchemaHelper.createDomainUnitSchema(schemaDefinitionClass = schemaDefinitionClass)
    }

    fun processDomainUnitInputData(parameterAccess: ParameterAccess, domainUnitProcessInputDataHelper: DomainUnitProcessInputDataHelper): ConceptEntries {
        val domainUnitProcessInputData = domainUnitProcessInputDataHelper.createDomainUnitProcessInputData(inputDefinitionClass = inputDefinitionClass)

        collectInputData(
            parameterAccess = parameterAccess,
            extensionAccess = domainUnitProcessInputData.getInputDataExtensionAccess(),
            dataCollector = domainUnitProcessInputData.getDataCollector()
        )
        return domainUnitProcessInputData.provideConceptEntries()
    }

    fun processDomainUnitTargetFiles(parameterAccess: ParameterAccess, domainUnitProcessTargetFilesHelper: DomainUnitProcessTargetFilesHelper): TargetFilesCollector {
        val domainUnitProcessTargetFilesData = domainUnitProcessTargetFilesHelper.createDomainUnitProcessTargetFilesData(schemaDefinitionClass = schemaDefinitionClass)
        collectTargetFiles(
            parameterAccess = parameterAccess,
            schemaInstance = domainUnitProcessTargetFilesData.getSchemaInstance(),
            targetFilesCollector = domainUnitProcessTargetFilesData.getTargetFilesCollector()
        )
        return domainUnitProcessTargetFilesData.getTargetFilesCollector()
    }

    abstract fun collectInputData(parameterAccess: ParameterAccess, extensionAccess: InputSourceExtensionAccess, dataCollector: I)

    abstract fun collectTargetFiles(parameterAccess: ParameterAccess, schemaInstance: S, targetFilesCollector: TargetFilesCollector)
}
