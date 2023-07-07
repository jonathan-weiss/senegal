package ch.cassiamon.api.registration

import ch.cassiamon.api.datacollection.ConceptData
import ch.cassiamon.api.datacollection.DomainUnitDataCollectionHelper
import ch.cassiamon.api.parameter.ParameterAccess
import ch.cassiamon.api.schema.DomainUnitSchemaHelper
import ch.cassiamon.api.schema.SchemaAccess
import ch.cassiamon.api.templating.DomainUnitProcessTargetFilesHelper
import ch.cassiamon.api.templating.TargetFilesCollector

abstract class DomainUnit<S: Any, I: Any>(private val schemaDefinitionClass: Class<S>, private val inputDefinitionClass: Class<I>) {
    fun createSchema(domainUnitSchemaHelper: DomainUnitSchemaHelper): SchemaAccess {
        return domainUnitSchemaHelper.createDomainUnitSchema(schemaDefinitionClass = schemaDefinitionClass)
    }

    fun processDomainUnitInputData(parameterAccess: ParameterAccess, domainUnitDataCollectionHelper: DomainUnitDataCollectionHelper): List<ConceptData> {
        val domainUnitProcessInputData = domainUnitDataCollectionHelper.createDomainUnitProcessInputData(inputDefinitionClass = inputDefinitionClass)

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
