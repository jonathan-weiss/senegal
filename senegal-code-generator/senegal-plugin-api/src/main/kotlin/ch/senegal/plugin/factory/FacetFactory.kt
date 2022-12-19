package ch.senegal.plugin.factory

import ch.senegal.plugin.*
import ch.senegal.plugin.model.ModelNode
import java.nio.file.Path

object FacetFactory {

    object StringFacetFactory {
        fun createCalculatedFacet(
            facetName: FacetName,
            enclosingConceptName: ConceptName,
            calculateFacetValue: (modelNode: ModelNode) -> String?
        ): StringFacet {
            val enhanceFacetValue: (modelNode: ModelNode, facetValue: String?) -> String? =
                { m, _ -> calculateFacetValue(m) }
            return StringFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = true,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: FacetName,
            enclosingConceptName: ConceptName,
            enhanceFacetValue: (modelNode: ModelNode, facetValue: String?) -> String?
        ): StringFacet {
            return StringFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: FacetName,
            enclosingConceptName: ConceptName
        ): StringFacet {
            val enhanceFacetValue: (modelNode: ModelNode, facetValue: String?) -> String? = { _, f -> f }
            return StringFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }
    }

    object BooleanFacetFactory {
        fun createCalculatedFacet(
            facetName: FacetName,
            enclosingConceptName: ConceptName,
            calculateFacetValue: (modelNode: ModelNode) -> Boolean?
        ): BooleanFacet {
            val enhanceFacetValue: (modelNode: ModelNode, facetValue: Boolean?) -> Boolean? =
                { m, _ -> calculateFacetValue(m) }
            return BooleanFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = true,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: FacetName,
            enclosingConceptName: ConceptName,
            enhanceFacetValue: (modelNode: ModelNode, facetValue: Boolean?) -> Boolean?
        ): BooleanFacet {
            return BooleanFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: FacetName,
            enclosingConceptName: ConceptName
        ): BooleanFacet {
            val enhanceFacetValue: (modelNode: ModelNode, facetValue: Boolean?) -> Boolean? = { _, f -> f }
            return BooleanFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }
    }

    object StringEnumerationFacetFactory {
        fun createCalculatedFacet(
            facetName: FacetName,
            enclosingConceptName: ConceptName,
            enumerationOptions: List<StringEnumerationFacetOption>,
            calculateFacetValue: (modelNode: ModelNode) -> String?
        ): StringEnumerationFacet {
            val enhanceFacetValue: (modelNode: ModelNode, facetValue: String?) -> String? =
                { m, _ -> calculateFacetValue(m) }
            return StringEnumerationFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = true,
                enumerationOptions = enumerationOptions,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: FacetName,
            enclosingConceptName: ConceptName,
            enumerationOptions: List<StringEnumerationFacetOption>,
            enhanceFacetValue: (modelNode: ModelNode, facetValue: String?) -> String?
        ): StringEnumerationFacet {
            return StringEnumerationFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enumerationOptions = enumerationOptions,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: FacetName,
            enclosingConceptName: ConceptName,
            enumerationOptions: List<StringEnumerationFacetOption>,
        ): StringEnumerationFacet {
            val enhanceFacetValue: (modelNode: ModelNode, facetValue: String?) -> String? = { _, f -> f }
            return StringEnumerationFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enumerationOptions = enumerationOptions,
                enhanceFacetValue = enhanceFacetValue
            )
        }
    }

    object IntegerFacetFactory {
        fun createCalculatedFacet(
            facetName: FacetName,
            enclosingConceptName: ConceptName,
            calculateFacetValue: (modelNode: ModelNode) -> Int?
        ): IntegerFacet {
            val enhanceFacetValue: (modelNode: ModelNode, facetValue: Int?) -> Int? =
                { m, _ -> calculateFacetValue(m) }
            return IntegerFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = true,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: FacetName,
            enclosingConceptName: ConceptName,
            enhanceFacetValue: (modelNode: ModelNode, facetValue: Int?) -> Int?
        ): IntegerFacet {
            return IntegerFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: FacetName,
            enclosingConceptName: ConceptName
        ): IntegerFacet {
            val enhanceFacetValue: (modelNode: ModelNode, facetValue: Int?) -> Int? = { _, f -> f }
            return IntegerFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }
    }

    object FileFacetFactory {
        fun createCalculatedFacet(
            facetName: FacetName,
            enclosingConceptName: ConceptName,
            calculateFacetValue: (modelNode: ModelNode) -> Path?
        ): FileFacet {
            val enhanceFacetValue: (modelNode: ModelNode, facetValue: Path?) -> Path? =
                { m, _ -> calculateFacetValue(m) }
            return FileFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = true,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: FacetName,
            enclosingConceptName: ConceptName,
            enhanceFacetValue: (modelNode: ModelNode, facetValue: Path?) -> Path?
        ): FileFacet {
            return FileFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: FacetName,
            enclosingConceptName: ConceptName
        ): FileFacet {
            val enhanceFacetValue: (modelNode: ModelNode, facetValue: Path?) -> Path? = { _, f -> f }
            return FileFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }
    }

    object DirectoryFacetFactory {
        fun createCalculatedFacet(
            facetName: FacetName,
            enclosingConceptName: ConceptName,
            calculateFacetValue: (modelNode: ModelNode) -> Path?
        ): DirectoryFacet {
            val enhanceFacetValue: (modelNode: ModelNode, facetValue: Path?) -> Path? =
                { m, _ -> calculateFacetValue(m) }
            return DirectoryFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = true,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: FacetName,
            enclosingConceptName: ConceptName,
            enhanceFacetValue: (modelNode: ModelNode, facetValue: Path?) -> Path?
        ): DirectoryFacet {
            return DirectoryFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: FacetName,
            enclosingConceptName: ConceptName
        ): DirectoryFacet {
            val enhanceFacetValue: (modelNode: ModelNode, facetValue: Path?) -> Path? = { _, f -> f }
            return DirectoryFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }
    }
}
