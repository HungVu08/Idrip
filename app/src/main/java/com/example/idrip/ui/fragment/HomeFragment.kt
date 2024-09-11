package com.example.idrip.ui.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.idrip.R
import com.example.idrip.adapter.IClickListener
import com.example.idrip.adapter.ListProductAdapter
import com.example.idrip.api.BaseResponse
import com.example.idrip.databinding.FragmentHomeBinding
import com.example.idrip.models.ListProductResponse
import com.example.idrip.models.Product
import com.example.idrip.ui.MainActivity
import java.util.function.Predicate

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var listProductAdapter: ListProductAdapter
    private var imageList = ArrayList<SlideModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        val homeViewModel = (activity as MainActivity).homeViewModel

        homeViewModel.listProductResult.observe(viewLifecycleOwner, Observer { it ->

            when (it) {
                is BaseResponse.Success -> {
                    hideLoading()
                    it.data?.let {
                        updateData(it)

                    }
                }

                is BaseResponse.Error -> {
                    hideLoading()
                }

                is BaseResponse.Loading -> {
                    showLoading()
                }
            }
        })

    }

    private fun initView() {
        listProductAdapter = ListProductAdapter(callback)
        binding.rvListProduct.apply {
            adapter = listProductAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
        initDataSlider()

    }

    private fun initDataSlider() {
        imageList.add(SlideModel("https://bit.ly/2YoJ77H", ScaleTypes.FIT))
        imageList.add(SlideModel("https://bit.ly/2BteuF2", ScaleTypes.FIT))
        imageList.add(SlideModel("https://bit.ly/3fLJf72", ScaleTypes.FIT))

        binding.imageSlider.setImageList(imageList)
    }

    private val callback = object : IClickListener {
        override fun changeWishStateListener(position: Int) {
            val product = listProductAdapter.differ.currentList[position]
            product.isWish = !product.isWish
            listProductAdapter.notifyItemChanged(position)
            if (product.isWish) {
                (activity as MainActivity).wishListViewModel.upsertWish(product)
            } else {
                (activity as MainActivity).wishListViewModel.deleteWish(product)
            }
        }

        override fun showDetailsProductListener(position: Int) {

        }

    }

    private fun updateData(it: ListProductResponse) {
        it.products.let {
            listProductAdapter.updateData(it)

            fetchListHotDeals(it)
        }


    }



    private fun fetchListHotDeals(it: List<Product>) {

       val hotDeals = it.stream().filter { product-> product.discountPercentage > 10 }.toList()

        val hotDealAdapter = ListProductAdapter(callback)
        binding.rvHotDeals.apply {
            adapter = hotDealAdapter
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        }

        hotDealAdapter.differ.submitList(hotDeals)
    }


    private fun showLoading() {

    }

    private fun hideLoading() {

    }

}