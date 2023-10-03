package ch.cassiamon.exampleapp.customizing.templates.restapi

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper

object RestApiTransferObjectTemplate {

    fun fillTemplate(restModelClass: RestModelClass): String {
        return """
            package ${restModelClass.transferObjectPackageName}
            
            import ${restModelClass.kotlinModelClass.kotlinPackage}.${restModelClass.kotlinModelClass.kotlinClassName}
            import ${restModelClass.kotlinModelClass.kotlinPackage}.${restModelClass.kotlinModelClass.idFieldType}
            
            data class ${restModelClass.transferObjectBaseName}TO(
                val ${restModelClass.transferObjectIdFieldName}: ${restModelClass.kotlinModelClass.idFieldType},${
            StringTemplateHelper.forEach(restModelClass.fields()) { field ->
            """
                val ${field.kotlinModelField.kotlinFieldName}: ${field.kotlinModelField.kotlinFieldType},"""
                }}
                val transferObjectDescription: String,
            ) {
                companion object {
                    internal fun fromDomain(domainInstance: ${restModelClass.kotlinModelClass.kotlinClassName}) = ${restModelClass.transferObjectBaseName}TO(
                        ${restModelClass.transferObjectIdFieldName} = ${restModelClass.kotlinModelClass.idFieldType}(domainInstance.${restModelClass.kotlinModelClass.idFieldName}.value),${
            StringTemplateHelper.forEach(restModelClass.fields()) { field ->
                    """
                        ${field.kotlinModelField.kotlinFieldName} = domainInstance.${field.kotlinModelField.kotlinFieldName},""" }}
                        transferObjectDescription = "${StringTemplateHelper.join(restModelClass.fields(), ",") { field -> "\${domainInstance.${field.kotlinModelField.kotlinFieldName}}" }}"
                    )
                }
            }
            
        """.identForMarker()
    }
}
