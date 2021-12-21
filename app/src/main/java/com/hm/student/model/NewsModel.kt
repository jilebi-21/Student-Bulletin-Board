package com.hm.student.model

data class NewsModel(
    var id: Long,
    var title: String,
    var category: String,
    var postedOn: Long,
    var postedBy: String
) {
    constructor() : this(0, "", "", 0, "")
}