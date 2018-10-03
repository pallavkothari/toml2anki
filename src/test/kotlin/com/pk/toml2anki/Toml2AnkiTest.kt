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
        TODO("this is broken")
    }

    @Test
    fun writeFile() {
        val toml = """
            "first key" = "first val"
        """.trimIndent()
        val dest = java.nio.file.Files.createTempFile("anki", ".txt").toFile()
        val toml2Anki = Toml2Anki(toml, dest)
        val anki = toml2Anki.parse()
        toml2Anki.writeFile(anki)
        println("dest = ${dest}")
        val contents = Files.asCharSource(dest, Charsets.UTF_8).read()
        val expected = "first key ; first val\n"
        assertThat(contents).isEqualTo(expected)
    }
}