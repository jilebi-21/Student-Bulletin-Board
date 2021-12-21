package com.hm.student.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hm.student.model.NewsModel
import com.hm.student.repositories.NewsRepository

class NewsViewModel : ViewModel() {
    var announcementsList = MutableLiveData<List<NewsModel>>()

    private val repo = NewsRepository.instance

    fun init() {
        announcementsList = repo!!.newsAndEventsList
    }

}