package org.codeblessing.senegal.app

import org.codeblessing.senegal.domain.SenegalService
import org.codeblessing.senegal.frontendapi.FrontendApi
import org.codeblessing.senegal.persistence.SenegalPersistence
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackageClasses = [
        FrontendApi::class,
        SenegalService::class,
        SenegalPersistence::class,
    ]
)
class SenegalApplication

fun main(args: Array<String>) {
    runApplication<SenegalApplication>(*args)
}
