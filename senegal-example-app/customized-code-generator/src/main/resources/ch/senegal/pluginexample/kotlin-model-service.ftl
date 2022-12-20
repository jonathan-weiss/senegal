package ${templateModel.KotlinModelPackage}

import ch.senegal.example.sharedservice.tx.Transactional
import org.springframework.stereotype.Service

@Service
class ${templateModel.KotlinModelClassName}Service(
    private val repository: ${templateModel.KotlinModelClassName}Repository,
) {

    fun get${templateModel.KotlinModelClassName}(${templateModel.KotlinModelIdField}: ${templateModel.KotlinModelIdFieldType}): ${templateModel.KotlinModelClassName} {
        return repository.fetch${templateModel.KotlinModelClassName}ById(${templateModel.KotlinModelIdField})
    }


    @Transactional
    fun create${templateModel.KotlinModelClassName}(instruction: Create${templateModel.KotlinModelClassName}Instruction): ${templateModel.KotlinModelClassName} {
        val instance = ${templateModel.KotlinModelClassName}.create(instruction)
        repository.insert${templateModel.KotlinModelClassName}(instance)
        return get${templateModel.KotlinModelClassName}(instance.${templateModel.KotlinModelIdField})
    }

    @Transactional
    fun update${templateModel.KotlinModelClassName}(instruction: Update${templateModel.KotlinModelClassName}Instruction): ${templateModel.KotlinModelClassName} {
        val existingEntry = repository.fetch${templateModel.KotlinModelClassName}ById(instruction.${templateModel.KotlinModelIdField})
        existingEntry.update(instruction)
        repository.update${templateModel.KotlinModelClassName}(existingEntry)
        return get${templateModel.KotlinModelClassName}(instruction.${templateModel.KotlinModelIdField})
    }

    @Transactional
    fun delete${templateModel.KotlinModelClassName}(instruction: Delete${templateModel.KotlinModelClassName}Instruction) {
        val existingEntry = repository.fetch${templateModel.KotlinModelClassName}ById(instruction.${templateModel.KotlinModelIdField})
        repository.delete${templateModel.KotlinModelClassName}(existingEntry)
    }

    fun getListOfAll${templateModel.KotlinModelClassName}(): List<${templateModel.KotlinModelClassName}> {
        return repository.fetchAll${templateModel.KotlinModelClassName}()
    }
}
