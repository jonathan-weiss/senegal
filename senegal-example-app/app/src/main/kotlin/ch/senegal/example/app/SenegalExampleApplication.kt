package ch.senegal.example.app

import ch.senegal.example.frontendapi.FrontendApi
import ch.senegal.example.persistence.SenegalPersistence
import ch.senegal.example.domain.SenegalService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackageClasses = [
        FrontendApi::class,
        SenegalService::class, SenegalPersistence::class,
    ]
)
class SenegalExampleApplication

fun main(args: Array<String>) {
    runApplication<SenegalExampleApplication>(*args)
}
