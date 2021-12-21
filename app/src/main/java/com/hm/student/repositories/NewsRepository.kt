package com.hm.student.repositories

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hm.student.model.NewsModel


class NewsRepository {
    val TAG = "NewsAndEventsRepository"

    val newsAndEventsList = MutableLiveData<List<NewsModel>>()
    val mRef = FirebaseDatabase.getInstance()

    companion object {
        private var mInstance: NewsRepository? = null
        val instance: NewsRepository?
            get() {
                if (mInstance == null) {
                    mInstance = NewsRepository()
                }
                return mInstance
            }
    }

    init {
        fetchDocs()
    }

    private fun fetchDocs() {
        val list = ArrayList<NewsModel>()

        mRef.getReference("news")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ds in snapshot.children) {
                        val item = ds.getValue(NewsModel::class.java)
                        list.add(item!!)
                    }
                    newsAndEventsList.value = list
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

}