package ch.cassiamon.engine

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName

object TestFixtures {
    val databaseTableConceptName = ConceptName.of("DatabaseTable")
    val databaseTableFieldConceptName = ConceptName.of("DatabaseField")
    val tableNameFacetName = FacetName.of("TableName")
    val tableFieldNameFacetName = FacetName.of("FieldName")
    val tableFieldTypeFacetName = FacetName.of("FieldType")
    val tableFieldLengthFacetName = FacetName.of("FieldLength")

}
