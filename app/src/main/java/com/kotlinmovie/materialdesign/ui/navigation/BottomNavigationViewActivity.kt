package com.kotlinmovie.materialdesign.ui.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kotlinmovie.materialdesign.R
import com.kotlinmovie.materialdesign.databinding.ActivityBottomNavigationViewBinding
import com.kotlinmovie.materialdesign.ui.BlueGrey
import com.kotlinmovie.materialdesign.ui.Cyan
import com.kotlinmovie.materialdesign.ui.LightGreen

private val KEY_SP = "sp"
private val KEY_CURRENT_THEME = "current_theme"

class BottomNavigationViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavigationViewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getRealStyle(getCurrentTheme()))
        binding = ActivityBottomNavigationViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFragment(FragmentEarth())
        initNavigationView()
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

    private fun initNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_earth -> {
                    initFragment(FragmentEarth())
                    true
                }
                R.id.navigation_pager -> {
                    initFragment(FragmentMars())
                    true
                }
                R.id.navigation_system -> {
                    initFragment(FragmentSystem())
                    true
                }
                R.id.navigation_solar -> {
                    initFragment(FragmentSolar())
                    true
                }
                R.id.navigation_sputnik -> {
                    initFragment(FragmentSputnik())
                    true
                }

                else -> true
            }

        }
    }

    private fun initFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.navigation_container, fragment).commit()
    }
}
