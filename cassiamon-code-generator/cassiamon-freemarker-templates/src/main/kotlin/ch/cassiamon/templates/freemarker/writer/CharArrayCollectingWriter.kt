package ch.cassiamon.templates.freemarker.writer

import java.io.CharArrayWriter
import java.io.Writer

class CharArrayCollectingWriter(): Writer() {

    private val charArrayWriter = CharArrayWriter()
    override fun close() {
        charArrayWriter.close()
    }

    override fun flush() {
        charArrayWriter.flush()
    }

    override fun write(cbuf: CharArray, off: Int, len: Int) {
        charArrayWriter.write(cbuf, off, len)
    }

    fun getCharArray(): CharArray {
        return charArrayWriter.toCharArray()
    }
}

