package com.example.techexactlytest.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.techexactlytest.R
import com.example.techexactlytest.data.dataSource.RetrofitBuilder
import com.example.techexactlytest.data.dataSource.repository.ApplicationRepository
import com.example.techexactlytest.databinding.ActivityMainBinding
import com.example.techexactlytest.utils.getViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var tabViewPagerAdapter: TabViewPagerAdapter? = null

    private var currentPosition = 0
    private val tabLayoutLabels = arrayOf(
        "Applications",
        "Settings"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        viewModel = getViewModel { MainViewModel(ApplicationRepository(RetrofitBuilder.apiService)) }
        initViewPager()
    }

    private fun initViewPager() {
        tabViewPagerAdapter = TabViewPagerAdapter(this, tabLayoutLabels)
        binding.viewpager.adapter = tabViewPagerAdapter
        /*To stop swipe effect*/
        /*binding.viewpager.isUserInputEnabled = false*/
        TabLayoutMediator(binding.tab, binding.viewpager, false, false) { tab, position ->
            tab.text = tabLayoutLabels[position]
            /*binding.viewpager.setCurrentItem(tab.position, true)*/
        }.attach()
        binding.viewpager.currentItem = currentPosition

    }


}