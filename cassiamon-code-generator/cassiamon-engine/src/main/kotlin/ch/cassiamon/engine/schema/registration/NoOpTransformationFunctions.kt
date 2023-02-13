package ch.cassiamon.engine.schema.registration

import ch.cassiamon.pluginapi.registration.GraphNode
import ch.cassiamon.pluginapi.registration.types.ConceptReferenceFacetTransformationFunction
import ch.cassiamon.pluginapi.registration.types.IntegerNumberFacetTransformationFunction
import ch.cassiamon.pluginapi.registration.types.TextFacetTransformationFunction

object NoOpTransformationFunctions {

    val noOpTextTransformationFunction: TextFacetTransformationFunction = TextFacetTransformationFunction { _: GraphNode, value: String -> value }
    val noOpIntegerNumberTransformationFunction: IntegerNumberFacetTransformationFunction = IntegerNumberFacetTransformationFunction { _: GraphNode, value: Int -> value }
    val noOpConceptReferenceTransformationFunction: ConceptReferenceFacetTransformationFunction = ConceptReferenceFacetTransformationFunction { _: GraphNode, value: GraphNode -> value }
}
