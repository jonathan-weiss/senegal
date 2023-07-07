package ch.cassiamon.engine

import ch.cassiamon.engine.process.EngineProcess
import ch.cassiamon.engine.process.ProcessSession
import ch.cassiamon.engine.process.finder.DomainUnitFinder


fun main() {
    val domainUnits = DomainUnitFinder.findAllDomainUnits()
    val processSession = ProcessSession(domainUnits = domainUnits)

    val process = EngineProcess(processSession)
    process.runProcess()
}
