package ch.cassiamon.engine.domain.process

import ch.cassiamon.api.parameter.ParameterAccess
import ch.cassiamon.api.registration.DomainUnitProcessSession
import ch.cassiamon.api.registration.DomainUnitProcessHelper
import ch.cassiamon.engine.ProcessSession

class DomainUnitProcessHelperImpl(private val processSession: ProcessSession): DomainUnitProcessHelper {

    override fun <S : Any> createDomainUnitProcessData(schemaDefinitionClass: Class<S>): DomainUnitProcessSession<S> {
        return DomainUnitProcessSessionImpl(processSession, schemaDefinitionClass)
    }

    override fun getParameterAccess(): ParameterAccess {
        return processSession.parameterAccess
    }
}
