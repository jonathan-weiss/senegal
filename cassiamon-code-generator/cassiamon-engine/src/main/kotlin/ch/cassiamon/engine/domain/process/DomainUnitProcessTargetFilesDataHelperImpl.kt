package ch.cassiamon.engine.domain.process

import ch.cassiamon.api.registration.DomainUnitProcessTargetFilesData
import ch.cassiamon.api.registration.DomainUnitProcessTargetFilesHelper
import ch.cassiamon.engine.domain.process.conceptresolver.ResolvedConcepts

class DomainUnitProcessTargetFilesDataHelperImpl(
    private val conceptEntries: ResolvedConcepts
): DomainUnitProcessTargetFilesHelper {
    override fun <S : Any> createDomainUnitProcessTargetFilesData(schemaDefinitionClass: Class<S>): DomainUnitProcessTargetFilesData<S> {
        return DomainUnitProcessTargetFilesDataImpl(schemaDefinitionClass, conceptEntries)
    }

}
