package ch.senegal.engine.freemarker

import org.junit.jupiter.api.Test

internal class KotlinDelegateTest {

    @Test
    fun testDelegationOfPropertyName() {
        val myMap = mutableMapOf<String, Any?>()
        println("myMap: $myMap")
        val delegateExample = DelegateExample(myMap)

        val halloResult = delegateExample["hallo"]
        println("get delegate halloResult: $halloResult")
        delegateExample.name = "foo"
        println("get delegate name before writing: ${delegateExample.name}")
        println("myMap: $myMap")


    }

    class DelegateExample(map: MutableMap<String, Any?>) {

        operator fun get(key: String): Any? {
            return "YOUR key: $key"
        }
        var name: String by map
    }
}
