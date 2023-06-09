package ch.cassiamon.exampleapp.customizing.templates.restapi

import ch.cassiamon.tools.StringIdentHelper.identForMarker

object RestApiFacadeTemplate {

    fun fillTemplate(restModelClass: RestModelClass): String {
        return """
            package ${restModelClass.facadePackageName}
            
            import ${restModelClass.kotlinModelClass.kotlinPackage}.${restModelClass.kotlinModelClass.kotlinClassName}Service
            import ${restModelClass.kotlinModelClass.kotlinPackage}.${restModelClass.kotlinModelClass.idFieldType}
            import ch.senegal.example.frontendapi.controller.commons.${restModelClass.transferObjectIdFieldTypeName}
            import org.springframework.stereotype.Service
            
            @Service
            class ${restModelClass.facadeClassName}(
                private val service: ${restModelClass.kotlinModelClass.kotlinClassName}Service,
            ) {
            
                fun get${restModelClass.kotlinModelClass.kotlinClassName}ById(id: UuidTO): ${restModelClass.transferObjectBaseName}TO {
                    val domainInstance = service.get${restModelClass.kotlinModelClass.kotlinClassName}(${restModelClass.kotlinModelClass.idFieldType}(id.uuid))
                    return ${restModelClass.transferObjectBaseName}TO.fromDomain(domainInstance)
                }
            
                fun getAll${restModelClass.kotlinModelClass.kotlinClassName}(): List<${restModelClass.transferObjectBaseName}TO> {
                    return service.getListOfAll${restModelClass.kotlinModelClass.kotlinClassName}().map { ${restModelClass.transferObjectBaseName}TO.fromDomain(it) }
                }
            
                fun create${restModelClass.kotlinModelClass.kotlinClassName}(instruction: Create${restModelClass.transferObjectBaseName}InstructionTO): ${restModelClass.transferObjectBaseName}TO {
                    val created${restModelClass.kotlinModelClass.kotlinClassName} = service.create${restModelClass.kotlinModelClass.kotlinClassName}(instruction.toDomain())
                    return ${restModelClass.transferObjectBaseName}TO.fromDomain(created${restModelClass.kotlinModelClass.kotlinClassName})
                }
            
                fun update${restModelClass.kotlinModelClass.kotlinClassName}(instruction: Update${restModelClass.transferObjectBaseName}InstructionTO): ${restModelClass.transferObjectBaseName}TO {
                    val updated${restModelClass.kotlinModelClass.kotlinClassName} = service.update${restModelClass.kotlinModelClass.kotlinClassName}(instruction.toDomain())
                    return ${restModelClass.transferObjectBaseName}TO.fromDomain(updated${restModelClass.kotlinModelClass.kotlinClassName})
                }
            
                fun delete${restModelClass.kotlinModelClass.kotlinClassName}(instruction: Delete${restModelClass.transferObjectBaseName}InstructionTO) {
                    service.delete${restModelClass.kotlinModelClass.kotlinClassName}(instruction.toDomain())
                }
            }
            
        """.identForMarker()
    }
}
