package org.codeblessing.senegal.frontendapi.configuration

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:jackson.properties")
class JacksonConfiguration {

    @Bean
    fun kotlinModule(): Module {
        return KotlinModule.Builder()
            .configure(KotlinFeature.StrictNullChecks, enabled = true)
            .build()
    }
}
