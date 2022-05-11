package com.kotlinmovie.materialdesign.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kotlinmovie.materialdesign.R
import com.kotlinmovie.materialdesign.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding
        get() = _binding!!

    companion object {
        fun newInstance() = SplashFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Thread {
            Thread.sleep(2000L)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PictureOfTheDayFragment.newInstance()).commit()

        }.start()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}