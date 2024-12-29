package org.codeblessing.senegal.codegen.toolbox.template

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class TemplateTest {

    @Test
    fun `test include#startWithBlankLineIfNotEmpty adds a separate blank line before the first line if true`() {
        val innerTemplateToInclude = template {
            +"first inner line"
            +"last inner line"
        }
        val outerTemplate = template {
            +"first outer line"
            include(innerTemplateToInclude, startWithBlankLineIfNotEmpty = true)
            +"last outer line"
        }.build()

        val expectedString = """
            first outer line

            first inner line
            last inner line
            last outer line
        """.trimIndent()

        Assertions.assertEquals(expectedString, outerTemplate)
    }

    @Test
    fun `test include#startWithBlankLineIfNotEmpty does not add separate blank line before the first line if false`() {
        val innerTemplateToInclude = template {
            +"first inner line"
            +"last inner line"
        }
        val outerTemplate = template {
            +"first outer line"
            include(innerTemplateToInclude, startWithBlankLineIfNotEmpty = false)
            +"last outer line"
        }.build()

        val expectedString = """
            first outer line
            first inner line
            last inner line
            last outer line
        """.trimIndent()

        Assertions.assertEquals(expectedString, outerTemplate)
    }

    @Test
    fun `test include#startWithBlankLineIfNotEmpty adds no separate blank line if included template is empty`() {
        val innerTemplateToInclude = template {
            // empty template
        }
        val outerTemplate = template {
            +"first outer line"
            include(innerTemplateToInclude, startWithBlankLineIfNotEmpty = true)
            +"last outer line"
        }.build()

        val expectedString = """
            first outer line
            last outer line
        """.trimIndent()

        Assertions.assertEquals(expectedString, outerTemplate)
    }

    @Test
    fun `test lines inserted by include are indented with same space as outer template`() {
        val innerTemplateToInclude = template {
            +"first inner line"
            +"last inner line"
        }
        val outerTemplate = template {
            +"first outer line"
            indented {
                +"first indented outer line"
                include(innerTemplateToInclude)
                +"last indented outer line"
            }
            +"last outer line"
        }.build()

        val expectedString = """
            first outer line
                first indented outer line
                first inner line
                last inner line
                last indented outer line
            last outer line
        """.trimIndent()

        Assertions.assertEquals(expectedString, outerTemplate)
    }

    @Test
    fun `test forEach#separateWithBlankLineIfNotEmpty adds a separate blank line after each item if true`() {
        val expectedString = """
            first line
            - foo

            - bar

            - baz
            last line
        """.trimIndent()

        Assertions.assertEquals(
            expectedString,
            templateWithForEachAndItems(
                startWithBlankLineIfNotEmpty = false,
                separateWithBlankLineIfNotEmpty = true,
                endWithBlankLineIfNotEmpty = false,
            ),
        )
    }

    @Test
    fun `test forEach#separateWithBlankLineIfNotEmpty does not add a separate blank line after each item if false`() {
        val expectedString = """
            first line
            - foo
            - bar
            - baz
            last line
        """.trimIndent()

        Assertions.assertEquals(
            expectedString,
            templateWithForEachAndItems(
                startWithBlankLineIfNotEmpty = false,
                separateWithBlankLineIfNotEmpty = false,
                endWithBlankLineIfNotEmpty = false,
            ),
        )
    }

    @Test
    fun `test forEach#startWithBlankLineIfNotEmpty adds a separate blank line before the first item if true`() {
        val expectedString = """
            first line

            - foo
            - bar
            - baz
            last line
        """.trimIndent()

        Assertions.assertEquals(
            expectedString,
            templateWithForEachAndItems(
                startWithBlankLineIfNotEmpty = true,
                separateWithBlankLineIfNotEmpty = false,
                endWithBlankLineIfNotEmpty = false,
            ),
        )
    }

    @Test
    fun `test forEach#endWithBlankLineIfNotEmpty adds a separate blank line after the last item if true`() {
        val expectedString = """
            first line
            - foo
            - bar
            - baz

            last line
        """.trimIndent()

        Assertions.assertEquals(
            expectedString,
            templateWithForEachAndItems(
                startWithBlankLineIfNotEmpty = false,
                separateWithBlankLineIfNotEmpty = false,
                endWithBlankLineIfNotEmpty = true,
            ),
        )
    }

    @Test
    fun `test foreach does not added lines at all if list of item is empty, even if all blank line options are set to true`() {
        val expectedString = """
            first line
            last line
        """.trimIndent()
        val items: List<String> = emptyList()

        Assertions.assertEquals(
            expectedString,
            templateWithForEachAndItems(
                startWithBlankLineIfNotEmpty = true,
                separateWithBlankLineIfNotEmpty = true,
                endWithBlankLineIfNotEmpty = true,
                items = items,
            ),
        )
    }

    @Test
    fun `test output of complex template works as expected`() {
        val things = listOf("foo", "bar")
        val arguments = listOf("first", "second")

        val innerTemplate: Template = template {
            +"// this is a comment"
        }

        val outerTemplate = template {
            +"package foo.bar"
            +""
            +"class Foo {"
            indented {
                +"first line"
                include(innerTemplate)
                +"second line"
                +""
                forEach(things, separateWithBlankLineIfNotEmpty = true) {
                    +"fun $it(): Int {"
                    indented {
                        +"val $it = 1"
                        +"return $it + 100"
                    }
                    +"}"
                    +""
                    +"fun ${it}WithArgs("
                    indented {
                        forEach(arguments) {
                            +"$it,"
                        }
                    }
                    +"): Int {"
                    indented {
                        +"return 0"
                    }
                    +"}"
                }
            }
            +"}"
        }

        val expectedString = """
            package foo.bar

            class Foo {
                first line
                // this is a comment
                second line

                fun foo(): Int {
                    val foo = 1
                    return foo + 100
                }

                fun fooWithArgs(
                    first,
                    second,
                ): Int {
                    return 0
                }

                fun bar(): Int {
                    val bar = 1
                    return bar + 100
                }

                fun barWithArgs(
                    first,
                    second,
                ): Int {
                    return 0
                }
            }
        """.trimIndent()

        Assertions.assertEquals(expectedString, outerTemplate.build())
    }

    private fun templateWithForEachAndItems(
        startWithBlankLineIfNotEmpty: Boolean,
        separateWithBlankLineIfNotEmpty: Boolean,
        endWithBlankLineIfNotEmpty: Boolean,
        items: List<String> = listOf("foo", "bar", "baz"),
    ): String {
        return template {
            +"first line"
            forEach(
                items,
                startWithBlankLineIfNotEmpty = startWithBlankLineIfNotEmpty,
                separateWithBlankLineIfNotEmpty = separateWithBlankLineIfNotEmpty,
                endWithBlankLineIfNotEmpty = endWithBlankLineIfNotEmpty,
            ) {
                +"- $it"
            }
            +"last line"
        }.build()
    }
}