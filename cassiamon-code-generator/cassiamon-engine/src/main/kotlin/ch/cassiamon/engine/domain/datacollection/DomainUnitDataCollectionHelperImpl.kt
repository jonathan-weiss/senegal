package ch.cassiamon.engine.domain.datacollection

import ch.cassiamon.api.datacollection.DomainUnitDataCollectionHelper
import ch.cassiamon.api.datacollection.DomainUnitDataCollection
import ch.cassiamon.api.schema.SchemaAccess
import ch.cassiamon.engine.ProcessSession

class DomainUnitDataCollectionHelperImpl(private val processSession: ProcessSession, private val schemaAccess: SchemaAccess):
    DomainUnitDataCollectionHelper {
    override fun <I : Any> createDomainUnitProcessInputData(
        inputDefinitionClass: Class<I>
    ): DomainUnitDataCollection<I> {
        return DomainUnitDataCollectionImpl(processSession, schemaAccess, inputDefinitionClass)
    }

}
