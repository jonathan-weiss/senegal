package ch.senegal.example.frontendapi.controller.author

import ch.senegal.example.frontendapi.API
import ch.senegal.example.frontendapi.controller.commons.UuidTO
import ch.senegal.example.frontendapi.facade.AuthorFacade
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

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
        return facade.createAuthor(request)
    }

    @PutMapping("/entry")
    fun updateAuthor(@RequestBody request: UpdateAuthorInstructionTO): AuthorTO {
        return facade.updateAuthor(request)
    }

    // @DeleteMapping does not support request body
    @PostMapping("/entry/delete")
    fun deleteAuthor(@RequestBody request: DeleteAuthorInstructionTO) {
        facade.deleteAuthor(request)
    }

}
