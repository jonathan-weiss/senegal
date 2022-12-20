package ${templateModel.KotlinModelPackage}

class ${templateModel.KotlinModelClassName}(
    val ${templateModel.KotlinModelIdFieldName}: ${templateModel.KotlinModelIdFieldType},<#list templateModel.childNodes as fieldNode>
    var ${fieldNode.KotlinModelFieldName}: ${fieldNode.KotlinModelFieldType},</#list>
) {
    companion object {
        internal fun create(instruction: Create${templateModel.KotlinModelClassName}Instruction) = ${templateModel.KotlinModelClassName}(
            ${templateModel.KotlinModelIdFieldName} = ${templateModel.KotlinModelIdFieldType}.random(),<#list templateModel.childNodes as fieldNode>
            ${fieldNode.KotlinModelFieldName} = instruction.${fieldNode.KotlinModelFieldName},</#list>
        )
    }

    internal fun update(instruction: Update${templateModel.KotlinModelClassName}Instruction) {<#list templateModel.childNodes as fieldNode>
        this.${fieldNode.KotlinModelFieldName} = instruction.${fieldNode.KotlinModelFieldName}</#list>
    }
}




