package com.hm.student.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hm.student.R
import com.hm.student.adapters.NewsAdapter
import com.hm.student.model.NewsModel
import com.hm.student.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    private val TAG = "SearchActivity"

    private val mCompleteList = ArrayList<NewsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(search_toolbar)

        val viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        val adapter = NewsAdapter(mCompleteList)
        viewModel.init()
        viewModel.announcementsList.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")
            mCompleteList.addAll(it)
            adapter.notifyDataSetChanged()
        })

        search_recycler_view.adapter = adapter
        search_recycler_view.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search_microphone -> {

            }
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }
}