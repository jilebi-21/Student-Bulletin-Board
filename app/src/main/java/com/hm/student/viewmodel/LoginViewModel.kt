package com.hm.student.viewmodel

import android.util.Patterns
import androidx.lifecycle.*
import com.hm.student.ui.login.Result
import com.google.firebase.auth.FirebaseUser
import com.hm.student.repositories.LoginRepository
import com.hm.student.ui.login.LoginFormState

class LoginViewModel : ViewModel() {

    private val TAG = "LoginViewModel"

    private val _loginForm = LoginFormState()
    val loginForm = MutableLiveData<LoginFormState>()

    private val mLoginRepo: LoginRepository = LoginRepository()
    var loggedInUser = MutableLiveData<Result<FirebaseUser>>()

    fun login(email: String, password: String) {
        isValidEmail(email)
        isValidPassword(password)

        if (_loginForm.isDataValid) {
            mLoginRepo.firebaseLogin(loggedInUser, email, password)
        }

        loginForm.value = _loginForm
    }

    private fun isValidEmail(email: String) {
        when {
            email.isEmpty() -> {
                _loginForm.emailError = "Empty email"
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _loginForm.emailError = "Not a valid email"
            }
            else -> {
                _loginForm.emailError = null
            }
        }
    }

    private fun isValidPassword(password: String) {
        when {
            password.isEmpty() -> {
                _loginForm.passwordError = "Empty password"
            }
            password.length <= 3 -> {
                _loginForm.passwordError = "Password too short"
            }
            password.length > 15 -> {
                _loginForm.passwordError = "Password too long"
            }
            else -> {
                _loginForm.passwordError = null
            }
        }
    }
}