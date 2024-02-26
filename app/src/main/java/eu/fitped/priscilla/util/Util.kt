package eu.fitped.priscilla.util

import eu.fitped.priscilla.EMAIL_REGEX

fun String.isValidEmail(): Boolean {
    return this.matches(EMAIL_REGEX.toRegex())
}