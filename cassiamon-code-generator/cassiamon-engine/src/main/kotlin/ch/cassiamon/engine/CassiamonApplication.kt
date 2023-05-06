package ch.cassiamon.engine

import ch.cassiamon.engine.schema.finder.RegistrarFinder


fun main() {
    val registrars = RegistrarFinder.findAllRegistrars()
    val processSession = ProcessSession(registrars = registrars)
    println("Registrars: [${registrars.joinToString { it.projectName.name }}]")


    val process = EngineProcess(processSession)
    process.runProcess()
}
