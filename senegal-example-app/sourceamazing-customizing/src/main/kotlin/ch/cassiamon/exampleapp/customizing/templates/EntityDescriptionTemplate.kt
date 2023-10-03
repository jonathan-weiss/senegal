package ch.cassiamon.exampleapp.customizing.templates

import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper.type
import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import java.nio.file.Path
import kotlin.io.path.absolutePathString

object EntityDescriptionTemplate {

    private const val ident = "  "

    fun createEntityDescriptionTemplate(targetFile: Path, entity: EntityConcept): String {

        val entityDescription = createEntityDescriptionContent(entity)

        return """
            Filename: ${targetFile.absolutePathString()}
            ---------
            
            {nestedIdent}$entityDescription{nestedIdent}
            
        """.identForMarker()
    }

    private fun createEntityDescriptionContent(entity: EntityConcept): String {

        val entityAttributes = entity.entityFields()
            .joinToString("\n") { createEntityAttributeSubTemplate(it) }

        return """
            Entity name: ${entity.getName()}
            
            Entity attributes:
            {nestedIdent}$entityAttributes{nestedIdent} 
            
        """.identForMarker()
    }

    private fun createEntityAttributeSubTemplate(entityAttribute: EntityField): String {
        return """
            Entity Attribute: ${entityAttribute.getName()} (Type: ${entityAttribute.type()})
        """.replaceIndent(ident)
    }

    fun createEntitiesDescriptionTemplate(targetFile: Path, entities: List<EntityConcept>): String {
        val content = entities
            .map { createEntityDescriptionContent(it) }
            .joinToString("\n-------------------------\n")
        return """
            Filename: ${targetFile.absolutePathString()}
            ---------
            
            {nestedIdent}$content{nestedIdent}
            
        """.identForMarker()
    }
}
