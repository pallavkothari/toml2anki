package com.pk.toml2anki

import com.google.common.io.Resources
import com.google.common.truth.Truth.assertThat
import net.consensys.cava.toml.Toml
import org.junit.Test

class TomlTest {

    @Test
    fun testMultilines() {
        val resource = Resources.getResource("test.toml")
        val test = Resources.toString(resource, Charsets.UTF_8)
        val result = Toml.parse(test)
        assertThat(result.hasErrors()).isFalse()
        val value = result.getString("multi.line.key")
        println("value = ${value}")
        assertThat(value).contains("\n")
        val expected = """
            this is
            a multi line
            scalar
        """.trimIndent()
        assertThat(value).isEqualTo(expected)
    }
}
