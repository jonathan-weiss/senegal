package ch.senegal.engine.template

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class KotlinDelegateTest {

    @Test
    fun testDelegationOfPropertyName() {
        val mapForDelegateExample = mutableMapOf<String, Any?>()
        val delegateExample = DelegateExample(mapForDelegateExample)

        assertEquals("DELEGATE KEY: hallo", delegateExample["hallo"])

        delegateExample.name = "This is foo"

        assertEquals("DELEGATE KEY: foo", delegateExample["foo"])
        assertEquals("This is foo", mapForDelegateExample["name"])
    }

    class DelegateExample(map: MutableMap<String, Any?>) {

        operator fun get(key: String): Any {
            return "DELEGATE KEY: $key"
        }
        var name: String by map
    }
}
