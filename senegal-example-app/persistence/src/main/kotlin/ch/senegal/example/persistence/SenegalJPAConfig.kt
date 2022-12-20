package ch.senegal.example.persistence

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackageClasses = [SenegalPersistence::class], enableDefaultTransactions = false)
@EntityScan(basePackageClasses = [SenegalPersistence::class])
class SenegalJPAConfig
