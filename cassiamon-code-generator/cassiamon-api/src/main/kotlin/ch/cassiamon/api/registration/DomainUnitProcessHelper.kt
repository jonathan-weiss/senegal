package ch.cassiamon.api.registration

import ch.cassiamon.api.parameter.ParameterAccess

interface DomainUnitProcessHelper {
    fun <S: Any> createDomainUnitProcessData(schemaDefinitionClass: Class<S>): DomainUnitProcessSession<S>

    fun getParameterAccess(): ParameterAccess
}
