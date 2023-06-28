package ch.cassiamon.engine.domain.registration

import ch.cassiamon.api.schema.SchemaAccess

interface SchemaProvider {

    fun provideSchema(): SchemaAccess
}
