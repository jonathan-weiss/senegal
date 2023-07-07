package ch.cassiamon.engine

import ch.cassiamon.engine.domain.finder.DomainUnitFinder


fun main() {
    val domainUnits = DomainUnitFinder.findAllDomainUnits()
    val processSession = ProcessSession(domainUnits = domainUnits)

    val process = EngineProcess(processSession)
    process.runProcess()
}
