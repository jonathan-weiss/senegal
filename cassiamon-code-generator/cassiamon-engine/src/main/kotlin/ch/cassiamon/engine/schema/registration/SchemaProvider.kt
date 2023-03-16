package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.pluginapi.model.facets.InputAndTemplateFacet
import ch.cassiamon.pluginapi.model.facets.InputFacet
import ch.cassiamon.pluginapi.model.facets.TemplateFacet

interface SchemaProvider {

    fun provideSchema(): Schema
}
