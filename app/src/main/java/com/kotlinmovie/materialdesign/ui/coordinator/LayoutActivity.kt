package com.kotlinmovie.materialdesign.ui.coordinator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kotlinmovie.materialdesign.R
import com.kotlinmovie.materialdesign.databinding.ActivityLayoutBinding
import com.kotlinmovie.materialdesign.ui.BlueGrey
import com.kotlinmovie.materialdesign.ui.Cyan
import com.kotlinmovie.materialdesign.ui.LightGreen


private val KEY_SP = "sp"
private val KEY_CURRENT_THEME = "current_theme"
class LayoutActivity : AppCompatActivity() {

    lateinit var binding: ActivityLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getRealStyle(getCurrentTheme()))
        binding = ActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayoutView()
        startFragment(ConstraintFragment())
    }

    private fun initLayoutView() {
        binding.bottomLayout.setOnItemSelectedListener {
            when(it.itemId){
                R.id.constraint ->{
                    startFragment(ConstraintFragment())
                true}
                R.id.coordinator->{
                    TODO()
                true}
                else -> true

            }
        }

    }
    private fun startFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.coordinator_container, fragment).commit()
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
}