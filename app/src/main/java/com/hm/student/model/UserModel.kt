package com.hm.student.model

data class UserModel(
    var uid: String,
    var name: String,
    var email: String,
    var type: String,
    var password: String
) {
    constructor() : this("", "", "", "", "")
}