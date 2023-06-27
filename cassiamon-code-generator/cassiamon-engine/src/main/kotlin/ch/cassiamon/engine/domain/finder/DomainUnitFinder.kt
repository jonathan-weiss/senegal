package ch.cassiamon.engine.domain.finder

import ch.cassiamon.api.registration.DomainUnit
import java.util.*

object DomainUnitFinder {

    fun findAllDomainUnits(): List<DomainUnit<*>> {
        val domainUnitServiceLoader: ServiceLoader<DomainUnit<*>> = ServiceLoader.load(DomainUnit::class.java)

        return domainUnitServiceLoader.toList()
    }

}
