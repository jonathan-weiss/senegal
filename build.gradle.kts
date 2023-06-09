plugins {
    kotlin("jvm") version "1.6.21" apply(false)
}

val fileProjectVersion = readVersionFile()

allprojects {
    group = "ch.senegal"
    version = fileProjectVersion
}

fun readVersionFile(): String {
    // read first line to get rid of line breaks
    return rootDir.resolve("VERSION").readLines()[0].trim()
}
