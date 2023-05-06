package ch.cassiamon.engine.schema.finder

import ch.cassiamon.pluginapi.registration.DomainUnit
import java.util.*

object DomainUnitFinder {

    fun findAllDomainUnits(): List<DomainUnit> {
        val domainUnitServiceLoader: ServiceLoader<DomainUnit> = ServiceLoader.load(DomainUnit::class.java)

        return domainUnitServiceLoader.toList()
    }

}
