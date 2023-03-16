package ch.cassiamon.engine.schema.facets

import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.facets.InputFacet

class InputFacetSchema<T>(
    val conceptName: ConceptName,
    val inputFacet: InputFacet<T>,
    )
