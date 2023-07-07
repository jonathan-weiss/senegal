package ch.cassiamon.api.process

import ch.cassiamon.api.process.datacollection.ConceptData
import ch.cassiamon.api.process.datacollection.DomainUnitDataCollectionHelper
import ch.cassiamon.api.process.datacollection.extensions.DataCollectionExtensionAccess
import ch.cassiamon.api.parameter.ParameterAccess
import ch.cassiamon.api.process.schema.DomainUnitSchemaHelper
import ch.cassiamon.api.process.schema.SchemaAccess
import ch.cassiamon.api.process.templating.DomainUnitProcessTargetFilesHelper
import ch.cassiamon.api.process.templating.TargetFilesCollector

abstract class DomainUnit<S: Any, I: Any>(private val schemaDefinitionClass: Class<S>, private val inputDefinitionClass: Class<I>) {
    fun createSchema(domainUnitSchemaHelper: DomainUnitSchemaHelper): SchemaAccess {
        return domainUnitSchemaHelper.createDomainUnitSchema(schemaDefinitionClass = schemaDefinitionClass)
    }

    fun processDomainUnitInputData(parameterAccess: ParameterAccess, domainUnitDataCollectionHelper: DomainUnitDataCollectionHelper): List<ConceptData> {
        val domainUnitProcessInputData = domainUnitDataCollectionHelper.createDomainUnitDataCollection(inputDefinitionClass = inputDefinitionClass)

        collectInputData(
            parameterAccess = parameterAccess,
            extensionAccess = domainUnitProcessInputData.getDataCollectionExtensionAccess(),
            dataCollector = domainUnitProcessInputData.getDataCollector()
        )
        return domainUnitProcessInputData.getCollectedData()
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

    abstract fun collectInputData(parameterAccess: ParameterAccess, extensionAccess: DataCollectionExtensionAccess, dataCollector: I)

    abstract fun collectTargetFiles(parameterAccess: ParameterAccess, schemaInstance: S, targetFilesCollector: TargetFilesCollector)
}
