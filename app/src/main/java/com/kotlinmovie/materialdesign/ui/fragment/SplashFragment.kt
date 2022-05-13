package com.kotlinmovie.materialdesign.ui.fragment

import android.os.Bundle
import android.view.View
import com.kotlinmovie.materialdesign.R
import com.kotlinmovie.materialdesign.databinding.FragmentSplashBinding

class SplashFragment : ViewBindingFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    companion object {
        fun newInstance() = SplashFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Thread {
            Thread.sleep(2000L)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance()).commit()

        }.start()
    }
}
