package com.layanacomputindo.bprcma.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.layanacomputindo.bprcma.fragment.FirstFragment
import com.layanacomputindo.bprcma.fragment.SecondFragment
import com.layanacomputindo.bprcma.fragment.ThirdFragment

class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FirstFragment()
            }
            1 -> SecondFragment()
            else -> {
                return ThirdFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Tanah"
            1 -> "Kendaraan"
            else -> {
                return "Tabungan"
            }
        }
    }
}