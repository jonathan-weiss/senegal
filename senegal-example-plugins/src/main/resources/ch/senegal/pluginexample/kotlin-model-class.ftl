package ${templateModel.KotlinModelPackage}

class ${templateModel.KotlinModelClassName}(<#list templateModel.childNodes as fieldNode>
    val ${fieldNode.EntityAttributeName}: ${fieldNode.KotlinFieldType},</#list>
)



