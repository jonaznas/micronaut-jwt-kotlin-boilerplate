package dev.jonaz.server.util.tools

import java.security.SecureRandom

object GenerateString {
    private const val letters = "abcdefghijklmnopqrstuvwxyz"
    private const val uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private const val numbers = "0123456789"
    private const val special = "@#=+!£$%&?-.,'*#°^"

    fun random(isWithLetters: Boolean,
               isWithUppercase: Boolean,
               isWithNumbers: Boolean,
               isWithSpecial: Boolean,
               length: Int): String {

        var result = ""
        var i = 0

        if (isWithLetters) result += this.letters
        if (isWithUppercase) result += this.uppercaseLetters
        if (isWithNumbers) result += this.numbers
        if (isWithSpecial) result += this.special

        val rnd = SecureRandom.getInstance("SHA1PRNG")
        val sb = StringBuilder(length)

        while (i < length) {
            val randomInt: Int = rnd.nextInt(result.length)
            sb.append(result[randomInt])
            i++
        }

        return sb.toString()
    }
}
