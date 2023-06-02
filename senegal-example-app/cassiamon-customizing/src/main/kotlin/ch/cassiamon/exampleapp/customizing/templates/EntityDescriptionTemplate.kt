package ch.cassiamon.exampleapp.customizing.templates

import ch.cassiamon.exampleapp.customizing.wrapper.EntityAttributeConcept
import ch.cassiamon.exampleapp.customizing.wrapper.EntityConcept
import ch.cassiamon.tools.StringIdentHelper
import java.nio.file.Path
import kotlin.io.path.absolutePathString

object EntityDescriptionTemplate {

    private const val ident = "  "

    fun createExampleTemplate(targetFile: Path, entity: EntityConcept): String {

        val entityAttributes = entity.entityAttributes()
            .joinToString("\n") { createEntityAttributeSubTemplate(it) }

        return StringIdentHelper.identForMarker("""
            Filename: ${targetFile.absolutePathString()}
            ---------
            
            Entity name: ${entity.name}
            
            Entity attributes:
            {nestedIdent}$entityAttributes{nestedIdent} 
            
        """)
    }

    private fun createEntityAttributeSubTemplate(entityAttribute: EntityAttributeConcept): String {
        return """
            Entity Attribute: ${entityAttribute.name} (Type: ${entityAttribute.type})
        """.replaceIndent(ident)
    }

}
