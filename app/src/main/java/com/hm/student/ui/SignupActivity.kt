package com.hm.student.ui

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.hm.student.R
import com.hm.student.databinding.ActivitySignupBinding
import com.hm.student.model.UserModel
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {
    val TAG = "SignupActivity"
    lateinit var binding: ActivitySignupBinding

    var mAuth: FirebaseAuth? = null
    var mRef: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        mAuth = FirebaseAuth.getInstance()
        mRef = FirebaseDatabase.getInstance()
    }

    fun submitForm(view: View) {
        val email = binding.signupEmailLayout.editText?.text.toString()
        val name = binding.signupNameLayout.editText?.text.toString()
        val password = binding.signupPasswordLayout.editText?.text.toString()
        val confirmPassword = binding.signupConfirmPasswordLayout.editText?.text.toString()

        if (validateName(name)
            && validateEmail(email)
            && validatePassword(password, confirmPassword)
        ) {
            mAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnSuccessListener {
                    var type = "Faculty"
                    for (ch in email) {
                        if (ch.isDigit()) type = "Student"
                    }
                    val user = UserModel(mAuth?.currentUser?.uid!!, name, email, type, password)
                    Log.d(TAG, "submitForm: $user")
                    mRef?.getReference("users/${user.uid}")
                        ?.setValue(user)
                        ?.addOnSuccessListener {
                            Log.i(TAG, "submitForm: Successfully registered")
                            Toast.makeText(this, "Successfully registered", Toast.LENGTH_SHORT)
                                .show()
                            finish()
                        }
                        ?.addOnFailureListener {
                            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                            Log.e(TAG, "submitForm: $it")
                        }
                }
                ?.addOnFailureListener {
                    Toast.makeText(this, "Failed to register", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "submitForm: $it")
                }
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validatePassword(p1: String, p2: String): Boolean {
        return if (p1 == p2) {
            if (p1.length in 5..16) {
                true
            } else {
                binding.signupPasswordLayout.error = "Password should be between 5 to 15 characters"
                false
            }
        } else {
            false
        }
    }

    private fun validateEmail(email: String): Boolean {
        return when {
            Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                val arr = email.split("@")
                if (arr[1] == "anits.edu.in") {
                    true
                } else {
                    binding.signupEmailLayout.error = "Not an anits mail"
                    false
                }
            }
            else -> {
                binding.signupEmailLayout.error = "Not a valid email"
                false
            }
        }
    }

    private fun validateName(name: String): Boolean {
        return when {
            name.length <= 3 -> {
                binding.signupNameLayout.error = "Name too short"
                false
            }
            name.length > 15 -> {
                binding.signupNameLayout.error = "Name too long >15"
                false
            }
            else -> {
                val pattern: Pattern = Pattern.compile("^[a-zA-Z\\s]*$")
                if (pattern.matcher(name).matches()) {
                    true
                } else {
                    binding.signupNameLayout.error = "Invalid name format"
                    false
                }
            }
        }
    }
}