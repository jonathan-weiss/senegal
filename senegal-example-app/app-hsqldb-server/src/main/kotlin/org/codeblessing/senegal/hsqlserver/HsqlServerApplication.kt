package org.codeblessing.senegal.hsqlserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackageClasses = [
        HsqlServerApplication::class
    ]
)
class HsqlServerApplication

fun main(args: Array<String>) {
    runApplication<HsqlServerApplication>(*args)
}
