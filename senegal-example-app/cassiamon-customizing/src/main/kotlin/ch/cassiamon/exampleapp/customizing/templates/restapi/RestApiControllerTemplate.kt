package ch.cassiamon.exampleapp.customizing.templates.restapi

import ch.cassiamon.tools.StringIdentHelper.identForMarker

object RestApiControllerTemplate {

    fun fillTemplate(restModelClass: RestModelClass): String {
        return """
            package ${restModelClass.controllerPackageName}
            
            import ch.senegal.example.frontendapi.API
            import  ${restModelClass.facadePackageName}.${restModelClass.facadeClassName}
            import ch.senegal.example.frontendapi.controller.commons.${restModelClass.transferObjectIdFieldTypeName}
            import ${restModelClass.facadePackageName}.${restModelClass.transferObjectBaseName}TO
            import ${restModelClass.facadePackageName}.Create${restModelClass.transferObjectBaseName}InstructionTO
            import ${restModelClass.facadePackageName}.Update${restModelClass.transferObjectBaseName}InstructionTO
            import ${restModelClass.facadePackageName}.Delete${restModelClass.transferObjectBaseName}InstructionTO
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
            @RequestMapping("${'$'}API/${restModelClass.urlPrefix}")
            @Service
            class ${restModelClass.controllerClassName}(
                private val facade: ${restModelClass.facadeClassName},
            ) {
            
                @GetMapping("/entry/{id}")
                fun get${restModelClass.kotlinModelClass.kotlinClassName}(@PathVariable("id") id: UUID): ${restModelClass.transferObjectBaseName}TO {
                    return facade.get${restModelClass.kotlinModelClass.kotlinClassName}ById(UuidTO(id))
                }
            
                @GetMapping("/all")
                fun getAll${restModelClass.kotlinModelClass.kotlinClassName}(): List<${restModelClass.transferObjectBaseName}TO> {
                    return facade.getAll${restModelClass.kotlinModelClass.kotlinClassName}()
                }
            
            
                @PostMapping("/entry")
                fun create${restModelClass.kotlinModelClass.kotlinClassName}(@RequestBody request: Create${restModelClass.transferObjectBaseName}InstructionTO): ${restModelClass.transferObjectBaseName}TO {
                    return facade.create${restModelClass.kotlinModelClass.kotlinClassName}(request)
                }
            
                @PutMapping("/entry")
                fun update${restModelClass.kotlinModelClass.kotlinClassName}(@RequestBody request: Update${restModelClass.transferObjectBaseName}InstructionTO): ${restModelClass.transferObjectBaseName}TO {
                    return facade.update${restModelClass.kotlinModelClass.kotlinClassName}(request)
                }
            
                // @DeleteMapping does not support request body
                @PostMapping("/entry/delete")
                fun delete${restModelClass.kotlinModelClass.kotlinClassName}(@RequestBody request: Delete${restModelClass.transferObjectBaseName}InstructionTO) {
                    facade.delete${restModelClass.kotlinModelClass.kotlinClassName}(request)
                }
            
            }
        """.identForMarker()
    }
}