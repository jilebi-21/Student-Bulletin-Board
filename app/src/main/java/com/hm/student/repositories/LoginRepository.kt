package com.hm.student.repositories

import androidx.lifecycle.MutableLiveData
import com.hm.student.ui.login.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginRepository {
    val TAG = "LoginRepository"

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun firebaseLogin(
        user: MutableLiveData<Result<FirebaseUser>>,
        email: String,
        password: String
    ) {

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val firebaseUser = mAuth.currentUser
                user.value = Result.Success(firebaseUser!!)
            }
            .addOnFailureListener {
                user.value = Result.Error(it)
            }
    }
}