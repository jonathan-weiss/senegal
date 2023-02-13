package ch.cassiamon.pluginapi.registration.exceptions

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName


class FacetDependencyNotFoundSchemaException(val concept: ConceptName, val facets: Set<FacetName>): SchemaException(
    """The facets ['${facets.joinToString { it.name }}'] are not known on the concept '${concept.name}', 
        but were declared as facet dependencies. 
    """.trimMargin()
) {

}
