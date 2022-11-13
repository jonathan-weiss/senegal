package ch.senegal.example.frontendapi.controller.commons

import ch.senegal.example.shareddomain.Id
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class UuidTO(
    val uuid: UUID,
) {
    internal constructor(id: Id<UUID>) : this(id.value)
}

data class LocalDateTO(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date: LocalDate,
)

data class LocalDateTimeTO(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val date: LocalDateTime,
)
