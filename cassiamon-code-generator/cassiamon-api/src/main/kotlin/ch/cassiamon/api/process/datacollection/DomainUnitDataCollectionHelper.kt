package ch.cassiamon.api.process.datacollection

interface DomainUnitDataCollectionHelper {
    fun <I: Any> createDomainUnitDataCollection(inputDefinitionClass: Class<I>): DomainUnitDataCollection<I>
}
