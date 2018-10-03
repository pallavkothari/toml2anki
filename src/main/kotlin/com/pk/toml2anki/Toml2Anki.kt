package com.pk.toml2anki

import com.google.common.io.Files
import net.consensys.cava.toml.Toml
import net.consensys.cava.toml.TomlArray
import net.consensys.cava.toml.TomlTable
import java.io.File
import java.io.StringWriter

const val SINGLE_QUOTE = """""""

data class Toml2Anki(val toml: String, val dest: File) {

    /**
     * parses the given toml into anki format
     */
    fun parse() : String {
        val result = Toml.parse(toml)
        if (result.hasErrors()) throw IllegalArgumentException(result.errors().toString())

        val deck = mutableListOf<AnkiCard>()
        for (key in result.dottedKeySet()) {
            val value = result.get(key)
            when (value) {
                is TomlArray -> {
                    println("reading array")
                    val size = value.size()
                    for (i in 0 until size) {
                        val element = value.get(i)
                        when (element) {
                            is TomlTable -> {
                                val front = escaped(element.getString("front").orEmpty())
                                val back = escaped(element.getString("back").orEmpty())
                                deck.add(AnkiCard(front, back))
                            }
                            else -> throw Error()
                        }
                    }
                }
                else -> throw Error()
            }
        }

        val writer = StringWriter()
        deck.forEach { ankiCard ->
            println(ankiCard)
            writer.write("${ankiCard.front};${ankiCard.back}\n")
        }

        return writer.toString()
    }

    private fun escaped(str: String): String {
        val escaped = str.replace("\"", "\"\"")
        return if (escaped.contains("\n") || escaped.contains(";")) """$SINGLE_QUOTE$escaped$SINGLE_QUOTE""" else escaped
    }

    /**
     * writes to the dest file
     */
    fun writeFile(contents: String) {
        Files.asCharSink(dest, Charsets.UTF_8).write(contents)
    }
}

/**
 * a card has a front and a back
 */
data class AnkiCard(val front: String, val back: String)