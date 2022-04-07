package com.aapolis.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.aapolis.movieapp.adapters.MovieAdapter
import com.aapolis.movieapp.api.ApiService
import com.aapolis.movieapp.data.Loader
import com.aapolis.movieapp.data.response.SearchMovieResponse
import com.aapolis.movieapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val items = ArrayList<Any>()
    lateinit var adapter:MovieAdapter
    lateinit var binding: ActivityMainBinding

    lateinit var etQuery: TextView

    var currentPage = 1
    var pageCount = 0
    var isLoading = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        etQuery = findViewById(R.id.et_query)

        binding.rvMovies.layoutManager = LinearLayoutManager(baseContext)
        adapter = MovieAdapter(items)
        binding.rvMovies.adapter = adapter

        binding.btnSearch.setOnClickListener {
            searchMovies()
        }

        val a = object: View.OnScrollChangeListener {
            override fun onScrollChange(view: View?, p1: Int, p2: Int, p3: Int, p4: Int) {
                TODO("Not yet implemented")
            }

        }
        binding.rvMovies.setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->
            val layoutManager = binding.rvMovies.layoutManager as LinearLayoutManager
            val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
            Log.d("LastPosition", "onCreate: $lastVisiblePosition data size = ${items.size}")
            if(lastVisiblePosition == items.size-1) {
                loadNextPage()
            }
        }
    }

    private fun searchMovies() {

        if(isLoading) {
            return
        }

        items.clear()
        adapter.notifyDataSetChanged()
        val query = binding.etQuery.text.toString()

        val apiService = ApiService.getInstance()
        currentPage = 1
        val call: Call<SearchMovieResponse> = apiService.search(query, currentPage)

        adapter.addLoader(Loader("Searching movies. Please wait."))
        isLoading = true
        call.enqueue(object : Callback<SearchMovieResponse> {
            override fun onResponse(
                call: Call<SearchMovieResponse>,
                response: Response<SearchMovieResponse>
            ) {
                adapter.removeLoader()
                isLoading = false
                if(!response.isSuccessful) {
                    Toast.makeText(baseContext, "Error code: ${response.code()}", Toast.LENGTH_LONG).show()
                    return
                }
                val searchResult = response.body()

                if(searchResult == null) {
                    Toast.makeText(baseContext, "Empty data received.", Toast.LENGTH_LONG).show()
                    return
                }
                adapter.addItems(searchResult.results)
                currentPage = 1
                pageCount = searchResult.total_pages
            }

            override fun onFailure(call: Call<SearchMovieResponse>, t: Throwable) {
                isLoading = false
                adapter.removeLoader()
                t.printStackTrace()
                Toast.makeText(baseContext, "Error is : $t", Toast.LENGTH_LONG).show()
            }
        })
    }


    fun loadNextPage() {
        if(currentPage == pageCount) {
            Toast.makeText(baseContext, "No more data available.", Toast.LENGTH_LONG).show()
            return
        }
        if(isLoading) {
            return
        }
        val query = binding.etQuery.text.toString()

        val apiService = ApiService.getInstance()
        currentPage++
        val call: Call<SearchMovieResponse> = apiService.search(query, currentPage)

        adapter.addLoader(Loader("Loading more search results. Please wait."))
        isLoading = true
        call.enqueue(object : Callback<SearchMovieResponse> {
            override fun onResponse(
                call: Call<SearchMovieResponse>,
                response: Response<SearchMovieResponse>
            ) {
                isLoading = false
                adapter.removeLoader()
                if(!response.isSuccessful) {
                    Toast.makeText(baseContext, "Error code: ${response.code()}", Toast.LENGTH_LONG).show()
                    return
                }
                val searchResult = response.body()

                if(searchResult == null) {
                    Toast.makeText(baseContext, "Empty data received.", Toast.LENGTH_LONG).show()
                    return
                }
                adapter.addItems(searchResult.results)

            }

            override fun onFailure(call: Call<SearchMovieResponse>, t: Throwable) {
                isLoading = false
                adapter.removeLoader()
                t.printStackTrace()
                Toast.makeText(baseContext, "Error is : $t", Toast.LENGTH_LONG).show()
            }
        })
    }


    private fun search() {

        if(isLoading) {
            return
        }

        items.clear()
        adapter.notifyDataSetChanged()
        val query = binding.etQuery.text.toString()

        val apiService = ApiService.getInstance()
        currentPage = 1
        val call: Call<SearchMovieResponse> = apiService.search(query, currentPage)

        adapter.addLoader(Loader("Searching movies. Please wait."))
        isLoading = true

        try {
            val response: Response<SearchMovieResponse> = apiService.searchMovie(query, currentPage)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        /*call.enqueue(object : Callback<SearchMovieResponse> {
            override fun onResponse(
                call: Call<SearchMovieResponse>,
                response: Response<SearchMovieResponse>
            ) {
                adapter.removeLoader()
                isLoading = false
                if(!response.isSuccessful) {
                    Toast.makeText(baseContext, "Error code: ${response.code()}", Toast.LENGTH_LONG).show()
                    return
                }
                val searchResult = response.body()

                if(searchResult == null) {
                    Toast.makeText(baseContext, "Empty data received.", Toast.LENGTH_LONG).show()
                    return
                }
                adapter.addItems(searchResult.results)
                currentPage = 1
                pageCount = searchResult.total_pages
            }

            override fun onFailure(call: Call<SearchMovieResponse>, t: Throwable) {
                isLoading = false
                adapter.removeLoader()
                t.printStackTrace()
                Toast.makeText(baseContext, "Error is : $t", Toast.LENGTH_LONG).show()
            }
        })*/
    }
}