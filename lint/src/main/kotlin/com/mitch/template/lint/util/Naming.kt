package com.mitch.template.lint.util

val PascalCaseRegex = Regex("[A-Z]([a-z0-9]+(?:[A-Z][a-z0-9]+)*)?")
fun String.toPascalCase(): String {
    return this
        .splitWords()
        .joinToString("") { it.capitalize() }
}

val CamelCaseRegex = Regex("[a-z]+([0-9])?(?:[A-Z][a-z0-9]+)*")
fun String.toCamelCase(): String {
    return this
        .splitWords()
        .mapIndexed { index, word ->
            if (index == 0) word.lowercase() else word.capitalize()
        }
        .joinToString("")
}

private fun String.splitWords(): List<String> {
    val regex = "(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)|(?<=\\w)(?=_+)|(?<=_)(?=\\w)|(?<=[a-z])(?=[A-Z])"
    return this.split(Regex(regex)).filterNot { it == "_" }
}

private fun String.capitalize(): String {
    return this.lowercase().replaceFirstChar { it.uppercase() }
}
