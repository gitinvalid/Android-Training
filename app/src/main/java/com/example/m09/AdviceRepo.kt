package com.example.m09

import android.os.Handler
import android.os.Looper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdviceRepo(private val apiService: ApiService) {
    private lateinit var callback: ((String) -> Unit)

    private val handle = Handler(Looper.getMainLooper()) {
        callback.invoke(it.obj as String)
        true
    }

    fun load(callback: (String) -> Unit) {
        this.callback = callback
        val message = handle.obtainMessage()

        apiService.getAdvice().enqueue(object : Callback<Advice> {
            override fun onResponse(call: Call<Advice>, response: Response<Advice>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        message.obj = it.slip.advice
                        message.sendToTarget()
                    }
                }
            }
            override fun onFailure(call: Call<Advice>, t: Throwable) {
                message.obj = "FETCH DATA ERROR"
                message.sendToTarget()
            }
        })
    }
}