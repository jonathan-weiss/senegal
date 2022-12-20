package ch.senegal.plugin.model

import ch.senegal.plugin.Concept
import ch.senegal.plugin.FacetName
import ch.senegal.plugin.PurposeName
import ch.senegal.plugin.StringEnumerationFacetOption
import java.nio.file.Path

interface ModelNode {
    fun concept(): Concept
    fun parentModelNode(): ModelNode?
    fun getStringFacetValue(purposeName: PurposeName, facetName: FacetName): String?
    fun getEnumFacetOption(purposeName: PurposeName, facetName: FacetName): StringEnumerationFacetOption?
    fun getBooleanFacetValue(purposeName: PurposeName, facetName: FacetName): Boolean?
    fun getIntFacetValue(purposeName: PurposeName, facetName: FacetName): Int?
    fun getFileFacetValue(purposeName: PurposeName, facetName: FacetName): Path?
    fun getDirectoryFacetValue(purposeName: PurposeName, facetName: FacetName): Path?
}
