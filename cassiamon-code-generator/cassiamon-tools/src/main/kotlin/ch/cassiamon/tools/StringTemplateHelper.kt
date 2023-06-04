package ch.cassiamon.tools

object StringTemplateHelper {
    fun <T> forEach(iterator: Iterable<T>, out: (v:T)->String): String {
        val sb = StringBuilder()
        iterator.forEach {
            sb.append(out(it))
        }
        return sb.toString()
    }

    fun <T> forEachIndexed(iterator: Iterable<T>, out: (index: Int, v:T)->String): String {
        val sb = StringBuilder()
        iterator.forEachIndexed { index, it ->
            sb.append(out(index, it))
        }
        return sb.toString()
    }

}