package ch.cassiamon.api.process.schema

import ch.cassiamon.api.FacetName


interface FacetSchema {
    val facetName: FacetName
    val facetType: FacetTypeEnum
    val mandatory: Boolean
}
