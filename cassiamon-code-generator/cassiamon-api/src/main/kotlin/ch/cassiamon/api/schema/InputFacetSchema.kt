package ch.cassiamon.api.schema

import ch.cassiamon.api.*
import ch.cassiamon.api.model.facets.InputFacet

class InputFacetSchema<T>(
    val conceptName: ConceptName,
    val inputFacet: InputFacet<T>,
    )
