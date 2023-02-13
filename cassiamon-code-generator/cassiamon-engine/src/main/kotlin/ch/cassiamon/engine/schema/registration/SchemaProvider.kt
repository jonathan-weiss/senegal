package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.schema.types.Schema

interface SchemaProvider {

    fun provideSchema(): Schema
}
