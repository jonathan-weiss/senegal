package ch.cassiamon.engine.process.conceptgraph

import ch.cassiamon.api.process.conceptgraph.exceptions.ConceptGraphException
import ch.cassiamon.api.process.conceptgraph.exceptions.ParentConceptNotFoundConceptGraphException
import ch.cassiamon.api.process.conceptgraph.exceptions.DuplicateConceptIdentifierFoundConceptGraphException
import ch.cassiamon.api.process.conceptgraph.exceptions.ReferencedConceptConceptGraphNodeNotFoundException
import ch.cassiamon.api.process.datacollection.ConceptData
import ch.cassiamon.api.process.datacollection.exceptions.SchemaValidationException
import ch.cassiamon.api.process.schema.ConceptIdentifier
import ch.cassiamon.api.process.schema.SchemaAccess
import ch.cassiamon.engine.process.datacollection.ConceptDataValidator

object ConceptResolver {

    @Throws(SchemaValidationException::class, ConceptGraphException::class)
    fun validateAndResolveConcepts(schema: SchemaAccess, conceptDataEntries: List<ConceptData>): ConceptGraph {
        conceptDataEntries.forEach { ConceptDataValidator.validateSingleEntry(schema, it) }
        val conceptDataMap = conceptDataEntries.associateBy { it.conceptIdentifier }
        val conceptNodeMap: Map<ConceptIdentifier, MutableConceptNode> = createConceptNodeMap(conceptDataEntries)
        val childrenConceptsMap = createChildrenConceptNodeMap(conceptDataEntries, conceptNodeMap)

        conceptNodeMap.values.forEach { conceptNode ->
            val conceptSchema = schema.conceptByConceptName(conceptNode.conceptName)
            val conceptData = requireNotNull(conceptDataMap[conceptNode.conceptIdentifier])

            // resolve parent concept node (if not root node)
            val parentConceptIdentifier = conceptData.parentConceptIdentifier
            if(parentConceptIdentifier != null) {
                conceptNode.parentConceptNode = conceptNodeMap[parentConceptIdentifier]
                    ?: throw ParentConceptNotFoundConceptGraphException(
                        conceptName = conceptNode.conceptName,
                        conceptIdentifier = conceptNode.conceptIdentifier,
                        parentConceptIdentifier = parentConceptIdentifier)
            }
            // resolve children concept nodes
            conceptNode.childrenConceptNodes = childrenConceptsMap[conceptNode.conceptIdentifier]
                ?.groupBy { it.conceptName }
                ?: emptyMap()

            // resolve all facet values
            conceptSchema.facetNames.forEach { facetName ->
                val facetValue = conceptData.getFacet(facetName)
                if(facetValue is ConceptIdentifier) {
                    val referencedConceptNode = conceptNodeMap[facetValue]
                        ?: throw ReferencedConceptConceptGraphNodeNotFoundException(
                            conceptName = conceptNode.conceptName,
                            conceptIdentifier = conceptNode.conceptIdentifier,
                            facetName = facetName,
                            referencedConceptIdentifier = facetValue,
                        )
                    conceptNode.facetValues[facetName] = referencedConceptNode
                } else {
                    conceptNode.facetValues[facetName] = facetValue
                }
            }
        }

        return ConceptGraph(conceptNodeMap)
    }

    private fun createConceptNodeMap(conceptDataEntries: List<ConceptData>): Map<ConceptIdentifier, MutableConceptNode> {
        val conceptNodeMap: MutableMap<ConceptIdentifier, MutableConceptNode> = mutableMapOf()
        conceptDataEntries.forEach { conceptData ->
            val conceptIdentifier = conceptData.conceptIdentifier
            if(conceptNodeMap.containsKey(conceptIdentifier)) {
                throw DuplicateConceptIdentifierFoundConceptGraphException(conceptData.conceptName, conceptIdentifier)
            }
            conceptNodeMap[conceptIdentifier] = MutableConceptNode(conceptData.conceptName, conceptData.conceptIdentifier)
        }
        return conceptNodeMap
    }

    private fun createChildrenConceptNodeMap(
        conceptDataEntries: List<ConceptData>,
        conceptNodeMap: Map<ConceptIdentifier, MutableConceptNode>
    ): Map<ConceptIdentifier, List<MutableConceptNode>> {
        return conceptDataEntries
            .filter { it.parentConceptIdentifier != null  }
            .groupBy(
                keySelector = { requireNotNull(it.parentConceptIdentifier) },
                valueTransform = { requireNotNull(conceptNodeMap[it.conceptIdentifier])
                }
            )
    }
}
