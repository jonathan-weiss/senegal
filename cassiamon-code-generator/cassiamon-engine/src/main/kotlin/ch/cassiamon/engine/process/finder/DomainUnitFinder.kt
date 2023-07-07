package ch.cassiamon.engine.process.finder

import ch.cassiamon.api.process.DomainUnit
import java.util.*

object DomainUnitFinder {

    fun findAllDomainUnits(): List<DomainUnit<*, *>> {
        val domainUnitServiceLoader: ServiceLoader<DomainUnit<*, *>> = ServiceLoader.load(DomainUnit::class.java)

        return domainUnitServiceLoader.toList()
    }

}
