package ch.cassiamon.pluginapi.factory

import java.nio.file.Path

object FacetSchemaFactory {

    object StringFacetSchemaFactory {
        fun createCalculatedFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
            calculateFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode) -> String?
        ): ch.cassiamon.pluginapi.StringFacetSchema {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: String?) -> String? =
                { m, _ -> calculateFacetValue(m) }
            return ch.cassiamon.pluginapi.StringFacetSchema(
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
        ): ch.cassiamon.pluginapi.StringFacetSchema {
            return ch.cassiamon.pluginapi.StringFacetSchema(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName
        ): ch.cassiamon.pluginapi.StringFacetSchema {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: String?) -> String? = { _, f -> f }
            return ch.cassiamon.pluginapi.StringFacetSchema(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }
    }

    object BooleanFacetSchemaFactory {
        fun createCalculatedFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
            calculateFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode) -> Boolean?
        ): ch.cassiamon.pluginapi.BooleanFacetSchema {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Boolean?) -> Boolean? =
                { m, _ -> calculateFacetValue(m) }
            return ch.cassiamon.pluginapi.BooleanFacetSchema(
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
        ): ch.cassiamon.pluginapi.BooleanFacetSchema {
            return ch.cassiamon.pluginapi.BooleanFacetSchema(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName
        ): ch.cassiamon.pluginapi.BooleanFacetSchema {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Boolean?) -> Boolean? = { _, f -> f }
            return ch.cassiamon.pluginapi.BooleanFacetSchema(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }
    }

    object StringEnumerationFacetSchemaFactory {
        fun createCalculatedFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
            enumerationOptions: List<ch.cassiamon.pluginapi.StringEnumerationFacetOption>,
            calculateFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode) -> String?
        ): ch.cassiamon.pluginapi.StringEnumerationFacetSchema {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: String?) -> String? =
                { m, _ -> calculateFacetValue(m) }
            return ch.cassiamon.pluginapi.StringEnumerationFacetSchema(
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
        ): ch.cassiamon.pluginapi.StringEnumerationFacetSchema {
            return ch.cassiamon.pluginapi.StringEnumerationFacetSchema(
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
        ): ch.cassiamon.pluginapi.StringEnumerationFacetSchema {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: String?) -> String? = { _, f -> f }
            return ch.cassiamon.pluginapi.StringEnumerationFacetSchema(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enumerationOptions = enumerationOptions,
                enhanceFacetValue = enhanceFacetValue
            )
        }
    }

    object IntegerFacetSchemaFactory {
        fun createCalculatedFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
            calculateFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode) -> Int?
        ): ch.cassiamon.pluginapi.IntegerFacetSchema {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Int?) -> Int? =
                { m, _ -> calculateFacetValue(m) }
            return ch.cassiamon.pluginapi.IntegerFacetSchema(
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
        ): ch.cassiamon.pluginapi.IntegerFacetSchema {
            return ch.cassiamon.pluginapi.IntegerFacetSchema(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName
        ): ch.cassiamon.pluginapi.IntegerFacetSchema {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Int?) -> Int? = { _, f -> f }
            return ch.cassiamon.pluginapi.IntegerFacetSchema(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }
    }

    object FileFacetSchemaFactory {
        fun createCalculatedFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
            calculateFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode) -> Path?
        ): ch.cassiamon.pluginapi.FileFacetSchema {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Path?) -> Path? =
                { m, _ -> calculateFacetValue(m) }
            return ch.cassiamon.pluginapi.FileFacetSchema(
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
        ): ch.cassiamon.pluginapi.FileFacetSchema {
            return ch.cassiamon.pluginapi.FileFacetSchema(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName
        ): ch.cassiamon.pluginapi.FileFacetSchema {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Path?) -> Path? = { _, f -> f }
            return ch.cassiamon.pluginapi.FileFacetSchema(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }
    }

    object DirectoryFacetSchemaFactory {
        fun createCalculatedFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
            calculateFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode) -> Path?
        ): ch.cassiamon.pluginapi.DirectoryFacetSchema {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Path?) -> Path? =
                { m, _ -> calculateFacetValue(m) }
            return ch.cassiamon.pluginapi.DirectoryFacetSchema(
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
        ): ch.cassiamon.pluginapi.DirectoryFacetSchema {
            return ch.cassiamon.pluginapi.DirectoryFacetSchema(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }

        fun createFacet(
            facetName: ch.cassiamon.pluginapi.FacetName,
            enclosingConceptName: ch.cassiamon.pluginapi.ConceptName
        ): ch.cassiamon.pluginapi.DirectoryFacetSchema {
            val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Path?) -> Path? = { _, f -> f }
            return ch.cassiamon.pluginapi.DirectoryFacetSchema(
                facetName = facetName,
                enclosingConceptName = enclosingConceptName,
                isOnlyCalculated = false,
                enhanceFacetValue = enhanceFacetValue
            )
        }
    }
}
