package com.example.myapplication.ui

import android.content.Intent
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
import com.example.myapplication.R
import com.example.myapplication.api.BaseResponse
import com.example.myapplication.database.wishdb.WishListDatabase
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.repository.HomeRepository
import com.example.myapplication.repository.WishListRepository
import com.example.myapplication.ui.viewmodel.AccountViewModel
import com.example.myapplication.ui.viewmodel.AccountViewModelFactory
import com.example.myapplication.ui.viewmodel.CategoriesViewModel
import com.example.myapplication.ui.viewmodel.CategoriesViewModelFactory
import com.example.myapplication.ui.viewmodel.HomeViewModel
import com.example.myapplication.ui.viewmodel.HomeViewModelFactory
import com.example.myapplication.ui.viewmodel.WishListViewModel
import com.example.myapplication.ui.viewmodel.WishListViewModelFactory
import com.example.myapplication.utils.Constants

class MainActivity : AppCompatActivity() {
    private  val TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding
    lateinit var homeViewModel: HomeViewModel
    lateinit var wishListViewModel: WishListViewModel
    lateinit var accountViewModel: AccountViewModel
    lateinit var categoriesViewModel: CategoriesViewModel
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
        initWishListViewModel()
        initAccountViewModel()
        initCategoriesViewModel()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navHostController = navHostFragment.findNavController()
        binding.bottomNavigation.setupWithNavController(navHostController )


    }

    override fun onResume() {
        super.onResume()
        val intent = intent
        val openTab = intent.getStringExtra(Constants.OPEN_TAB_FROM_NOTIFICATION) ?: null
        if (openTab != null){
            openTab(openTab)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val openTab = intent.getStringExtra(Constants.OPEN_TAB_FROM_NOTIFICATION) ?: null
        if (openTab != null){
            openTab(openTab)
        }
    }

    private fun openTab(openTab: String) {
        when(openTab){
            "1" ->{
                binding.bottomNavigation.selectedItemId = R.id.wishFragment
            }"2" ->{
                binding.bottomNavigation.selectedItemId = R.id.categoryFragment
            }"3" ->{
                binding.bottomNavigation.selectedItemId = R.id.cartFragment
            }

            "4" -> {
                binding.bottomNavigation.selectedItemId = R.id.accountFragment
            }
        }

    }

    private fun initCategoriesViewModel() {
        val homeRepository = HomeRepository()
        val categoriesViewModelFactory = CategoriesViewModelFactory(homeRepository,application)
        categoriesViewModel = ViewModelProvider(this, categoriesViewModelFactory)[CategoriesViewModel::class.java]
    }

    private fun initAccountViewModel() {
        val accountViewModelFactory = AccountViewModelFactory(application)
        accountViewModel = ViewModelProvider(this, accountViewModelFactory)[AccountViewModel::class.java]
    }

    private fun initWishListViewModel() {
        val wishListDatabase = WishListDatabase(this)
        val wishListRepository = WishListRepository(wishListDatabase)
        val wishListViewModelFactory = WishListViewModelFactory(wishListRepository, application)
        wishListViewModel = ViewModelProvider(this, wishListViewModelFactory)[WishListViewModel::class]

    }

    private fun initHomeViewModel() {
        val homeRepository = HomeRepository()
        val homeViewModelFactory =HomeViewModelFactory(homeRepository, application)
        homeViewModel = ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]


    }
}