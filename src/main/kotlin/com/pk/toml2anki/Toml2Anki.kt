package com.pk.toml2anki

import com.google.common.io.Files
import net.consensys.cava.toml.Toml
import java.io.File
import java.io.StringWriter

data class Toml2Anki(val toml: String, val dest: File) {

    fun parse() : String {
        val result = Toml.parse(toml)
        if (result.hasErrors()) throw IllegalArgumentException(result.errors().toString())
        val anki = StringWriter()
        for (key in result.dottedKeySet()) {
            val value = result.get(mutableListOf(key))

            anki.write("$key ; $value\n")
        }
        return anki.toString()
    }

    fun writeFile(contents: String) {
        Files.asCharSink(dest, Charsets.UTF_8).write(contents)
    }
}
