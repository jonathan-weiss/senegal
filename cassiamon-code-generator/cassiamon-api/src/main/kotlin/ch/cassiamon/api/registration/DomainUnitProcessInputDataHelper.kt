package ch.cassiamon.api.registration

interface DomainUnitProcessInputDataHelper {
    fun <I: Any> createDomainUnitProcessInputData(inputDefinitionClass: Class<I>): DomainUnitProcessInputData<I>
}
