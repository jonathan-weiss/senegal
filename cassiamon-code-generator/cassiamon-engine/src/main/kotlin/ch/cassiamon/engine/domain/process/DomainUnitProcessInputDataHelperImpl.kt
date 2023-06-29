package ch.cassiamon.engine.domain.process

import ch.cassiamon.api.registration.DomainUnitProcessInputDataHelper
import ch.cassiamon.api.registration.DomainUnitProcessInputData
import ch.cassiamon.api.schema.SchemaAccess
import ch.cassiamon.engine.ProcessSession

class DomainUnitProcessInputDataHelperImpl(private val processSession: ProcessSession, private val schemaAccess: SchemaAccess): DomainUnitProcessInputDataHelper {
    override fun <I : Any> createDomainUnitProcessInputData(
        inputDefinitionClass: Class<I>
    ): DomainUnitProcessInputData<I> {
        return DomainUnitProcessInputDataImpl(processSession, schemaAccess, inputDefinitionClass)
    }

}
