package ch.cassiamon.pluginapi.template

import java.io.InputStream
import java.lang.IllegalArgumentException
import java.nio.charset.Charset

class StringContentByteIterator (content: String, bufferSize: Int = 10): ByteIterator() {

    init {
        if(bufferSize <= 0) {
            throw IllegalArgumentException("Buffer size must be greater than zero.")
        }
    }

    private val byteInputStream: InputStream = content.byteInputStream(Charset.defaultCharset())

    private val buffer: ByteArray = ByteArray(bufferSize)
    private var bytesInBuffer = 0
    private var bytesBufferPosition = 0
    private var hasMoreData = true

    override fun hasNext(): Boolean {
        if(!hasMoreData) {
            return false
        }
        fillBufferIfNecessary()
        return hasMoreData
    }
    override fun nextByte(): Byte {
        fillBufferIfNecessary()
        if(!hasMoreData) {
            throw IllegalStateException("Byte stream has no additional data.")
        }
        return buffer[bytesBufferPosition++]
    }

    private fun fillBufferIfNecessary() {
        if(bytesInBuffer <= bytesBufferPosition) {
            readSomeBytes()
        }
    }

    private fun readSomeBytes() {
        val result = byteInputStream.read(buffer)
        if(result < 1) {
            hasMoreData = false
        } else {
            bytesInBuffer = result
            bytesBufferPosition = 0
        }
    }
}
