package ch.cassiamon.pluginapi.registration

interface RegistrationApi {

    fun configure(registration: Registration.() -> Unit)
}
