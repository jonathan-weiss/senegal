package ch.cassiamon.exampleapp.customizing.templates.kotlinmodel

import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper.forEach

object KotlinModelServiceTemplate {

    fun fillTemplate(kotlinModelClass: KotlinModelClass): String {
        return """
            package ${kotlinModelClass.kotlinPackage}
            
            import ch.senegal.example.sharedservice.tx.Transactional
            import org.springframework.stereotype.Service
            
            @Service
            class ${kotlinModelClass.kotlinClassName}Service(
                private val repository: ${kotlinModelClass.kotlinClassName}Repository,
            ) {
            
                fun get${kotlinModelClass.kotlinClassName}(${kotlinModelClass.idFieldName}: ${kotlinModelClass.idFieldType}): ${kotlinModelClass.kotlinClassName} {
                    return repository.fetch${kotlinModelClass.kotlinClassName}ById(${kotlinModelClass.idFieldName})
                }
            
            
                @Transactional
                fun create${kotlinModelClass.kotlinClassName}(instruction: Create${kotlinModelClass.kotlinClassName}Instruction): ${kotlinModelClass.kotlinClassName} {
                    val instance = ${kotlinModelClass.kotlinClassName}.create(instruction)
                    repository.insert${kotlinModelClass.kotlinClassName}(instance)
                    return get${kotlinModelClass.kotlinClassName}(instance.${kotlinModelClass.idFieldName})
                }
            
                @Transactional
                fun update${kotlinModelClass.kotlinClassName}(instruction: Update${kotlinModelClass.kotlinClassName}Instruction): ${kotlinModelClass.kotlinClassName} {
                    val existingEntry = repository.fetch${kotlinModelClass.kotlinClassName}ById(instruction.${kotlinModelClass.idFieldName})
                    existingEntry.update(instruction)
                    repository.update${kotlinModelClass.kotlinClassName}(existingEntry)
                    return get${kotlinModelClass.kotlinClassName}(instruction.${kotlinModelClass.idFieldName})
                }
            
                @Transactional
                fun delete${kotlinModelClass.kotlinClassName}(instruction: Delete${kotlinModelClass.kotlinClassName}Instruction) {
                    val existingEntry = repository.fetch${kotlinModelClass.kotlinClassName}ById(instruction.${kotlinModelClass.idFieldName})
                    repository.delete${kotlinModelClass.kotlinClassName}(existingEntry)
                }
            
                fun getListOfAll${kotlinModelClass.kotlinClassName}(): List<${kotlinModelClass.kotlinClassName}> {
                    return repository.fetchAll${kotlinModelClass.kotlinClassName}()
                }
            }
        """.identForMarker()
    }
}
