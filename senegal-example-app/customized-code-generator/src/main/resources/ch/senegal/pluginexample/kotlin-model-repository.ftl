package ${templateModel.KotlinModelPackage}

interface ${templateModel.KotlinModelClassName}Repository {
    fun fetch${templateModel.KotlinModelClassName}ById(${templateModel.KotlinModelIdField}: ${templateModel.KotlinModelIdFieldType}): ${templateModel.KotlinModelClassName}
    fun fetchAll${templateModel.KotlinModelClassName}(): List<${templateModel.KotlinModelClassName}>

    fun insert${templateModel.KotlinModelClassName}(instance: ${templateModel.KotlinModelClassName})
    fun update${templateModel.KotlinModelClassName}(instance: ${templateModel.KotlinModelClassName})
    fun delete${templateModel.KotlinModelClassName}(instance: ${templateModel.KotlinModelClassName})
}


