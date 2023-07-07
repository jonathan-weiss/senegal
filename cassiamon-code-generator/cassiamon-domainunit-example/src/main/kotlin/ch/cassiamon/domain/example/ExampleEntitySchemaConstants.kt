package ch.cassiamon.domain.example

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName

object ExampleEntitySchemaConstants {
    val conceptName = ConceptName.of("TestEntity")

    val exampleEntityNameFacetName = FacetName.of("TestEntityName")
}
