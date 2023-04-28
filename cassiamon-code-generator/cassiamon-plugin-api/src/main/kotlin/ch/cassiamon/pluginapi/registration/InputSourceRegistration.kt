package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.schema.SchemaAccess

interface InputSourceRegistration {

    fun receiveDataCollector(): InputSourceDataCollector

    fun receiveSchema(): SchemaAccess

}
