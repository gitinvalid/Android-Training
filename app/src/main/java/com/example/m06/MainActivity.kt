package com.example.m06

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.m06.module.PopularMovies
import com.example.m06.service.ApiService
import com.example.m06.service.createApiService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private val apiService = createApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        fetchDataAndFill()
    }

    fun fetchDataAndFill() {
        Toast.makeText(this@MainActivity, "start fetch data", Toast.LENGTH_LONG).show()

        apiService.getMovie(getString(R.string.api_key)).enqueue(object : Callback<PopularMovies> {
            override fun onResponse(call: Call<PopularMovies>, response: Response<PopularMovies>) {
                if (!response.isSuccessful) {
                    fetchDataAndFill()
                    return
                }
                recyclerView.adapter = response.body()?.let { RecyclerAdapter(it) }
            }

            override fun onFailure(call: Call<PopularMovies>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "fetch data error, ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
