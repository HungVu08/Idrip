package com.example.idrip.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.idrip.R
import com.example.idrip.api.BaseResponse
import com.example.idrip.database.wishdb.WishListDatabase
import com.example.idrip.databinding.ActivityMainBinding
import com.example.idrip.repository.HomeRepository
import com.example.idrip.repository.WishListRepository
import com.example.idrip.ui.viewmodel.HomeViewModel
import com.example.idrip.ui.viewmodel.HomeViewModelFactory
import com.example.idrip.ui.viewmodel.WishListViewModel
import com.example.idrip.ui.viewmodel.WishListViewModelFactory

class MainActivity : AppCompatActivity() {
    private  val TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding
    lateinit var homeViewModel: HomeViewModel
    lateinit var wishListViewModel: WishListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val view = layoutInflater.inflate(R.layout.activity_main, null)
        binding = ActivityMainBinding.bind(view)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initHomeViewModel()
        initWishViewModel()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navHostController = navHostFragment.findNavController()
        binding.bottomNavigation.setupWithNavController(navHostController)



    }

    private fun initWishViewModel() {
        val wishListDatabase = WishListDatabase(this)
        val wishListRepository = WishListRepository(wishListDatabase)
        val wishListViewModelFactory = WishListViewModelFactory(wishListRepository,application)
        wishListViewModel = ViewModelProvider(this, wishListViewModelFactory)[WishListViewModel::class.java]

    }

    private fun initHomeViewModel() {
        val homeRepository = HomeRepository()
        val homeViewModelFactory = HomeViewModelFactory(homeRepository, application)
        homeViewModel = ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
    }
}