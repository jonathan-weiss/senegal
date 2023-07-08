package ch.cassiamon.engine.process.templating

import ch.cassiamon.api.process.templating.DomainUnitProcessTargetFilesData
import ch.cassiamon.api.process.templating.DomainUnitProcessTargetFilesHelper
import ch.cassiamon.engine.process.conceptgraph.ConceptGraph

class DomainUnitProcessTargetFilesDataHelperImpl(
    private val conceptGraph: ConceptGraph
): DomainUnitProcessTargetFilesHelper {
    override fun <S : Any> createDomainUnitProcessTargetFilesData(schemaDefinitionClass: Class<S>): DomainUnitProcessTargetFilesData<S> {
        return DomainUnitProcessTargetFilesDataImpl(schemaDefinitionClass, conceptGraph)
    }

}
