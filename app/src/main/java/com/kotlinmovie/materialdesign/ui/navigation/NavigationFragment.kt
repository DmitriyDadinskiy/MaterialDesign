package com.kotlinmovie.materialdesign.ui.navigation

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.kotlinmovie.materialdesign.R
import com.kotlinmovie.materialdesign.databinding.FragmentNavigationBinding
import com.kotlinmovie.materialdesign.ui.fragment.ViewBindingFragment


class NavigationFragment : ViewBindingFragment<FragmentNavigationBinding> (FragmentNavigationBinding :: inflate){

    companion object {
        fun newInstance() = NavigationFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
    }
    private fun initViewPager() {
        binding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout,binding.viewPager){
            tab, position ->
            tab.text = when (position){
                EARTH_KEY-> "Земля"
                MARS_KEY->"Марс"
                SPUTNIK_KEY->"Спутник"
                SOLAR_KEY->"Солнце"
                SYSTEM_KEY->"Ситема"
                else->"Земля"
            }

        }.attach()
        binding.tabLayout.getTabAt(EARTH_KEY)?.setIcon(R.drawable.bg_earth)
        binding.tabLayout.getTabAt(MARS_KEY)?.setIcon(R.drawable.bg_mars)
        binding.tabLayout.getTabAt(SPUTNIK_KEY)?.setIcon(R.drawable.bg_sputnik)
        binding.tabLayout.getTabAt(SOLAR_KEY)?.setIcon(R.drawable.bg_solar)
        binding.tabLayout.getTabAt(SYSTEM_KEY)?.setIcon(R.drawable.bg_system)
    }
}


