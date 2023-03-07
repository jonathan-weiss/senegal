package ch.cassiamon.pluginapi



sealed class FacetDescriptor<O> constructor(
    val facetName: FacetName,
    val isMandatoryFacetValue: Boolean,
    val isManualFacetValue: Boolean,
) {

    val isOptionalFacetValue: Boolean
        get() = !isMandatoryFacetValue

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FacetDescriptor<*>

        if (facetName != other.facetName) return false

        return true
    }

    override fun hashCode(): Int {
        return facetName.hashCode()
    }


}
