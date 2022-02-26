package com.example.techexactlytest.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.techexactlytest.ui.home.application.ApplicationsFragment
import com.example.techexactlytest.ui.home.setting.SettingsFragment

class TabViewPagerAdapter(fragmentActivity: FragmentActivity, private val tabLayoutLabels: Array<String>) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return ApplicationsFragment()
            1 -> return SettingsFragment()
        }
        return ApplicationsFragment()
    }

    override fun getItemCount(): Int {
        return tabLayoutLabels.size
    }

}