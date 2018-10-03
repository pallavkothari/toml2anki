package com.pk.toml2anki

import com.google.common.base.Preconditions
import com.google.common.io.Files
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.DefaultHelpFormatter
import com.xenomachina.argparser.default
import com.xenomachina.argparser.mainBody
import java.io.File

class MyArgs(parser: ArgParser) {
    val source by parser.positional("source filename") { File(this) }
    val destination by parser.positional("destination filename") { File(this) }.default { File( source.parentFile,"${source.nameWithoutExtension}.txt") }
}

fun main(args : Array<String>) = mainBody {
    ArgParser(
        args,
        helpFormatter = DefaultHelpFormatter(
            epilogue = "Sample usage: toml2anki my-anki-deck.toml"
        )
    ).parseInto(::MyArgs).run {
        Preconditions.checkArgument(source.extension == "toml", "please use a toml file as src")
        println("reading ${source.absolutePath}")

        val toml = Files.asCharSource(source, Charsets.UTF_8).read()
        val toml2Anki = Toml2Anki(toml, destination)
        val anki = toml2Anki.parse()
        toml2Anki.writeFile(anki)

        println("wrote anki file to ${destination.absolutePath}")
    }
}
