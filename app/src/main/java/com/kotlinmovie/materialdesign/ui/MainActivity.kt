package com.kotlinmovie.materialdesign.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kotlinmovie.materialdesign.R
import com.kotlinmovie.materialdesign.ui.fragment.PictureOfTheDayFragment
import com.kotlinmovie.materialdesign.ui.fragment.SplashFragment


const val LightGreen = 1
const val BlueGrey = 2
const val Cyan = 3


class MainActivity : AppCompatActivity() {

    private val KEY_SP = "sp"
    private val KEY_CURRENT_THEME = "current_theme"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getRealStyle(getCurrentTheme()))
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, SplashFragment.newInstance()).commit()
    }

    fun setCurrentTheme(currentTheme: Int) {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CURRENT_THEME, currentTheme)
        editor.apply()
    }

    fun getCurrentTheme(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME, -1)
    }

    fun getRealStyle(currentTheme: Int): Int {
        return when (currentTheme) {
            LightGreen -> R.style.MyLightGreen
            BlueGrey -> R.style.MyBlueGrey
            Cyan-> R.style.MyCyan
            else -> R.style.MyCyan
        }
    }
}