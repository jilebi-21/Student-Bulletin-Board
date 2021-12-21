package com.hm.student.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.hm.student.R
import com.hm.student.databinding.ActivityNewPostBinding
import com.hm.student.model.NewsModel

class NewPost : AppCompatActivity() {
    private val TAG = "NewPost"
    lateinit var binding: ActivityNewPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_post)

        val count = intent.extras!!.getLong("count") + 100

        binding.postButton.setOnClickListener {
            val data = binding.inputTextField.text.toString()
            val type =
                findViewById<RadioButton>(binding.typeGroup.checkedRadioButtonId)
                    .text
                    .toString()
            val postItem = NewsModel(
                count,
                data,
                type,
                System.currentTimeMillis(),
                PreferenceManager.getDefaultSharedPreferences(this).getString("name", "")!!
            )

            Log.d(TAG, "onCreate: $postItem")

            FirebaseDatabase.getInstance().getReference("news/$count")
                .setValue(postItem)
                .addOnSuccessListener {
                    Log.i(TAG, "Successfully posted")
                }
                .addOnFailureListener {
                    Log.e(TAG, "Failed to post")
                    Toast.makeText(this, "Failed to post", Toast.LENGTH_SHORT).show()
                }

        }
    }
}