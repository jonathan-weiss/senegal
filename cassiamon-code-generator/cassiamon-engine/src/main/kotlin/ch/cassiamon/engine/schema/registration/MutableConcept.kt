package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.schema.types.*
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.registration.*
import ch.cassiamon.pluginapi.registration.exceptions.DuplicateFacetNameFoundSchemaException
import ch.cassiamon.pluginapi.registration.exceptions.FacetDependencyNotFoundSchemaException
import ch.cassiamon.pluginapi.registration.types.*


class MutableConcept(override val conceptName: ConceptName,
                     override val parentConceptName: ConceptName?,
                     val mutableFacets: MutableList<Facet> = mutableListOf()
    ): Concept
{

    override val facets: List<Facet>
        get() = mutableFacets.toList()
}
