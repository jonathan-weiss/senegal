package ch.cassiamon.pluginapi.rules

import org.junit.jupiter.api.Assertions.*

internal class NameEnforcerTest {

    @org.junit.jupiter.api.Test
    fun isValidName() {

        assertThat(ch.cassiamon.pluginapi.rules.NameEnforcerTest.Validity.NAME_IS_VALID, "ConceptName")
        assertThat(ch.cassiamon.pluginapi.rules.NameEnforcerTest.Validity.NAME_IS_VALID, "Concept123Name")
        assertThat(ch.cassiamon.pluginapi.rules.NameEnforcerTest.Validity.NAME_IS_VALID, "CONCEPT123NAME")
        assertThat(ch.cassiamon.pluginapi.rules.NameEnforcerTest.Validity.NAME_IS_VALID, "C1")
        assertThat(ch.cassiamon.pluginapi.rules.NameEnforcerTest.Validity.NAME_IS_VALID, "Ca")
        assertThat(ch.cassiamon.pluginapi.rules.NameEnforcerTest.Validity.NAME_IS_VALID, "CA")

        assertThat(ch.cassiamon.pluginapi.rules.NameEnforcerTest.Validity.NAME_IS_NOT_VALID, "")
        assertThat(ch.cassiamon.pluginapi.rules.NameEnforcerTest.Validity.NAME_IS_NOT_VALID, "C")
        assertThat(ch.cassiamon.pluginapi.rules.NameEnforcerTest.Validity.NAME_IS_NOT_VALID, "concept123Name")
        assertThat(ch.cassiamon.pluginapi.rules.NameEnforcerTest.Validity.NAME_IS_NOT_VALID, " Concept123Name")
        assertThat(ch.cassiamon.pluginapi.rules.NameEnforcerTest.Validity.NAME_IS_NOT_VALID, "cONCEPT123Name")
        assertThat(ch.cassiamon.pluginapi.rules.NameEnforcerTest.Validity.NAME_IS_NOT_VALID, "_ConceptName")
        assertThat(ch.cassiamon.pluginapi.rules.NameEnforcerTest.Validity.NAME_IS_NOT_VALID, "Concept_Name")
        assertThat(ch.cassiamon.pluginapi.rules.NameEnforcerTest.Validity.NAME_IS_NOT_VALID, "Concept Name")
        assertThat(ch.cassiamon.pluginapi.rules.NameEnforcerTest.Validity.NAME_IS_NOT_VALID, "Concept-Name")
        assertThat(ch.cassiamon.pluginapi.rules.NameEnforcerTest.Validity.NAME_IS_NOT_VALID, "-ConceptName")
    }

    private fun assertThat(validity: ch.cassiamon.pluginapi.rules.NameEnforcerTest.Validity, name: String) {
        when(validity) {
            ch.cassiamon.pluginapi.rules.NameEnforcerTest.Validity.NAME_IS_VALID -> assertTrue(
                ch.cassiamon.pluginapi.rules.NameEnforcer.isValidName(
                    name
                ), "Name '$name' was assert to be valid, but was not.")
            ch.cassiamon.pluginapi.rules.NameEnforcerTest.Validity.NAME_IS_NOT_VALID -> assertFalse(
                ch.cassiamon.pluginapi.rules.NameEnforcer.isValidName(
                    name
                ), "Name '$name' was assert to be invalid, but was valid.")
        }
    }

    private enum class Validity {
        NAME_IS_VALID, NAME_IS_NOT_VALID
    }
}
