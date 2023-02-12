package ch.cassiamon.pluginapi

sealed class FacetSchema(
    val facetName: FacetName,
    val enclosingConceptName: ConceptName,
    val isOnlyCalculated: Boolean = false, // TODO rename to isVirtual
): Plugin
