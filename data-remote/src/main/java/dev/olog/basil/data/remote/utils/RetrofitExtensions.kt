package dev.olog.basil.data.remote.utils

import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import retrofit2.Response


internal suspend fun <T> networkCall(
    delayBetweenCalls: Long = 1000L,
    repeatTimes: Int = 3,
    call: suspend () -> Response<T>
): T? {

    var errorCode: Int? = null
    var errorMessage: String? = null

    repeat(repeatTimes) { iteration ->
        delay(delayBetweenCalls * iteration)
        val result = call()
        if (result.isSuccessful) {
            return result.body()
        }
        errorCode = result.code()
        errorMessage = result.message()
        yield()
    }

    try {
        throw RuntimeException("status code=$errorCode, message=$errorMessage")
    } catch (ex: Throwable) {
        ex.printStackTrace()
    }

    return null
}