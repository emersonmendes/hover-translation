package br.com.emersonmendes.hovertranslation

internal fun String.splitToWords() = this
    .split(" ")
    .map { it.replace(Regex("[^a-zA-Z0-9:$\\s]"), "") }
    .flatMap { it.split(Regex("(?<=\\p{Lower})(?=\\p{Upper}|\\W)|(?<=\\W)(?=\\p{Upper}|\\p{Lower})")) }
    .map { it.lowercase() }