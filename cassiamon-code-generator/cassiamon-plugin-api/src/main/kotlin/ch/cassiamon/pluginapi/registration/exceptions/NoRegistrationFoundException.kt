package ch.cassiamon.pluginapi.registration.exceptions

import ch.cassiamon.pluginapi.registration.SchemaRegistration

class NoRegistrationFoundException(): SchemaException(
    """Could not find a implementation of the interface '${SchemaRegistration::javaClass}'. 
       Do you have the cassiamon engine on your classpath as dependency? 
    """.trimMargin()
) {

}
