package ch.cassiamon.pluginapi.factory

import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.ModelNode
import java.nio.file.Path

object FacetFactory {

    object StringFacetFactory {
        fun createCalculatedFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
            calculateFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode) -> String?
        ): ch.cassiamon.pluginapi.StringFacet {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: String?) -> String? =
                { m, _ -> calculateFacetValue(m) }
            return ch.cassiamon.pluginapi.StringFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = true,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
            enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: String?) -> String?
        ): ch.cassiamon.pluginapi.StringFacet {
            return ch.cassiamon.pluginapi.StringFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName
        ): ch.cassiamon.pluginapi.StringFacet {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: String?) -> String? = { _, f -> f }
            return ch.cassiamon.pluginapi.StringFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }
    }

    object BooleanFacetFactory {
        fun createCalculatedFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
            calculateFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode) -> Boolean?
        ): ch.cassiamon.pluginapi.BooleanFacet {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Boolean?) -> Boolean? =
                { m, _ -> calculateFacetValue(m) }
            return ch.cassiamon.pluginapi.BooleanFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = true,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
            enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Boolean?) -> Boolean?
        ): ch.cassiamon.pluginapi.BooleanFacet {
            return ch.cassiamon.pluginapi.BooleanFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName
        ): ch.cassiamon.pluginapi.BooleanFacet {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Boolean?) -> Boolean? = { _, f -> f }
            return ch.cassiamon.pluginapi.BooleanFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }
    }

    object StringEnumerationFacetFactory {
        fun createCalculatedFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
            enumerationOptions: List<ch.cassiamon.pluginapi.StringEnumerationFacetOption>,
            calculateFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode) -> String?
        ): ch.cassiamon.pluginapi.StringEnumerationFacet {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: String?) -> String? =
                { m, _ -> calculateFacetValue(m) }
            return ch.cassiamon.pluginapi.StringEnumerationFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = true,
                enumerationOptions = enumerationOptions,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
            enumerationOptions: List<ch.cassiamon.pluginapi.StringEnumerationFacetOption>,
            enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: String?) -> String?
        ): ch.cassiamon.pluginapi.StringEnumerationFacet {
            return ch.cassiamon.pluginapi.StringEnumerationFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enumerationOptions = enumerationOptions,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
            enumerationOptions: List<ch.cassiamon.pluginapi.StringEnumerationFacetOption>,
        ): ch.cassiamon.pluginapi.StringEnumerationFacet {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: String?) -> String? = { _, f -> f }
            return ch.cassiamon.pluginapi.StringEnumerationFacet(
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
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
            calculateFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode) -> Int?
        ): ch.cassiamon.pluginapi.IntegerFacet {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Int?) -> Int? =
                { m, _ -> calculateFacetValue(m) }
            return ch.cassiamon.pluginapi.IntegerFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = true,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
            enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Int?) -> Int?
        ): ch.cassiamon.pluginapi.IntegerFacet {
            return ch.cassiamon.pluginapi.IntegerFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName
        ): ch.cassiamon.pluginapi.IntegerFacet {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Int?) -> Int? = { _, f -> f }
            return ch.cassiamon.pluginapi.IntegerFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }
    }

    object FileFacetFactory {
        fun createCalculatedFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
            calculateFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode) -> Path?
        ): ch.cassiamon.pluginapi.FileFacet {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Path?) -> Path? =
                { m, _ -> calculateFacetValue(m) }
            return ch.cassiamon.pluginapi.FileFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = true,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
            enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Path?) -> Path?
        ): ch.cassiamon.pluginapi.FileFacet {
            return ch.cassiamon.pluginapi.FileFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName
        ): ch.cassiamon.pluginapi.FileFacet {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Path?) -> Path? = { _, f -> f }
            return ch.cassiamon.pluginapi.FileFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }
    }

    object DirectoryFacetFactory {
        fun createCalculatedFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
            calculateFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode) -> Path?
        ): ch.cassiamon.pluginapi.DirectoryFacet {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Path?) -> Path? =
                { m, _ -> calculateFacetValue(m) }
            return ch.cassiamon.pluginapi.DirectoryFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = true,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
            enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Path?) -> Path?
        ): ch.cassiamon.pluginapi.DirectoryFacet {
            return ch.cassiamon.pluginapi.DirectoryFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName
        ): ch.cassiamon.pluginapi.DirectoryFacet {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Path?) -> Path? = { _, f -> f }
            return ch.cassiamon.pluginapi.DirectoryFacet(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }
    }
}
