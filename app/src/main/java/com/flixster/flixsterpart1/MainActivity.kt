package com.flixster.flixsterpart1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "MainActivity/"
private const val MOVIE_DB_API_KEY = BuildConfig.API_KEY
private const val MOVIE_DB_URL =
    "https://api.themoviedb.org/3/movie/now_playing?api_key=${MOVIE_DB_API_KEY}&language=en-US"

class MainActivity : AppCompatActivity() {

    private val movies  = mutableListOf<Movies.Result>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moviesRecyclerView = findViewById<RecyclerView>(R.id.rv_movies)
        val moviesAdapter = MoviesAdapter(this, movies)

        moviesRecyclerView.adapter = moviesAdapter
        moviesRecyclerView.layoutManager = LinearLayoutManager(this)

        val client = AsyncHttpClient()
        client.get(MOVIE_DB_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch movies: $statusCode $response")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched movies: $json")
                try {
                    val response = Gson().fromJson(json.jsonObject.toString(), Movies::class.java)
                    response.results?.let { list ->
                        movies.addAll(list)
                        moviesAdapter.notifyDataSetChanged()
                    }
                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }
        })
    }
}