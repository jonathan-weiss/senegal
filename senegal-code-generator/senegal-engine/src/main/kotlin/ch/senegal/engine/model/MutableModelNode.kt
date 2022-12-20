package ch.senegal.engine.model

import ch.senegal.engine.plugin.resolver.ResolvedConcept
import ch.senegal.engine.plugin.resolver.ResolvedFacet
import ch.senegal.plugin.*
import ch.senegal.plugin.model.FacetValue
import ch.senegal.plugin.model.ModelNode
import java.nio.file.Path
import kotlin.reflect.KClass

class MutableModelNode(val resolvedConcept: ResolvedConcept,
                       private val parentMutableModelInstance: MutableModelInstance,
): MutableModelInstance(), ModelNode {

    val nodeFacetValues: MutableMap<PurposeFacetCombinedName, FacetValue> = mutableMapOf()
    val nodeFacet: MutableMap<PurposeFacetCombinedName, Facet> = mutableMapOf()

    override fun parentMutableModelInstance(): MutableModelInstance {
        return parentMutableModelInstance
    }

    fun addFacetValue(resolvedFacet: ResolvedFacet, facetValue: FacetValue) {
        nodeFacetValues[resolvedFacet.purposeFacetName] = facetValue
        nodeFacet[resolvedFacet.purposeFacetName] = resolvedFacet.facet
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
        return getStringFacetValue(purposeName, facetName)?.let { StringEnumerationFacetOption(it) }
    }

    override fun getStringFacetValue(purposeName: PurposeName, facetName: FacetName): String? {
        return getFacetValueOrThrow(purposeName, facetName, String::class.java)?.value as String?
    }

    override fun getBooleanFacetValue(purposeName: PurposeName, facetName: FacetName): Boolean? {
        return getFacetValueOrThrow(purposeName, facetName, Boolean::class.java)?.value as Boolean?
    }

    override fun getIntFacetValue(purposeName: PurposeName, facetName: FacetName): Int? {
        return getFacetValueOrThrow(purposeName, facetName, Int::class.java)?.value as Int?
    }

    override fun getFileFacetValue(purposeName: PurposeName, facetName: FacetName): Path? {
        return getFacetValueOrThrow(purposeName, facetName, Path::class.java)?.value as Path?
    }

    override fun getDirectoryFacetValue(purposeName: PurposeName, facetName: FacetName): Path? {
        return getFacetValueOrThrow(purposeName, facetName, Path::class.java)?.value as Path?
    }

    private fun getFacetValueOrThrow(purposeName: PurposeName, facetName: FacetName, asClass: Class<out Any>): FacetValue? {
        val purposeFacetCombinedName = PurposeFacetCombinedName.of(purposeName, facetName)
        val facet = nodeFacet[purposeFacetCombinedName] ?: throw IllegalArgumentException("No facet found for ${purposeFacetCombinedName.name}.")
        val facetValue = nodeFacetValues[purposeFacetCombinedName] ?: return null
        validTypeOrThrow(facet, asClass, purposeFacetCombinedName)
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
