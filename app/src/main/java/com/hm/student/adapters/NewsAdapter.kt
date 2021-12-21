package com.hm.student.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hm.student.R
import com.hm.student.databinding.ItemAnnouncementBinding
import com.hm.student.model.NewsModel

class NewsAdapter(
    private val mList: List<NewsModel>
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val TAG = "NewsAdapter"

//    private val mList: ArrayList<NewsItemModel> = ArrayList()

    class ViewHolder(
        private val itemBinding: ItemAnnouncementBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(info: NewsModel) {
            itemBinding.info = info
            itemBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = DataBindingUtil.inflate<ItemAnnouncementBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_announcement,
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size
    /*fun changeList(list: List<NewsItemModel>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }*/
}