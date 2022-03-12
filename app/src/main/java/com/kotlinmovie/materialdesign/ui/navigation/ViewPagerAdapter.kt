package com.kotlinmovie.materialdesign.ui.navigation

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

const val EARTH_KEY = 0
const val MARS_KEY = 1
const val SPUTNIK_KEY = 2
const val SOLAR_KEY = 3
const val SYSTEM_KEY = 4

class ViewPagerAdapter(
    fragmentManager: FragmentActivity)
    : FragmentStateAdapter(fragmentManager) {
    private val fragment = arrayOf(FragmentEarth(),FragmentMars(), FragmentSputnik(),FragmentSolar(),
        FragmentSystem())

    override fun getItemCount() = fragment.size


    override fun createFragment(position: Int) = fragment[position]
    }
