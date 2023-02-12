package ch.cassiamon.engine.schema.finder

import ch.cassiamon.pluginapi.registration.Registrar
import java.util.*

object RegistrarFinder {

    fun findAllRegistrars(): List<Registrar> {
        val registrarServiceLoader: ServiceLoader<Registrar> = ServiceLoader.load(Registrar::class.java)

        return registrarServiceLoader.toList()
    }

}
