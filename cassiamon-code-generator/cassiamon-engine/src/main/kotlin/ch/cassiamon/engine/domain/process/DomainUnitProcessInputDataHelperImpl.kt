package ch.cassiamon.engine.domain.process

import ch.cassiamon.api.registration.DomainUnitProcessInputDataHelper
import ch.cassiamon.api.registration.DomainUnitProcessInputData
import ch.cassiamon.api.registration.DomainUnitProcessTargetFilesData
import ch.cassiamon.engine.ProcessSession

class DomainUnitProcessInputDataHelperImpl(private val processSession: ProcessSession): DomainUnitProcessInputDataHelper {
    override fun <S : Any> createDomainUnitProcessInputData(schemaDefinitionClass: Class<S>): DomainUnitProcessInputData {
        return DomainUnitProcessInputDataImpl(processSession, schemaDefinitionClass)
    }

}
