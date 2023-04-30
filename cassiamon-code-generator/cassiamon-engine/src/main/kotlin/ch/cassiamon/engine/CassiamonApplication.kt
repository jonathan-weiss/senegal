package ch.cassiamon.engine

import ch.cassiamon.engine.schema.finder.RegistrarFinder


fun main() {
    val engineProcessHelpers = EngineProcessHelpers()
    val registrars = RegistrarFinder.findAllRegistrars()
    println("Registrars: [${registrars.joinToString { it.projectName.name }}]")


    val process = EngineProcess(registrars, engineProcessHelpers)
    process.runProcess()
}
