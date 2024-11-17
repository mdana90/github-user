package com.dana.githubuser.common

import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale

private const val PRECISION_DIGIT = 1

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