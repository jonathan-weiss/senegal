package ch.cassiamon.pluginapi.template.helper

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class StringContentByteIteratorTest {

    @Test
    fun `test with zero buffer size throws an exception`() {
        assertThrows(
            IllegalArgumentException::class.java) {
            StringContentByteIterator("", 0)
        }
    }

    @Test
    fun `test missing hasNext calls throws an exception`() {
        val iterator = StringContentByteIterator("a", 2)

        iterator.nextByte()
        assertThrows(
            IllegalStateException::class.java) {
            iterator.nextByte()
        }
    }


    @Test
    fun `test with zero character`() {
        val iterator = StringContentByteIterator("", 2)

        assertEquals(false, iterator.hasNext())
    }

    @Test
    fun `test with only one character`() {
        val iterator = StringContentByteIterator("a", 2)

        assertEquals(true, iterator.hasNext())
        iterator.nextByte()
        assertEquals(false, iterator.hasNext())

    }

    @Test
    fun `test if hasNext can be called multiple times`() {
        val iterator = StringContentByteIterator("a", 2)

        assertEquals(true, iterator.hasNext())
        assertEquals(true, iterator.hasNext()) // check if hasNext can be called multiple times
        assertEquals(true, iterator.hasNext()) // check if hasNext can be called multiple times
        iterator.nextByte()
        assertEquals(false, iterator.hasNext())

    }

    @Test
    fun `test with same number of characters as in buffer`() {
        val iterator = StringContentByteIterator("ab", 2)

        assertEquals(true, iterator.hasNext()) // a
        iterator.nextByte()
        assertEquals(true, iterator.hasNext()) // b
        iterator.nextByte()
        assertEquals(false, iterator.hasNext())

    }

    @Test
    fun `test with more number of characters as buffer size`() {
        val iterator = StringContentByteIterator("abcde", 2)

        assertEquals(true, iterator.hasNext()) // a
        iterator.nextByte()
        assertEquals(true, iterator.hasNext()) // b
        iterator.nextByte()
        assertEquals(true, iterator.hasNext()) // c
        iterator.nextByte()
        assertEquals(true, iterator.hasNext()) // d
        iterator.nextByte()
        assertEquals(true, iterator.hasNext()) // e
        iterator.nextByte()
        assertEquals(false, iterator.hasNext())

    }

}
