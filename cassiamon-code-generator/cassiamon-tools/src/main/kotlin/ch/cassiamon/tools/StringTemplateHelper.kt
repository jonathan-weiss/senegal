package ch.cassiamon.tools

object StringTemplateHelper {
    fun <T> forEach(iterator: Iterable<T>, out: (v:T)->String): String {
        val sb = StringBuilder()
        iterator.forEach {
            sb.append(out(it))
        }
        return sb.toString()
    }

    fun onlyIf(condition: Boolean, text: String): String {
        return ifElse(condition, text, "")
    }

    fun ifElse(condition: Boolean, text: String, elseText: String): String {
        return if(condition) text else elseText
    }

    fun <T> forEachIndexed(iterator: Iterable<T>, out: (index: Int, v:T)->String): String {
        val sb = StringBuilder()
        iterator.forEachIndexed { index, it ->
            sb.append(out(index, it))
        }
        return sb.toString()
    }

    fun <T> join(iterator: Iterable<T>, separator: CharSequence, out: (v:T)->String): String {
        return iterator.joinToString(separator) {
            out(it)
        }
    }


}
