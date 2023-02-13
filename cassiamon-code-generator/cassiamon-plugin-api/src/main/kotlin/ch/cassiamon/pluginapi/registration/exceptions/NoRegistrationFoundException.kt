package ch.cassiamon.pluginapi.registration.exceptions

import ch.cassiamon.pluginapi.registration.Registration

class NoRegistrationFoundException(): SchemaException(
    """Could not find a implementation of the interface '${Registration::javaClass}'. 
       Do you have the cassiamon engine on your classpath as dependency? 
    """.trimMargin()
) {

}
