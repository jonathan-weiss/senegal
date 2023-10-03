package ch.cassiamon.exampleapp.customizing.templates.restapi

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object RestApiControllerTemplate {

    fun fillTemplate(restModelClass: RestModelClass): String {
        return """
            package ${restModelClass.controllerPackageName}
            
            import ch.senegal.example.frontendapi.API
            import  ${restModelClass.facadePackageName}.${restModelClass.facadeClassName}
            import ${restModelClass.kotlinModelClass.kotlinPackage}.${restModelClass.kotlinModelClass.idFieldType}
            import ${restModelClass.facadePackageName}.${restModelClass.transferObjectBaseName}TO
            import ${restModelClass.facadePackageName}.Create${restModelClass.transferObjectBaseName}InstructionTO
            import ${restModelClass.facadePackageName}.Update${restModelClass.transferObjectBaseName}InstructionTO
            import ${restModelClass.facadePackageName}.Delete${restModelClass.transferObjectBaseName}InstructionTO
            import ${restModelClass.facadePackageName}.Search${restModelClass.transferObjectBaseName}InstructionTO
            import org.springframework.stereotype.Service
            import org.springframework.web.bind.annotation.DeleteMapping
            import org.springframework.web.bind.annotation.GetMapping
            import org.springframework.web.bind.annotation.PathVariable
            import org.springframework.web.bind.annotation.PostMapping
            import org.springframework.web.bind.annotation.PutMapping
            import org.springframework.web.bind.annotation.RequestBody
            import org.springframework.web.bind.annotation.RequestMapping
            import org.springframework.web.bind.annotation.RequestParam
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
                    return facade.get${restModelClass.kotlinModelClass.kotlinClassName}ById(${restModelClass.kotlinModelClass.idFieldType}(id))
                }
            
                @GetMapping("/all")
                fun getAll${restModelClass.kotlinModelClass.kotlinClassName}(): List<${restModelClass.transferObjectBaseName}TO> {
                    return facade.getAll${restModelClass.kotlinModelClass.kotlinClassName}()
                }
            
                @PostMapping("/search")
                fun search${restModelClass.kotlinModelClass.kotlinClassName}(@RequestBody searchParams: Search${restModelClass.kotlinModelClass.kotlinClassName}InstructionTO): List<${restModelClass.transferObjectBaseName}TO> {
                    return facade.searchAll${restModelClass.kotlinModelClass.kotlinClassName}(searchParams)
                }
            
                @GetMapping("/all-filtered")
                fun getAll${restModelClass.kotlinModelClass.kotlinClassName}Filtered(@RequestParam("searchTerm") searchTerm: String): List<${restModelClass.transferObjectBaseName}TO> {
                    return facade.getAll${restModelClass.kotlinModelClass.kotlinClassName}Filtered(searchTerm)
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
