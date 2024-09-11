package com.example.idrip.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.idrip.R
import com.example.idrip.adapter.IClickListener
import com.example.idrip.adapter.ListProductAdapter
import com.example.idrip.databinding.FragmentWishBinding
import com.example.idrip.models.Product
import com.example.idrip.ui.MainActivity
import com.example.idrip.ui.viewmodel.WishListViewModel


class WishFragment : Fragment() {
    lateinit var binding: FragmentWishBinding
    lateinit var wishListViewModel: WishListViewModel
    lateinit var mlistProductAdapter: ListProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_wish, container, false)
        binding = FragmentWishBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        wishListViewModel = (activity as MainActivity).wishListViewModel

        wishListViewModel.getAllWishList().observe(viewLifecycleOwner, Observer {
            it
            it?.let {
                bindData(it)
            }
        })

    }

    private fun bindData(it: List<Product>) {
        mlistProductAdapter.updateData(it)
    }

    private fun initView() {
        mlistProductAdapter = ListProductAdapter(callback)
        binding.rvWishList.apply {
            adapter = mlistProductAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }

    private val callback = object : IClickListener {
        override fun changeWishStateListener(position: Int) {
            val product = mlistProductAdapter.differ.currentList[position]
            if (product.isWish){
                wishListViewModel.deleteWish(product)
            }

        }

        override fun showDetailsProductListener(position: Int) {

        }
    }
}
