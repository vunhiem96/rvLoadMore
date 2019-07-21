package com.vunhiem.rvcheckboxkotlin


import android.os.Handler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    internal lateinit var recyclerView: RecyclerView
    internal lateinit var recyclerViewAdapter: RecyclerViewAdapter
    internal var rowsArrayList = ArrayList<String?>()

    internal var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycleView)
        populateData()
        initAdapter()
        initScrollListener()


    }

    private fun populateData() {
        var i = 0
        while (i < 10) {
            rowsArrayList.add("Item $i")
            i++
        }
    }

    private fun initAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerViewAdapter = RecyclerViewAdapter(rowsArrayList)
        recyclerView.adapter = recyclerViewAdapter

    }

    private fun initScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == rowsArrayList.size - 1) {

                        loadMore()
                        isLoading = true
                    }
                }
            }
        })


    }

    private fun loadMore() {
        rowsArrayList.add(null)
        recyclerViewAdapter.notifyItemInserted(rowsArrayList.size - 1)


        val handler = Handler()
        handler.postDelayed({
            rowsArrayList.removeAt(rowsArrayList.size - 1)
            val scrollPosition = rowsArrayList.size
            recyclerViewAdapter.notifyItemRemoved(scrollPosition)
            var currentSize = scrollPosition
            val nextLimit = currentSize + 10

            while (currentSize - 1 < nextLimit) {
                rowsArrayList.add("Item $currentSize")
                currentSize++
            }

            recyclerViewAdapter.notifyDataSetChanged()
            isLoading = false
        }, 2000)


    }
}