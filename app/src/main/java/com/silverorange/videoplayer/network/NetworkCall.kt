package com.silverorange.videoplayer.network

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.livinglifetechway.k4kotlin_retrofit.enqueueDeferredResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call

fun <R, F> CoroutineScope.simpleNetworkCall(
    data: R? = null,
    func: suspend () -> Result<R, F>
): LiveData<Resource<R, F>> {
    val liveData = MutableLiveData<Resource<R, F>>()
    liveData.value = Resource.empty()
    launch {
        withContext(Dispatchers.Main) {
            liveData.value = Resource.loading(data)
            val result = func.invoke()
            liveData.value = when (result) {
                is Success -> {
                    Resource.success(result.data)
                }
                is Failure -> Resource.error(throwable = result.throwable, errorData = result.error)
            }
        }
    }
    return liveData
}

suspend fun <T : Any, S : T> Call<S>.getResult(): Result<T, APIError> {
    return try {
        val response = this.enqueueDeferredResponse().await()
        when {
            response.code() == 201 -> Failure(null, APIError("201", "Something went wrong!"))
            response.isSuccessful -> //Send success body
                Success(response.body())
            else -> //parse error from API
                Failure(null, APIError("0", "Something went wrong!"))
        }
    } catch (throwable: Throwable) {
        Log.e(TAG, "getResult: ", throwable)
        Failure(
            throwable,
            APIError(
                "0",
                "There was a problem connecting to the server. Please try again after sometime."
            )
        )
    }
}
