package ${templateModel.RestApiControllerPackage}

import ch.senegal.example.frontendapi.API
import  ${templateModel.RestApiFacadePackage}.${templateModel.KotlinModelClassName}Facade
import ${templateModel.KotlinModelPackage}.${templateModel.KotlinModelIdFieldType}
import ch.senegal.example.frontendapi.controller.commons.${templateModel.RestApiTransferObjectIdFieldType}
import ${templateModel.RestApiFacadePackage}.${templateModel.RestApiTransferObjectName}TO
import ${templateModel.RestApiFacadePackage}.Create${templateModel.RestApiTransferObjectName}InstructionTO
import ${templateModel.RestApiFacadePackage}.Update${templateModel.RestApiTransferObjectName}InstructionTO
import ${templateModel.RestApiFacadePackage}.Delete${templateModel.RestApiTransferObjectName}InstructionTO
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
    fun get${templateModel.KotlinModelClassName}(@PathVariable("id") id: UUID): ${templateModel.RestApiTransferObjectName}TO {
        return facade.get${templateModel.KotlinModelClassName}ById(UuidTO(id))
    }

    @GetMapping("/all")
    fun getAll${templateModel.KotlinModelClassName}(): List<${templateModel.RestApiTransferObjectName}TO> {
        return facade.getAll${templateModel.KotlinModelClassName}()
    }


    @PostMapping("/entry")
    fun create${templateModel.KotlinModelClassName}(@RequestBody request: Create${templateModel.RestApiTransferObjectName}InstructionTO): ${templateModel.RestApiTransferObjectName}TO {
        return facade.create${templateModel.KotlinModelClassName}(request)
    }

    @PutMapping("/entry")
    fun update${templateModel.KotlinModelClassName}(@RequestBody request: Update${templateModel.RestApiTransferObjectName}InstructionTO): ${templateModel.RestApiTransferObjectName}TO {
        return facade.update${templateModel.KotlinModelClassName}(request)
    }

    // @DeleteMapping does not support request body
    @PostMapping("/entry/delete")
    fun delete${templateModel.KotlinModelClassName}(@RequestBody request: Delete${templateModel.RestApiTransferObjectName}InstructionTO) {
        facade.delete${templateModel.KotlinModelClassName}(request)
    }

}


