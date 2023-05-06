package ch.cassiamon.api.registration.exceptions

import ch.cassiamon.api.registration.SchemaRegistration

class NoRegistrationFoundException(): SchemaException(
    """Could not find a implementation of the interface '${SchemaRegistration::javaClass}'. 
       Do you have the cassiamon engine on your classpath as dependency? 
    """.trimMargin()
) {

}
