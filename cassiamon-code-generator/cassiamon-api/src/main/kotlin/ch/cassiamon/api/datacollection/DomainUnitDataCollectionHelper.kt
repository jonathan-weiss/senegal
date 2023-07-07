package ch.cassiamon.api.datacollection

interface DomainUnitDataCollectionHelper {
    fun <I: Any> createDomainUnitProcessInputData(inputDefinitionClass: Class<I>): DomainUnitDataCollection<I>
}
