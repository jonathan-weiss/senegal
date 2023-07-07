package ch.cassiamon.engine.process.datacollection

import ch.cassiamon.api.process.datacollection.DomainUnitDataCollectionHelper
import ch.cassiamon.api.process.datacollection.DomainUnitDataCollection
import ch.cassiamon.api.process.schema.SchemaAccess
import ch.cassiamon.engine.process.ProcessSession

class DomainUnitDataCollectionHelperImpl(private val processSession: ProcessSession, private val schemaAccess: SchemaAccess):
    DomainUnitDataCollectionHelper {
    override fun <I : Any> createDomainUnitProcessInputData(
        inputDefinitionClass: Class<I>
    ): DomainUnitDataCollection<I> {
        return DomainUnitDataCollectionImpl(processSession, schemaAccess, inputDefinitionClass)
    }

}
