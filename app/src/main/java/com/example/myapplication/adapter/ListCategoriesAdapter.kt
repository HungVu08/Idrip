package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.LayoutProductCategoriesBinding
import com.example.myapplication.models.ListCategoriesResponseItem

class ListCategoriesAdapter :
    RecyclerView.Adapter<ListCategoriesAdapter.ListCategoriesViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<ListCategoriesResponseItem>() {
        override fun areItemsTheSame(
            oldItem: ListCategoriesResponseItem,
            newItem: ListCategoriesResponseItem
        ): Boolean {
            return oldItem.slug == newItem.slug
        }

        override fun areContentsTheSame(
            oldItem: ListCategoriesResponseItem,
            newItem: ListCategoriesResponseItem
        ): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtil)

    fun updateData(data : List<ListCategoriesResponseItem>){
        differ.submitList(data)

    }

    class ListCategoriesViewHolder(private val binding: LayoutProductCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(listCategoriesResponseItem: ListCategoriesResponseItem) {
            binding.listCategories = listCategoriesResponseItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCategoriesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_product_categories, parent, false)
        val binding = LayoutProductCategoriesBinding.bind(view)
        return ListCategoriesViewHolder(binding)
    }

    override fun getItemCount() = differ.currentList.size
    override fun onBindViewHolder(holder: ListCategoriesViewHolder, position: Int) {
        holder.bindData(differ.currentList[position])
    }
}