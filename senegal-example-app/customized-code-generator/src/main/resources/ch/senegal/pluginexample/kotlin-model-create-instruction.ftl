package ${templateModel.KotlinModelPackage}

class Create${templateModel.KotlinModelClassName}Instruction(<#list templateModel.childNodes as fieldNode>
    val ${fieldNode.KotlinModelFieldName}: ${fieldNode.KotlinModelFieldType},</#list>
)
