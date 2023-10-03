package org.codeblessing.senegal.frontendapi.controller.author

import org.codeblessing.senegal.frontendapi.API
import org.codeblessing.senegal.frontendapi.controller.commons.UuidTO
import org.codeblessing.senegal.frontendapi.facade.AuthorFacade
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("${org.codeblessing.senegal.frontendapi.API}/author")
@Service
class AuthorController(
    private val facade: org.codeblessing.senegal.frontendapi.facade.AuthorFacade,
) {

    @GetMapping("/entry/{id}")
    fun getAuthor(@PathVariable("id") id: UUID): org.codeblessing.senegal.frontendapi.controller.author.AuthorTO {
        return facade.getAuthorById(org.codeblessing.senegal.frontendapi.controller.commons.UuidTO(id))
    }

    @GetMapping("/all")
    fun getAllAuthor(): List<org.codeblessing.senegal.frontendapi.controller.author.AuthorTO> {
        return facade.getAllAuthor()
    }

    @PostMapping("/search")
    fun searchAuthor(@RequestBody searchParams: org.codeblessing.senegal.frontendapi.controller.author.SearchAuthorInstructionTO): List<org.codeblessing.senegal.frontendapi.controller.author.AuthorTO> {
        return facade.searchAllAuthor(searchParams)
    }

    @GetMapping("/all-filtered")
    fun getAllAuthorFiltered(@RequestParam("searchTerm") searchTerm: String): List<org.codeblessing.senegal.frontendapi.controller.author.AuthorTO> {
        return facade.getAllAuthorFiltered(searchTerm)
    }


    @PostMapping("/entry")
    fun createAuthor(@RequestBody request: org.codeblessing.senegal.frontendapi.controller.author.CreateAuthorInstructionTO): org.codeblessing.senegal.frontendapi.controller.author.AuthorTO {
        if(request.firstname == "error") {
            throw RuntimeException("O la la. There was an error.")
        }
        return facade.createAuthor(request)
    }

    @PutMapping("/entry")
    fun updateAuthor(@RequestBody request: org.codeblessing.senegal.frontendapi.controller.author.UpdateAuthorInstructionTO): org.codeblessing.senegal.frontendapi.controller.author.AuthorTO {
        if(request.firstname == "error") {
            throw RuntimeException("O la la. There was an error.")
        }
        return facade.updateAuthor(request)
    }

    // @DeleteMapping does not support request body
    @PostMapping("/entry/delete")
    fun deleteAuthor(@RequestBody request: org.codeblessing.senegal.frontendapi.controller.author.DeleteAuthorInstructionTO) {
        try {
            facade.deleteAuthor(request)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.message, ex)
        }
    }

}
