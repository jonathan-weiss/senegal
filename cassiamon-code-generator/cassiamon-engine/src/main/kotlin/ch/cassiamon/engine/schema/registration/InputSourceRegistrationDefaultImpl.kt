package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.pluginapi.registration.InputSourceDataCollector
import ch.cassiamon.pluginapi.registration.InputSourceRegistration
import ch.cassiamon.pluginapi.schema.SchemaAccess

class InputSourceRegistrationDefaultImpl(
    private val modelInputDataCollector: ModelInputDataCollector,
    private val schemaProvider: SchemaProvider,
    ) : InputSourceRegistration {
    override fun receiveDataCollector(): InputSourceDataCollector {
        return modelInputDataCollector
    }

    override fun receiveSchema(): SchemaAccess {
        return schemaProvider.provideSchema()
    }
}
