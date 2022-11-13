package ch.senegal.example.frontendapi.controller

import ch.senegal.example.shareddomain.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException

@ControllerAdvice
class RestControllerExceptionHandler {

    @ExceptionHandler(ResponseStatusException::class)
    fun handleStatusException(ex: ResponseStatusException): ResponseEntity<String> {
        throw ex
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: Exception): ResponseEntity<out Any> {

        val httpStatus: HttpStatus = when (ex) {
            is NotFoundException -> NOT_FOUND
            else -> INTERNAL_SERVER_ERROR
        }

        println("Http error: $ex")

        return ResponseEntity.status(httpStatus).build()
    }
}
