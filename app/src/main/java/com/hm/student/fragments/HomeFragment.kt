package com.hm.student.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hm.student.R
import com.hm.student.adapters.NewsAdapter
import com.hm.student.databinding.FragmentHomeBinding
import com.hm.student.interfaces.OnItemSelectedListener
import com.hm.student.model.NewsModel
import com.hm.student.ui.NewPost
import com.hm.student.viewmodel.NewsViewModel

class HomeFragment : Fragment() {
    private val TAG = "HomeFragment"

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: NewsViewModel
    private val mList = ArrayList<NewsModel>()
    private val mFullList: ArrayList<NewsModel> = ArrayList()

    private lateinit var mAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        viewModel.init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        if (PreferenceManager.getDefaultSharedPreferences(requireContext()).getString("type", "")
                .equals("Student", true)
        ) {
            binding.newPost.visibility = View.GONE
        }
        binding.newPost.setOnClickListener {
            startActivity(Intent(requireContext(), NewPost::class.java).apply {
                putExtra("count", mFullList.size.toLong())
            })
        }

        viewModel.announcementsList.observe(viewLifecycleOwner, Observer { list ->
            mList.addAll(list)
            mFullList.addAll(list)
            mAdapter.notifyDataSetChanged()
            binding.progressBar.visibility = View.GONE
            binding.mainLayout.visibility = View.VISIBLE
        })

        mAdapter = NewsAdapter(mList)
        binding.recyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }
        binding.spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(item: MenuItem) {
                setSubList(item.title.toString())
                mAdapter.notifyDataSetChanged()
            }
        })

        return binding.root
    }

    private fun setSubList(type: String) {
        mList.clear()
        if (type == "All") {
            mList.addAll(mFullList)
        }

        for (item in mFullList) {
            if (item.category == type) {
                mList.add(item)
            }
        }
    }
}