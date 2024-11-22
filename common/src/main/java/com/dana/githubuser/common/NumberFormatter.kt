package com.dana.githubuser.common

import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale

private const val PRECISION_DIGIT = 1

/**
 * Formats a given number to a string representation in thousands (K) or millions (M).
 *
 * @param number The number to format.
 * @return A string representation of the number in K or M format.
 */
fun formatToKorM(number: Long): String {
    val numberFormat = NumberFormat.getInstance(Locale.getDefault())
    numberFormat.maximumFractionDigits = PRECISION_DIGIT

    return when {
        number >= 1_000_000L -> formatToM(numberFormat, number)
        number >= 999_000L -> formatToK(numberFormat, number, RoundingMode.FLOOR)
        number >= 1_000 -> formatToK(numberFormat, number, RoundingMode.HALF_UP)
        else -> number.toString()
    }
}

/**
 * Formats a given number to a string representation in thousands (K).
 *
 * @param numberFormat The NumberFormat instance to use for formatting.
 * @param number The number to format.
 * @param roundingMode The rounding mode to apply.
 * @return A string representation of the number in K format.
 */
private fun formatToK(numberFormat: NumberFormat, number: Long, roundingMode: RoundingMode): String {
    val result = numberFormat.format(
        round(
            (number.toDouble() / 1_000),
            if (number < 10_000) PRECISION_DIGIT else 0,
            roundingMode
        )
    )
    return "${result}K"
}

/**
 * Formats a given number to a string representation in millions (M).
 *
 * @param numberFormat The NumberFormat instance to use for formatting.
 * @param number The number to format.
 * @return A string representation of the number in M format.
 */
private fun formatToM(numberFormat: NumberFormat, number: Long): String {
    val result = numberFormat.format(
        round(
            (number.toDouble() / 1_000_000L),
            PRECISION_DIGIT,
            RoundingMode.FLOOR
        )
    )
    return "${result}M"
}

private fun round(value: Double, places: Int, round: RoundingMode): Double {
    require(places >= 0)
    return value.toBigDecimal().setScale(places, round).toDouble()
}