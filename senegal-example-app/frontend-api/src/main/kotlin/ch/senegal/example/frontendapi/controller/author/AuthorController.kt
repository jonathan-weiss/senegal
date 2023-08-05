package ch.senegal.example.frontendapi.controller.author

import ch.senegal.example.frontendapi.API
import ch.senegal.example.frontendapi.controller.commons.UuidTO
import ch.senegal.example.frontendapi.facade.AuthorFacade
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("$API/author")
@Service
class AuthorController(
    private val facade: AuthorFacade,
) {

    @GetMapping("/entry/{id}")
    fun getAuthor(@PathVariable("id") id: UUID): AuthorTO {
        return facade.getAuthorById(UuidTO(id))
    }

    @GetMapping("/all")
    fun getAllAuthor(): List<AuthorTO> {
        return facade.getAllAuthor()
    }

    @PostMapping("/search")
    fun searchAuthor(@RequestBody searchParams: SearchAuthorInstructionTO): List<AuthorTO> {
        return facade.searchAllAuthor(searchParams)
    }

    @GetMapping("/all-filtered")
    fun getAllAuthorFiltered(@RequestParam("searchTerm") searchTerm: String): List<AuthorTO> {
        return facade.getAllAuthorFiltered(searchTerm)
    }


    @PostMapping("/entry")
    fun createAuthor(@RequestBody request: CreateAuthorInstructionTO): AuthorTO {
        if(request.firstname == "error") {
            throw RuntimeException("O la la. There was an error.")
        }
        return facade.createAuthor(request)
    }

    @PutMapping("/entry")
    fun updateAuthor(@RequestBody request: UpdateAuthorInstructionTO): AuthorTO {
        if(request.firstname == "error") {
            throw RuntimeException("O la la. There was an error.")
        }
        return facade.updateAuthor(request)
    }

    // @DeleteMapping does not support request body
    @PostMapping("/entry/delete")
    fun deleteAuthor(@RequestBody request: DeleteAuthorInstructionTO) {
        try {
            facade.deleteAuthor(request)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.message, ex)
        }
    }

}
