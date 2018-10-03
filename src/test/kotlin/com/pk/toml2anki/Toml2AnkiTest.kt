package com.pk.toml2anki

import com.google.common.io.Files
import com.google.common.io.Resources
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class Toml2AnkiTest {

    @Test
    fun parse() {
        val resource = Resources.getResource("test.toml")
        val toml = Resources.toString(resource, Charsets.UTF_8)
        val toml2Anki = Toml2Anki(toml, java.nio.file.Files.createTempFile("anki", "txt").toFile())
        val anki = toml2Anki.parse()
        println("anki = \n${anki}")
        val expected = """
            "front of ; the card";"multiline ;
            back of the card; ""quotes""
            "
            second front;second back

        """.trimIndent()

        assertThat(anki).isEqualTo(expected)
    }

    @Test
    fun anki() {
        val toml  = """
            [[card]]
            front = "first front"
            back = "first back"
            [[card]]
            front = "second front"
            back = "second back"
        """.trimIndent()

        val dest = java.nio.file.Files.createTempFile("anki", ".txt").toFile()
        val toml2Anki = Toml2Anki(toml, dest)
        val anki = toml2Anki.parse()
        println("anki:\n${anki}")
        toml2Anki.writeFile(anki)
        val contents = Files.asCharSource(dest, Charsets.UTF_8).read()
        val expected = """
            first front;first back
            second front;second back

        """.trimIndent()
        assertThat(contents).isEqualTo(expected)
    }

    @Test
    fun escapeStrings() {
        val quote = """""""
        val quotes = """
            brits like to say ${quote}inverted commas"
        """.trimIndent()
        println(quotes)
        val quoted = escape(quotes, quote)
        println(quoted)
    }

    private fun escape(quotes: String, quote: String): String {
        val escaped = quotes.replace("\"", "\"\"")
        val quoted = """$quote${escaped}$quote"""
        return quoted
    }
}