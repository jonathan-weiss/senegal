package ch.cassiamon.engine.process.datacollection

import ch.cassiamon.api.process.schema.ConceptIdentifier
import ch.cassiamon.api.process.datacollection.extensions.ExtensionDataCollector
import ch.cassiamon.api.process.datacollection.ConceptData
import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName
import ch.cassiamon.api.process.schema.SchemaAccess

class ConceptDataCollector(private val schema: SchemaAccess, private val validateConcept: Boolean = true):
    ExtensionDataCollector {

    private val conceptData: MutableMap<ConceptIdentifier, ConceptData> = mutableMapOf()

    override fun existingConceptData(conceptIdentifier: ConceptIdentifier): ConceptData {
        return conceptData[conceptIdentifier] ?: throw IllegalArgumentException("No concept with concept id '$conceptIdentifier' found.")
    }

    override fun existingOrNewConceptData(conceptName: ConceptName, conceptIdentifier: ConceptIdentifier, parentConceptIdentifier: ConceptIdentifier?): ConceptData {
        return conceptData.getOrPut(conceptIdentifier) {
            ConceptDataImpl(conceptName, conceptIdentifier)
        }.setParentConceptIdentifier(parentConceptIdentifier)
    }

    fun provideConceptData(): List<ConceptData> {
        return conceptData.values.toList()
    }

    class ConceptDataImpl(
        override val conceptName: ConceptName,
        override val conceptIdentifier: ConceptIdentifier,
    ): ConceptData {
        private var mutableParentConceptIdentifier: ConceptIdentifier? = null
        private val mutableFacets: MutableMap<FacetName, Any?> = mutableMapOf()
        override val parentConceptIdentifier: ConceptIdentifier?
            get() = mutableParentConceptIdentifier

        override fun setParentConceptIdentifier(parentConceptIdentifier: ConceptIdentifier?): ConceptData {
            this.mutableParentConceptIdentifier = parentConceptIdentifier
            return this
        }


        override fun hasFacet(facetName: FacetName): Boolean {
            return mutableFacets.containsKey(facetName)
        }

        override fun getFacet(facetName: FacetName): Any? {
            if(hasFacet(facetName)) {
                return mutableFacets[facetName]
            } else {
                throw NoSuchElementException("No facet defined for name ${facetName.name}.")
            }
        }

        override fun getFacetNames(): Set<FacetName> {
            return mutableFacets.keys
        }

        override fun allFacets(): Map<FacetName, Any?> {
            return mutableFacets.toMap()
        }


        override fun addOrReplaceFacetValues(facetValues: Map<FacetName, Any?>): ConceptDataImpl {
            facetValues.forEach { (facetName, facetValue) ->
                mutableFacets[facetName] = facetValue
            }
            return this
        }


        override fun addOrReplaceFacetValue(facetName: FacetName, facetValue: Any?): ConceptDataImpl {
            mutableFacets[facetName] = facetValue
            return this
        }
    }
}
