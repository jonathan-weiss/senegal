package ch.senegal.gradle

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test

internal class GradlePropertyHelperTest {

    @Test
    fun replacePlaceholders() {
        val replacements = mapOf(
                Pair("placeholderOne", "replacementOne"),
                Pair("placeholderTwo", "replacementTwo"),
        )
        val expectedResult = "test with \${placeholderOne} and " +
                "with \${placeholderTwo} and \${placeholderOne} and \${placeholderThree}"
        val result = GradlePropertyHelper.replacePlaceholders("test with replacementOne and " +
                "with replacementTwo and replacementOne and \${placeholderThree}", replacements)

        MatcherAssert.assertThat(result, Matchers.equalTo(expectedResult))
    }
}
