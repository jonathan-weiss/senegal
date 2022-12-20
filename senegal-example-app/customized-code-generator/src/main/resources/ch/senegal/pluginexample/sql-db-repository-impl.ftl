package ${templateModel.SqlDbJpaEntityPackage}

import ${templateModel.KotlinModelPackage}.${templateModel.KotlinModelClassName}
import ${templateModel.KotlinModelPackage}.${templateModel.KotlinModelClassName}Repository
import ${templateModel.KotlinModelPackage}.${templateModel.KotlinModelIdFieldType}
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository


@Repository
class ${templateModel.KotlinModelClassName}RepositoryImpl(
private val jpaRepository: ${templateModel.SqlDbJpaEntityName}Repository
) : ${templateModel.KotlinModelClassName}Repository {

    override fun fetch${templateModel.KotlinModelClassName}ById(${templateModel.KotlinModelIdFieldName}: ${templateModel.KotlinModelIdFieldType}): ${templateModel.KotlinModelClassName} {
        val entry = jpaRepository.findByIdOrNull(${templateModel.KotlinModelIdFieldName}.value) ?: throw RuntimeException("${templateModel.KotlinModelClassName} with id \$${templateModel.KotlinModelIdFieldName} not found")
        return entry.toDomain()
    }

    override fun fetchAll${templateModel.KotlinModelClassName}(): List<${templateModel.KotlinModelClassName}> {
        return jpaRepository.findAll().map { it.toDomain() }
    }

    override fun insert${templateModel.KotlinModelClassName}(domainInstance: ${templateModel.KotlinModelClassName}) {
        jpaRepository.save(${templateModel.SqlDbJpaEntityName}.fromDomain(domainInstance))
    }

    override fun update${templateModel.KotlinModelClassName}(domainInstance: ${templateModel.KotlinModelClassName}) {
        jpaRepository.save(${templateModel.SqlDbJpaEntityName}.fromDomain(domainInstance))
    }

    override fun delete${templateModel.KotlinModelClassName}(domainInstance: ${templateModel.KotlinModelClassName}) {
        jpaRepository.delete(${templateModel.SqlDbJpaEntityName}.fromDomain(domainInstance))
    }
}
