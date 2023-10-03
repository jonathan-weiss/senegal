package org.codeblessing.senegal.domain.author

import org.codeblessing.senegal.sharedservice.tx.Transactional
import org.springframework.stereotype.Service

@Service
class AuthorService(
    private val repository: org.codeblessing.senegal.domain.author.AuthorRepository,
) {

    fun getAuthor(authorId: org.codeblessing.senegal.domain.author.AuthorId): org.codeblessing.senegal.domain.author.Author {
        return repository.fetchAuthorById(authorId)
    }


    @Transactional
    fun createAuthor(instruction: org.codeblessing.senegal.domain.author.CreateAuthorInstruction): org.codeblessing.senegal.domain.author.Author {
        val instance = org.codeblessing.senegal.domain.author.Author.create(instruction)
        repository.insertAuthor(instance)
        return getAuthor(instance.authorId)
    }

    @Transactional
    fun updateAuthor(instruction: org.codeblessing.senegal.domain.author.UpdateAuthorInstruction): org.codeblessing.senegal.domain.author.Author {
        val existingEntry = repository.fetchAuthorById(instruction.authorId)
        existingEntry.update(instruction)
        repository.updateAuthor(existingEntry)
        return getAuthor(instruction.authorId)
    }

    @Transactional
    fun deleteAuthor(instruction: org.codeblessing.senegal.domain.author.DeleteAuthorInstruction) {
        val existingEntry = repository.fetchAuthorById(instruction.authorId)
        repository.deleteAuthor(existingEntry)
    }

    fun getListOfAllAuthor(): List<org.codeblessing.senegal.domain.author.Author> {
        return repository.fetchAllAuthor()
    }

    fun getListOfFilteredAuthor(searchTerm: String): List<org.codeblessing.senegal.domain.author.Author> {
        return repository.fetchAllAuthorFiltered(searchTerm)
    }

    fun searchAllAuthor(searchParam: org.codeblessing.senegal.domain.author.SearchAuthorInstruction): List<org.codeblessing.senegal.domain.author.Author> {
        // TODO implement that in SQL
        return repository.fetchAllAuthor()
    }

}
