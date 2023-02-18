package ch.cassiamon.engine.schema.registration

import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptNode
import ch.cassiamon.pluginapi.registration.types.ConceptReferenceFacetTransformationFunction
import ch.cassiamon.pluginapi.registration.types.IntegerNumberFacetTransformationFunction
import ch.cassiamon.pluginapi.registration.types.TextFacetTransformationFunction

object NoOpTransformationFunctions {

    val noOpTextTransformationFunction: TextFacetTransformationFunction = TextFacetTransformationFunction { _: ConceptNode, value: String -> value }
    val noOpIntegerNumberTransformationFunction: IntegerNumberFacetTransformationFunction = IntegerNumberFacetTransformationFunction { _: ConceptNode, value: Int -> value }
    val noOpConceptReferenceTransformationFunction: ConceptReferenceFacetTransformationFunction = ConceptReferenceFacetTransformationFunction { _: ConceptNode, value: ConceptIdentifier -> value }
}
