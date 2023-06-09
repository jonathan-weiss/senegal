package ch.cassiamon.exampleapp.customizing.templates.restapi

import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object RestApiTransferObjectTemplate {

    fun fillTemplate(restModelClass: RestModelClass): String {
        return """
            package ${restModelClass.transferObjectPackageName}
            
            import ${restModelClass.kotlinModelClass.kotlinPackage}.${restModelClass.kotlinModelClass.kotlinClassName}
            
            import ch.senegal.example.frontendapi.controller.commons.${restModelClass.transferObjectIdFieldTypeName}
            
            data class ${restModelClass.transferObjectBaseName}TO(
                val ${restModelClass.transferObjectIdFieldName}: ${restModelClass.transferObjectIdFieldTypeName},${StringTemplateHelper.forEach(restModelClass.fields()) { field ->
            """
                val ${field.kotlinModelField.kotlinFieldName}: ${field.kotlinModelField.kotlinFieldType},"""
                }}
            ) {
                companion object {
                    internal fun fromDomain(domainInstance: ${restModelClass.kotlinModelClass.kotlinClassName}) = ${restModelClass.transferObjectBaseName}TO(
                        ${restModelClass.transferObjectIdFieldName} = ${restModelClass.transferObjectIdFieldTypeName}(domainInstance.${restModelClass.kotlinModelClass.idFieldName}.value),${StringTemplateHelper.forEach(restModelClass.fields()) { field ->
                    """
                        ${field.kotlinModelField.kotlinFieldName} = domainInstance.${field.kotlinModelField.kotlinFieldName},""" }}
                    )
                }
            }
            
        """.identForMarker()
    }
}
