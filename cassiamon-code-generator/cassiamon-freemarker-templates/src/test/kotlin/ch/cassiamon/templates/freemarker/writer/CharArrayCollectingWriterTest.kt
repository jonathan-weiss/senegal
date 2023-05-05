package ch.cassiamon.templates.freemarker.writer

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class CharArrayCollectingWriterTest {

    @Test
    fun `test with empty writer`() {
        val writer = CharArrayCollectingWriter()


        assertArrayEquals(CharArray(0), writer.getCharArray())
    }

    @Test
    fun `test with writer that writes one word`() {
        val writer = CharArrayCollectingWriter()

        val charArray = "hello".toCharArray()
        writer.write(charArray, 0, charArray.size)

        assertArrayEquals(charArray, writer.getCharArray())
    }

    @Test
    fun `test with writer that writes some words`() {
        val writer = CharArrayCollectingWriter()

        val word1 = "Hello"
        val word2 = " world."
        val word3 = " Morning."

        writer.write(word1.toCharArray(), 0, word1.length)
        writer.write(word2.toCharArray(), 0, word2.length)
        writer.write(word3.toCharArray(), 0, word3.length)

        val expectedResult = listOf(word1, word2, word3).joinToString("").toCharArray()
        assertArrayEquals(expectedResult, writer.getCharArray())
    }

}
