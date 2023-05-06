package ch.cassiamon.engine

import ch.cassiamon.engine.schema.finder.DomainUnitFinder


fun main() {
    val domainUnits = DomainUnitFinder.findAllDomainUnits()
    val processSession = ProcessSession(domainUnits = domainUnits)
    println("DomainUnits: [${domainUnits.joinToString { it.domainUnitName.name }}]")


    val process = EngineProcess(processSession)
    process.runProcess()
}
