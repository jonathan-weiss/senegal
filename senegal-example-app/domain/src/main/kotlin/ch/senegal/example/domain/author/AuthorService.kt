package ch.senegal.example.domain.author

import ch.senegal.example.sharedservice.tx.Transactional
import org.springframework.stereotype.Service

@Service
class AuthorService(
    private val repository: AuthorRepository,
) {

    fun getAuthor(authorId: AuthorId): Author {
        return repository.fetchAuthorById(authorId)
    }


    @Transactional
    fun createAuthor(instruction: CreateAuthorInstruction): Author {
        val instance = Author.create(instruction)
        repository.insertAuthor(instance)
        return getAuthor(instance.authorId)
    }

    @Transactional
    fun updateAuthor(instruction: UpdateAuthorInstruction): Author {
        val existingEntry = repository.fetchAuthorById(instruction.authorId)
        existingEntry.update(instruction)
        repository.updateAuthor(existingEntry)
        return getAuthor(instruction.authorId)
    }

    @Transactional
    fun deleteAuthor(instruction: DeleteAuthorInstruction) {
        val existingEntry = repository.fetchAuthorById(instruction.authorId)
        repository.deleteAuthor(existingEntry)
    }

    fun getListOfAllAuthor(): List<Author> {
        return repository.fetchAllAuthor()
    }

    fun getListOfFilteredAuthor(searchTerm: String): List<Author> {
        return repository.fetchAllAuthorFiltered(searchTerm)
    }

    fun searchAllAuthor(searchParam: SearchAuthorInstruction): List<Author> {
        // TODO implement that in SQL
        return repository.fetchAllAuthor()
    }

}
