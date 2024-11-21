package com.dana.githubuser.network

import com.dana.githubuser.model.Result
import com.dana.githubuser.network.util.apiCall
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import retrofit2.HttpException
import java.io.IOException

class ApiUtilTest {

    @Test
    fun `apiCall with success result should return success`() = runTest {
        val mockApiCall = mockk<suspend () -> Result<String>>()
        val expectedResult = Result.Success("Success")
        coEvery { mockApiCall() } returns expectedResult

        val result = apiCall(mockApiCall)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `apiCall with IOException should return error`() = runTest {
        val mockApiCall = mockk<suspend () -> Result<String>>()
        val exception = IOException("Network error")
        coEvery { mockApiCall() } throws exception

        val result = apiCall(mockApiCall)

        assertEquals(exception, (result as Result.Error).exception)
    }

    @Test
    fun `apiCall with HttpException should return error`() = runTest {
        val mockApiCall = mockk<suspend () -> Result<String>>()
        val exception = mockk<HttpException>(relaxed = true)
        coEvery { mockApiCall() } throws exception

        val result = apiCall(mockApiCall)

        assertEquals(exception, (result as Result.Error).exception)
    }

    @Test
    fun `apiCall with an generic exception should return error`() = runTest {
        val mockApiCall = mockk<suspend () -> Result<String>>()
        val exception = RuntimeException("Unexpected error")
        coEvery { mockApiCall() } throws exception

        val result = apiCall(mockApiCall)

        assertEquals(exception, (result as Result.Error).exception)
    }
}
