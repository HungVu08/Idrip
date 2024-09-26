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
import com.example.myapplication.adapter.ListCategoriesAdapter
import com.example.myapplication.api.BaseResponse
import com.example.myapplication.databinding.FragmentAccountBinding
import com.example.myapplication.databinding.FragmentCategoryBinding
import com.example.myapplication.models.ListCategoriesResponse
import com.example.myapplication.ui.MainActivity
import com.example.myapplication.ui.viewmodel.CategoriesViewModel


class CategoryFragment : Fragment() {
    lateinit var categoriesViewModel: CategoriesViewModel
    lateinit var binding: FragmentCategoryBinding
    lateinit var listCategoriesAdapter: ListCategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        binding = FragmentCategoryBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        categoriesViewModel  = (activity as MainActivity).categoriesViewModel
        categoriesViewModel.listCategoriesResult.observe(viewLifecycleOwner, Observer {
            when(it){
                is BaseResponse.Success ->{
                    it.data?.let {
                        updateData(it)
                    }
                }
                is  BaseResponse.Error ->{

                }
                is BaseResponse.Loading ->{

                }
            }
        })




    }

    private fun updateData(it: ListCategoriesResponse) {
        it.let {
            listCategoriesAdapter.updateData(it)
        }
    }


    private fun initView() {
        listCategoriesAdapter = ListCategoriesAdapter()
        binding.rvListCategories.apply {
            adapter = listCategoriesAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }

}