package ch.senegal.engine.model

import ch.senegal.engine.plugin.resolver.ResolvedConcept
import ch.senegal.engine.plugin.resolver.ResolvedFacet
import ch.senegal.plugin.*
import ch.senegal.plugin.model.ModelNode
import java.nio.file.Path

class MutableModelNode(val resolvedConcept: ResolvedConcept,
                       private val parentMutableModelInstance: MutableModelInstance,
): MutableModelInstance(), ModelNode {

    val nodeFacetValues: MutableMap<PurposeFacetCombinedName, FacetValue> = mutableMapOf()

    override fun parentMutableModelInstance(): MutableModelInstance {
        return parentMutableModelInstance
    }

    fun addFacetValue(resolvedFacet: ResolvedFacet, facetValue: FacetValue) {
        nodeFacetValues[resolvedFacet.purposeFacetName] = facetValue
    }

    override fun concept(): Concept {
        return resolvedConcept.concept
    }

    override fun parentModelNode(): ModelNode? {
        return when(parentMutableModelInstance) {
            is MutableModelNode -> this
            is MutableModelTree -> null
        }
    }

    override fun getEnumFacetOption(purposeName: PurposeName, facetName: FacetName): StringEnumerationFacetOption? {
        val resolvedFacet = getResolvedFacetOrThrow(purposeName, facetName)
        if(resolvedFacet.facet !is StringEnumerationFacet) {
            throw IllegalArgumentException("Wrong facet type for ${resolvedFacet.purposeFacetName.name}. Facet is not an enum facet.")
        }
        return getStringFacetValue(purposeName, facetName)?.let { StringEnumerationFacetOption(it) }
    }

    override fun getStringFacetValue(purposeName: PurposeName, facetName: FacetName): String? {
        return getFacetValueOrThrow(getResolvedFacetOrThrow(purposeName, facetName), String::class.java)?.value as String?
    }

    override fun getBooleanFacetValue(purposeName: PurposeName, facetName: FacetName): Boolean? {
        return getFacetValueOrThrow(getResolvedFacetOrThrow(purposeName, facetName), Boolean::class.java)?.value as Boolean?
    }

    override fun getIntFacetValue(purposeName: PurposeName, facetName: FacetName): Int? {
        return getFacetValueOrThrow(getResolvedFacetOrThrow(purposeName, facetName), Int::class.java)?.value as Int?
    }

    override fun getFileFacetValue(purposeName: PurposeName, facetName: FacetName): Path? {
        return getFacetValueOrThrow(getResolvedFacetOrThrow(purposeName, facetName), Path::class.java)?.value as Path?
    }

    override fun getDirectoryFacetValue(purposeName: PurposeName, facetName: FacetName): Path? {
        return getFacetValueOrThrow(getResolvedFacetOrThrow(purposeName, facetName), Path::class.java)?.value as Path?
    }

    private fun getResolvedFacetOrThrow(purposeName: PurposeName, facetName: FacetName): ResolvedFacet {
        val purposeFacetCombinedName = PurposeFacetCombinedName.of(purposeName, facetName)
        return resolvedConcept.getFacetByCombinedName(purposeFacetCombinedName)
            ?: throw IllegalArgumentException("No facet found for ${purposeFacetCombinedName.name}.")
    }


    private fun getFacetValueOrThrow(resolvedFacet: ResolvedFacet, asClass: Class<out Any>): FacetValue? {
        val facetValue = nodeFacetValues[resolvedFacet.purposeFacetName] ?: return null
        validTypeOrThrow(resolvedFacet.facet, asClass, resolvedFacet.purposeFacetName)
        return facetValue
    }

    private fun validTypeOrThrow(
        facet: Facet,
        asClass: Class<out Any>,
        purposeFacetCombinedName: PurposeFacetCombinedName
    ) {
        when (facet) {
            is StringEnumerationFacet -> checkCastConditionOrThrow(asClass, String::class.java, purposeFacetCombinedName)
            is StringFacet -> checkCastConditionOrThrow(asClass, String::class.java, purposeFacetCombinedName)
            is BooleanFacet -> checkCastConditionOrThrow(asClass, Boolean::class.java, purposeFacetCombinedName)
            is IntegerFacet -> checkCastConditionOrThrow(asClass, Int::class.java, purposeFacetCombinedName)
            is DirectoryFacet -> checkCastConditionOrThrow(asClass, Path::class.java, purposeFacetCombinedName)
            is FileFacet -> checkCastConditionOrThrow(asClass, Path::class.java, purposeFacetCombinedName)
            else -> throw IllegalArgumentException("FacetType is not supported: $facet")
        }

    }

    private fun checkCastConditionOrThrow(clazz:Class<out Any>, expectedClazz: Class<out Any>, purposeFacetCombinedName: PurposeFacetCombinedName) {
        if(clazz != expectedClazz) {
            throw IllegalArgumentException("Wrong facet type for ${purposeFacetCombinedName.name}. Expected $expectedClazz, but was $clazz.")
        }
    }
}
