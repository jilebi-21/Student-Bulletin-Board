package com.hm.student.ui.login

data class LoginFormState(
    var emailError: String? = null,
    var passwordError: String? = null
) {
    val isDataValid: Boolean
        get() {
            return (emailError == null) && (passwordError == null)
        }
}