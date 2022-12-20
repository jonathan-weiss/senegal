package ${templateModel.SqlDbJpaEntityPackage}

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ${templateModel.SqlDbJpaEntityName}Repository : JpaRepository<${templateModel.SqlDbJpaEntityName}, UUID> {

}
