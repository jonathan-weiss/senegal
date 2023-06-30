package ch.cassiamon.api.schema

import ch.cassiamon.api.FacetName


interface FacetSchema {
    val facetName: FacetName
    val facetType: FacetTypeEnum
    val mandatory: Boolean
}
