package ch.cassiamon.engine.schema.registration

import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNode
import ch.cassiamon.pluginapi.registration.types.ConceptReferenceFacetTransformationFunction
import ch.cassiamon.pluginapi.registration.types.IntegerNumberFacetTransformationFunction
import ch.cassiamon.pluginapi.registration.types.TextFacetTransformationFunction

object NoOpTransformationFunctions {

    val noOpTextTransformationFunction: TextFacetTransformationFunction = TextFacetTransformationFunction { _: ConceptModelNode, value: String -> value }
    val noOpIntegerNumberTransformationFunction: IntegerNumberFacetTransformationFunction = IntegerNumberFacetTransformationFunction { _: ConceptModelNode, value: Int -> value }
    val noOpConceptReferenceTransformationFunction: ConceptReferenceFacetTransformationFunction = ConceptReferenceFacetTransformationFunction { _: ConceptModelNode, value: ConceptIdentifier -> value }
}
