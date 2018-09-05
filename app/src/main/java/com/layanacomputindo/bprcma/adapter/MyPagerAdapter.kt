package com.layanacomputindo.bprcma.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
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