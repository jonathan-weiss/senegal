package ch.cassiamon.domain.example

import ch.cassiamon.api.model.ConceptModelNode
import ch.cassiamon.tools.StringIdentHelper
import java.nio.file.Path
import kotlin.io.path.absolutePathString

object ExampleTemplate {

    private const val ident = "  "

    fun createExampleTemplate(targetFile: Path, model: ConceptModelNode): String {

        val entityAttributes = model.children(ExampleEntityAttributeConcept.conceptName)
            .joinToString("\n") { createEntityAttributeSubTemplate(it) }

        return StringIdentHelper.identForMarker("""
            Filename: ${targetFile.absolutePathString()}
            ---------
            
            Entity name: ${model[ExampleEntityConcept.nameFacet]}
            Entity alternative name: ${model[ExampleEntityConcept.alternativeNameFacet]}
            
            Entity attributes:
            {nestedIdent}$entityAttributes{nestedIdent} 
            
        """)
    }

    private fun createEntityAttributeSubTemplate(entityAttribute: ConceptModelNode): String {
        return """
            Entity Attribute name: ${entityAttribute[ExampleEntityAttributeConcept.nameFacet]}
        """.replaceIndent(ident)
    }

}
