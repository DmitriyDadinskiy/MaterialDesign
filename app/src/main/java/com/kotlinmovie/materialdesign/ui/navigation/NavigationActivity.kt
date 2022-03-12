package com.kotlinmovie.materialdesign.ui.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.kotlinmovie.materialdesign.R
import com.kotlinmovie.materialdesign.databinding.ActivityNavigationBinding
import com.kotlinmovie.materialdesign.ui.BlueGrey
import com.kotlinmovie.materialdesign.ui.Cyan
import com.kotlinmovie.materialdesign.ui.LightGreen

private val KEY_SP = "sp"
private val KEY_CURRENT_THEME = "current_theme"

class NavigationActivity : AppCompatActivity() {
    lateinit var binding: ActivityNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setTheme(getRealStyle(getCurrentTheme()))
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewPager()
    }


    fun getCurrentTheme(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME, -1)
    }

    private fun getRealStyle(currentTheme: Int): Int {
        return when (currentTheme) {
            LightGreen -> R.style.MyLightGreen
            BlueGrey -> R.style.MyBlueGrey
            Cyan -> R.style.MyCyan
            else -> R.style.MyCyan
        }
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


