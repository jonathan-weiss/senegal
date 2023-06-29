package ch.cassiamon.engine.domain.process

import ch.cassiamon.api.registration.DomainUnitProcessTargetFilesData
import ch.cassiamon.api.registration.DomainUnitProcessTargetFilesHelper
import ch.cassiamon.api.schema.SchemaAccess
import ch.cassiamon.engine.ProcessSession

class DomainUnitProcessTargetFilesDataHelperImpl(
    private val processSession: ProcessSession,
    private val schema: SchemaAccess,
    private val conceptEntries: Concepts
): DomainUnitProcessTargetFilesHelper {
    override fun <S : Any> createDomainUnitProcessTargetFilesData(schemaDefinitionClass: Class<S>): DomainUnitProcessTargetFilesData<S> {
        return DomainUnitProcessTargetFilesDataImpl(processSession, schemaDefinitionClass, schema, conceptEntries)
    }

}
