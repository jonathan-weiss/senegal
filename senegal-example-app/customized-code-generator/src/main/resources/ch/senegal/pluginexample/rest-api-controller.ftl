package ${templateModel.RestApiControllerPackage}

import ch.senegal.example.frontendapi.API
import  ${templateModel.RestApiFacadePackage}.${templateModel.KotlinModelClassName}Facade
import ${templateModel.KotlinModelPackage}.${templateModel.KotlinModelIdFieldType}
import ch.senegal.example.frontendapi.controller.commons.${templateModel.RestApiTransferObjectIdFieldType}
import ${templateModel.RestApiFacadePackage}.${templateModel.RestApiTransferObjectName}
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("$API/${templateModel.RestApiUrlPrefixName}")
@Service
class ${templateModel.KotlinModelClassName}Controller(
    private val facade: ${templateModel.KotlinModelClassName}Facade,
) {

    @GetMapping("/entry/{id}")
    fun get${templateModel.KotlinModelClassName}(@PathVariable("id") id: UUID): ${templateModel.RestApiTransferObjectName} {
        return facade.get${templateModel.KotlinModelClassName}ById(UuidTO(id))
    }

    @GetMapping("/all")
    fun getAll${templateModel.KotlinModelClassName}(): List<${templateModel.RestApiTransferObjectName}> {
        return facade.getAll${templateModel.KotlinModelClassName}()
    }
}


