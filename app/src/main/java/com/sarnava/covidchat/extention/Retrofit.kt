package com.sarnava.covidchat.extention

import androidx.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> Call<T>.enqueue(success: (response: Response<T>) -> Unit,
                        error: (errorResponse: Response<T>) -> Unit,
                        failure: (t: Throwable) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>?, response: Response<T>) {
            if (response.isSuccessful) {
                success(response)
            } else {
                error(response)
            }
        }

        override fun onFailure(call: Call<T>?, t: Throwable) = failure(t)
    })
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

/**
 * Method for creating request body with object.
 *
 * @return [RequestBody] made for object.
 */
fun <T> T.createRequestBody(): RequestBody {
    return GsonBuilder().create()
        .toJson(this)
        .toRequestBody("application/json".toMediaType())
}
