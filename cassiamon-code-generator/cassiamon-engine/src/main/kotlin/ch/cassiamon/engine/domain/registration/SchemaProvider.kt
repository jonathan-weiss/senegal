package ch.cassiamon.engine.domain.registration

import ch.cassiamon.engine.domain.Schema

interface SchemaProvider {

    fun provideSchema(): Schema
}
