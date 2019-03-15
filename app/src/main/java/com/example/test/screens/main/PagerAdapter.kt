package com.example.test.screens.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.test.screens.loan.LoanFragment

class PagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

    var tabs = mutableListOf<String>()

    override fun getItem(position: Int): Fragment {
        return LoanFragment.newInstance(tabs[position])
    }

    override fun getCount() = tabs.size

    override fun getPageTitle(position: Int) = tabs[position]
}
