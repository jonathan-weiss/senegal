package ch.cassiamon.exampleapp.customizing.templates

import ch.cassiamon.api.model.ConceptModelNode
import ch.cassiamon.exampleapp.customizing.concepts.EntityAttributeConcept
import ch.cassiamon.exampleapp.customizing.concepts.EntityConcept
import ch.cassiamon.tools.StringIdentHelper
import java.nio.file.Path
import kotlin.io.path.absolutePathString

object ExampleAppTemplate {

    private const val ident = "  "

    fun createExampleTemplate(targetFile: Path, model: ConceptModelNode): String {

        val entityAttributes = model.children(EntityAttributeConcept.conceptName)
            .joinToString("\n") { createEntityAttributeSubTemplate(it) }

        return StringIdentHelper.identForMarker("""
            Filename: ${targetFile.absolutePathString()}
            ---------
            
            Entity name: ${model[EntityConcept.nameFacet]}
            Entity alternative name: ${model[EntityConcept.alternativeNameFacet]}
            
            Entity attributes:
            {nestedIdent}$entityAttributes{nestedIdent} 
            
        """)
    }

    private fun createEntityAttributeSubTemplate(entityAttribute: ConceptModelNode): String {
        return """
            Entity Attribute name: ${entityAttribute[EntityAttributeConcept.nameFacet]}
        """.replaceIndent(ident)
    }

}
