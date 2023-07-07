package ch.cassiamon.api.process.datacollection

interface DomainUnitDataCollectionHelper {
    fun <I: Any> createDomainUnitProcessInputData(inputDefinitionClass: Class<I>): DomainUnitDataCollection<I>
}
