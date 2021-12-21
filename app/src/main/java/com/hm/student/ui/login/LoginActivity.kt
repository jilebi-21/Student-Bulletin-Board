package com.hm.student.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hm.student.R
import com.hm.student.databinding.ActivityLoginBinding
import com.hm.student.model.UserModel
import com.hm.student.ui.MainActivity
import com.hm.student.ui.SignupActivity
import com.hm.student.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    val TAG = "LoginActivity"
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_login
        )

        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        setObservable()
        binding.loginBtn.setOnClickListener {
            mViewModel.login(
                binding.loginEmailLayout.editText!!.text.toString(),
                binding.loginPasswordLayout.editText!!.text.toString()
            )
        }

    }

    private fun setObservable() {
        mViewModel.loginForm.observe(this, Observer { loginState ->

            binding.loginEmailLayout.error = loginState.emailError
            binding.loginPasswordLayout.error = loginState.passwordError

            if (binding.loginEmailLayout.error == null) {
                binding.loginEmailLayout.isErrorEnabled = false
            }

            if (binding.loginPasswordLayout.error == null) {
                binding.loginPasswordLayout.isErrorEnabled = false
            }
        })

        mViewModel.loggedInUser.observe(this, Observer { result ->
            if (result is Result.Success) {
                Log.i(TAG, "setObservable: login success")
                val user = FirebaseAuth.getInstance().currentUser
                FirebaseDatabase.getInstance().getReference("users/${user?.uid}")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {

                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            val data = p0.getValue(UserModel::class.java)!!
                            val editor =
                                PreferenceManager.getDefaultSharedPreferences(this@LoginActivity)
                                    .edit()
                            editor.putString("name", data.name)
                            editor.putString("email", data.email)
                            editor.putString("type", data.type)
                            editor.putString("uid", data.uid)
                            editor.apply()
                        }
                    })
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }

            if (result is Result.Error) {
                Log.e(TAG, "setObservable: ${result.exception}")
                Toast.makeText(this, "${result.exception.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun signUp(view: View) {
        startActivity(Intent(this, SignupActivity::class.java))
    }
}