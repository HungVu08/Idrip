package com.example.myapplication.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.ListProductAdapter
import com.example.myapplication.databinding.FragmentWishBinding
import com.example.myapplication.models.Product
import com.example.myapplication.ui.MainActivity
import com.example.myapplication.ui.viewmodel.WishListViewModel

class WishFragment : Fragment() {
    lateinit var binding: FragmentWishBinding
    lateinit var wishListViewModel: WishListViewModel
    lateinit var mProductAdapter: ListProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_wish, container, false)
        binding = FragmentWishBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        wishListViewModel = (activity as MainActivity).wishListViewModel

        wishListViewModel.getAllWishList().observe(viewLifecycleOwner, Observer {it->

            it?.let {
                bindData(it)
            }

        })
    }

    private fun bindData(it: List<Product>) {
        mProductAdapter.updateData(it)
    }

    private fun initView() {
        mProductAdapter = ListProductAdapter(callback)
        binding.rvWishList.apply {
            adapter = mProductAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }

    }
    private val callback = object :ListProductAdapter.IClickListener{
        override fun changeWishStateListener(position: Int) {

            val product = mProductAdapter.differ.currentList[position]
            if (product.iswish){
                wishListViewModel.deleteWish(product)
            }

        }

        override fun showDetailsProductListener(position: Int) {

        }
    }

}


