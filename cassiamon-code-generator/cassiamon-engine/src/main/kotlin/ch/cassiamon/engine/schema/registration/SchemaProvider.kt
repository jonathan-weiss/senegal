package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.schema.Schema

interface SchemaProvider {

    fun provideSchema(): Schema
}
