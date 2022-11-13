package ch.senegal.example.shareddomain

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import java.util.UUID

class IdTest {

    @Test
    fun `create typed uuid-id from string`() {
        val uuidStr = "a4ea357c-4968-4d80-8b5d-64300c57a298"
        val uuid = UUID.fromString(uuidStr)
        val testId = TestId.parse(uuidStr)

        assertThat(testId, equalTo(TestId(uuid)))
    }

    @Test
    fun `handle null`() {
        assertThat(TestId.ofNullable(null), nullValue())
        assertThat(TestId.parseNullable(null), nullValue())
    }
}

private data class TestId(override val value: UUID) : Id<UUID> {
    companion object Factory : UUIDIdFactory<TestId>(TestId::class)
}
