package ch.senegal.example.architecturetests

import ch.senegal.example.architecturetests.SenegalCodebase.getTypesAnnotatedWith
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

class JpaConfigTest {

    /**
     * By ensuring that default JPA transactions are disabled, we are forced to explicitly define the transaction
     * boundaries. We are therefore in control and can ensure that nothing gets partially committed.
     */
    @TestFactory
    fun `ensure default jpa transactions are disabled`(): List<DynamicTest> {
        return getTypesAnnotatedWith(EnableJpaRepositories::class)
            .map { clazz ->
                dynamicTest(clazz.name) {
                    assertHasNoDefaultTransactions(clazz)
                }
            }
    }

    private fun assertHasNoDefaultTransactions(clazz: Class<*>) {
        val annotation = clazz.getAnnotation(EnableJpaRepositories::class.java)
        assertThat("Default transactions aren't disabled on $clazz", annotation.enableDefaultTransactions, `is`(false))
    }
}
