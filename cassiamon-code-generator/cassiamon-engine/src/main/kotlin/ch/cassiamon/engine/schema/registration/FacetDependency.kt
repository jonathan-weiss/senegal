package ch.cassiamon.engine.schema.registration

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName

data class FacetDependency(
    val targetConceptName: ConceptName,
    val targetFacetName: FacetName
)
