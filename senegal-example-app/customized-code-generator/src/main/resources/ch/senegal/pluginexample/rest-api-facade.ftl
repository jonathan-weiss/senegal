package ${templateModel.RestApiFacadePackage}

import  ${templateModel.KotlinModelPackage}.${templateModel.KotlinModelClassName}Service
import ${templateModel.KotlinModelPackage}.${templateModel.KotlinModelIdFieldType}
import ch.senegal.example.frontendapi.controller.commons.${templateModel.RestApiTransferObjectIdFieldType}
import org.springframework.stereotype.Service

@Service
class ${templateModel.KotlinModelClassName}Facade(
    private val service: ${templateModel.KotlinModelClassName}Service,
) {

    fun get${templateModel.KotlinModelClassName}ById(id: UuidTO): ${templateModel.RestApiTransferObjectName} {
        val domainInstance = service.get${templateModel.KotlinModelClassName}(${templateModel.KotlinModelIdFieldType}(id.uuid))
        return ${templateModel.RestApiTransferObjectName}.fromDomain(domainInstance)
    }


    fun getAll${templateModel.KotlinModelClassName}(): List<${templateModel.RestApiTransferObjectName}> {
        return service.getListOfAll${templateModel.KotlinModelClassName}().map { ${templateModel.RestApiTransferObjectName}.fromDomain(it) }
    }
}
