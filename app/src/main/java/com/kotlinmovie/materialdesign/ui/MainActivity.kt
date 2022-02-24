package com.kotlinmovie.materialdesign.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kotlinmovie.materialdesign.R
import com.kotlinmovie.materialdesign.ui.fragment.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance()).commit()
        }
    }
}