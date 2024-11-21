package com.dana.githubuser.common

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class NumberFormatterTest {
    @Test
    fun `test formatToKorM `() {
        assertEquals("999", formatToKorM(999))
        assertEquals("1K", formatToKorM(1_000))
        assertEquals("1.5K", formatToKorM(1_500))
        assertEquals("7.8K", formatToKorM(7_820))
        assertEquals("7.9K", formatToKorM(7_890))
        assertEquals("8K", formatToKorM(7_990))
        assertEquals("15K", formatToKorM(15_000))
        assertEquals("15K", formatToKorM(15_200))
        assertEquals("16K", formatToKorM(15_900))
        assertEquals("123K", formatToKorM(123_456))
        assertEquals("124K", formatToKorM(123_789))
        assertEquals("999K", formatToKorM(999_800))
        assertEquals("1M", formatToKorM(1_000_000))
        assertEquals("1.5M", formatToKorM(1_500_000))
        assertEquals("1.9M", formatToKorM(1_999_000))
        assertEquals("0", formatToKorM(0))
    }
}