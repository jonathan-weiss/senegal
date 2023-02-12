package ch.cassiamon.pluginapi.model

import java.nio.file.Path

interface ModelNode {
    fun concept(): ch.cassiamon.pluginapi.ConceptSchema
    fun parentModelNode(): ch.cassiamon.pluginapi.model.ModelNode?
    fun getStringFacetValue(purposeName: ch.cassiamon.pluginapi.PurposeName, facetName: ch.cassiamon.pluginapi.FacetName): String?
    fun getEnumFacetOption(purposeName: ch.cassiamon.pluginapi.PurposeName, facetName: ch.cassiamon.pluginapi.FacetName): ch.cassiamon.pluginapi.StringEnumerationFacetOption?
    fun getBooleanFacetValue(purposeName: ch.cassiamon.pluginapi.PurposeName, facetName: ch.cassiamon.pluginapi.FacetName): Boolean?
    fun getIntFacetValue(purposeName: ch.cassiamon.pluginapi.PurposeName, facetName: ch.cassiamon.pluginapi.FacetName): Int?
    fun getFileFacetValue(purposeName: ch.cassiamon.pluginapi.PurposeName, facetName: ch.cassiamon.pluginapi.FacetName): Path?
    fun getDirectoryFacetValue(purposeName: ch.cassiamon.pluginapi.PurposeName, facetName: ch.cassiamon.pluginapi.FacetName): Path?
}
