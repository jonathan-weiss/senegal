package ${templateModel.KotlinModelPackage}

class Update${templateModel.KotlinModelClassName}Instruction(
    val ${templateModel.KotlinModelIdField}: ${templateModel.KotlinModelIdFieldType},<#list templateModel.childNodes as fieldNode>
    val ${fieldNode.KotlinModelFieldName}: ${fieldNode.KotlinModelFieldType},</#list>
)
