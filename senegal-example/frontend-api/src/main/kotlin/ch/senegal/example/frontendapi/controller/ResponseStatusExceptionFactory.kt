package ch.senegal.example.frontendapi.controller

import org.springframework.http.HttpStatus.I_AM_A_TEAPOT
import org.springframework.web.server.ResponseStatusException

object ResponseStatusExceptionFactory {

    fun createValidationMessageResponseException(validationMessage: String): ResponseStatusException {
        // use the fancy status code 418 for all validation errors
        throw ResponseStatusException(I_AM_A_TEAPOT, validationMessage)
    }
}
