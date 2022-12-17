package ${templateModel.KotlinModelPackage}

class ${templateModel.KotlinModelClassName}(<#list templateModel.childNodes as fieldNode>
    val ${fieldNode.KotlinModelFieldName}: ${fieldNode.KotlinModelFieldType},</#list>
)



