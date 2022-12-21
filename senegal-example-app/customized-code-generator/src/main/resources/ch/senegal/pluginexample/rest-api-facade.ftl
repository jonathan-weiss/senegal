package ${templateModel.RestApiFacadePackage}

import  ${templateModel.KotlinModelPackage}.${templateModel.KotlinModelClassName}Service
import ${templateModel.KotlinModelPackage}.${templateModel.KotlinModelIdFieldType}
import ch.senegal.example.frontendapi.controller.commons.${templateModel.RestApiTransferObjectIdFieldType}
import org.springframework.stereotype.Service

@Service
class ${templateModel.KotlinModelClassName}Facade(
    private val service: ${templateModel.KotlinModelClassName}Service,
) {

    fun get${templateModel.KotlinModelClassName}ById(id: UuidTO): ${templateModel.RestApiTransferObjectName}TO {
        val domainInstance = service.get${templateModel.KotlinModelClassName}(${templateModel.KotlinModelIdFieldType}(id.uuid))
        return ${templateModel.RestApiTransferObjectName}TO.fromDomain(domainInstance)
    }

    fun getAll${templateModel.KotlinModelClassName}(): List<${templateModel.RestApiTransferObjectName}TO> {
        return service.getListOfAll${templateModel.KotlinModelClassName}().map { ${templateModel.RestApiTransferObjectName}TO.fromDomain(it) }
    }

    fun create${templateModel.KotlinModelClassName}(instruction: Create${templateModel.RestApiTransferObjectName}InstructionTO): ${templateModel.RestApiTransferObjectName}TO {
        val created${templateModel.KotlinModelClassName} = service.create${templateModel.KotlinModelClassName}(instruction.toDomain())
        return ${templateModel.RestApiTransferObjectName}TO.fromDomain(created${templateModel.KotlinModelClassName})
    }

    fun update${templateModel.KotlinModelClassName}(instruction: Update${templateModel.RestApiTransferObjectName}InstructionTO): ${templateModel.RestApiTransferObjectName}TO {
        val updated${templateModel.KotlinModelClassName} = service.update${templateModel.KotlinModelClassName}(instruction.toDomain())
        return ${templateModel.RestApiTransferObjectName}TO.fromDomain(updated${templateModel.KotlinModelClassName})
    }

    fun delete${templateModel.KotlinModelClassName}(instruction: Delete${templateModel.RestApiTransferObjectName}InstructionTO) {
        service.delete${templateModel.KotlinModelClassName}(instruction.toDomain())
    }
}
