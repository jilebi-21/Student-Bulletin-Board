package com.hm.student.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.hm.student.R
import com.hm.student.databinding.FragmentForumsBinding

class ForumsFragment : Fragment() {
    private val TAG = "ForumsFragment"

    companion object {
        @JvmStatic
        fun newInstance() =
            ForumsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentForumsBinding>(
            layoutInflater,
            R.layout.fragment_forums,
            container,
            false
        )

        Handler().postDelayed({
            Toast.makeText(requireContext(), "Unable to fetch", Toast.LENGTH_SHORT).show()
        }, 3000)

        return binding.root
    }

}