package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.myapplication.R
import com.example.myapplication.adapter.ListProductAdapter
import com.example.myapplication.api.BaseResponse
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.models.ListProductResponse
import com.example.myapplication.models.Product
import com.example.myapplication.ui.MainActivity
import com.example.myapplication.utils.Constants
import java.util.function.Consumer
import java.util.function.Predicate
import java.util.stream.Collectors


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val TAG = "HomeFragment"
    lateinit var listProductAdapter: ListProductAdapter
    private var imageList = ArrayList<SlideModel>()
    lateinit var hotDealAdapter: ListProductAdapter
    lateinit var mostPoplularAdapter: ListProductAdapter
    private var wishListData: List<Product>? = null
    private var listData: List<Product>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

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
                    it.data?.let { response ->
                        listData = it.data.products
                        Log.d(TAG, "initHomeViewModel: ${response.products.size} ")
                        updateData(response)
                    }
                }

                is BaseResponse.Error -> {

                }

                is BaseResponse.Loading -> {

                }
            }


        })

        (activity as MainActivity).wishListViewModel.getAllWishList().observe(viewLifecycleOwner,
            Observer {
                wishListData = it
                listData?.let { it1 -> fetchListHotDeals(it1) }
                listData?.let { it1 -> fetchMostPoplular(it1) }
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
        if (imageList.size < Constants.SLIDER_SIZE) {
            imageList.clear()

            imageList.add(
                SlideModel(
                    "https://bit.ly/2YoJ77H", ScaleTypes.FIT
                )
            )
            imageList.add(
                SlideModel(
                    "https://bit.ly/2BteuF2", ScaleTypes.FIT
                )
            )
            imageList.add(SlideModel("https://bit.ly/3fLJf72", ScaleTypes.FIT))
        }
        binding.imageSlider.apply {
            setImageList(imageList)
            setSlideAnimation(AnimationTypes.DEPTH_SLIDE)
        }
    }

    private val callback = object : ListProductAdapter.IClickListener {
        override fun changeWishStateListener(position: Int) {
            val product = listProductAdapter.differ.currentList[position]
            product.iswish = !product.iswish
            listProductAdapter.notifyItemChanged(position)

            if (product.iswish) {
                (activity as MainActivity).wishListViewModel.upsertWish(product)
            } else {
                (activity as MainActivity).wishListViewModel.deleteWish(product)
            }
        }

        override fun showDetailsProductListener(position: Int) {

        }
    }

    private fun updateData(response: ListProductResponse) {
        response.products.let { listProduct ->
//            listProductAdapter.updateData(listProduct)
            fetchListHotDeals(listProduct)
            fetchMostPoplular(listProduct)

        }

    }

    private fun fetchMostPoplular(listProduct: List<Product>) {
        val mostPlular = listProduct.stream().filter(Predicate { product -> product.rating > 4.9 })
            .collect(Collectors.toList())

        mostPoplularAdapter = ListProductAdapter(callbackMostPoplular)
        binding.rvMostPoplular.apply {
            adapter = mostPoplularAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
        // reset wish
        mostPlular.forEach(Consumer {
            it.iswish = false
        })

        wishListData?.forEach(Consumer { wishProduct ->
            val index = mostPlular.indexOfFirst { wishProduct.id == it.id }
            if (index >= 0) {
                mostPlular[index].iswish = true
            }
        })

        mostPoplularAdapter.differ.submitList(mostPlular)

    }
    private val callbackMostPoplular = object : ListProductAdapter.IClickListener {
        override fun changeWishStateListener(position: Int) {
            val product = mostPoplularAdapter.differ.currentList[position]
            product.iswish = !product.iswish
            mostPoplularAdapter.notifyItemChanged(position)
//            val scrollPosition: Int = layoutManager.findFirstVisibleItemPosition()
//            layoutManager.scrollToPosition(scrollPosition);
            if (product.iswish) {
                (activity as MainActivity).wishListViewModel.upsertWish(product)
            } else {
                (activity as MainActivity).wishListViewModel.deleteWish(product)
            }
        }

        override fun showDetailsProductListener(position: Int) {
            TODO("Not yet implemented")
        }

    }




    private fun fetchListHotDeals(listProduct: List<Product>) {
        val hotDeals = listProduct.stream()
            .filter(Predicate { product -> product.discountPercentage > 13 })
            .collect(Collectors.toList())

        hotDealAdapter = ListProductAdapter(callbackHostDeals)
        binding.rvHotDeals.apply {
            adapter = hotDealAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        // reset wish
        hotDeals.forEach(Consumer {
            it.iswish = false
        })
        wishListData?.forEach(Consumer { wishProduct ->
            val index = hotDeals.indexOfFirst { wishProduct.id == it.id }
            if (index >= 0) {
                hotDeals[index].iswish = true
            }
        })
        hotDealAdapter.differ.submitList(hotDeals)
    }

    private val callbackHostDeals = object : ListProductAdapter.IClickListener {
        override fun changeWishStateListener(position: Int) {
            val product = hotDealAdapter.differ.currentList[position]
            product.iswish = !product.iswish
            hotDealAdapter.notifyItemChanged(position)
            if (product.iswish) {
                (activity as MainActivity).wishListViewModel.upsertWish(product)
            } else {
                (activity as MainActivity).wishListViewModel.deleteWish(product)
            }
        }

        override fun showDetailsProductListener(position: Int) {
            TODO("Not yet implemented")
        }

    }
}

