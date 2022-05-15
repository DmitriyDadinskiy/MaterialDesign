package com.kotlinmovie.materialdesign.ui.coordinator

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kotlinmovie.materialdesign.R
import com.kotlinmovie.materialdesign.databinding.FragmentLayoutBinding

import com.kotlinmovie.materialdesign.ui.fragment.ViewBindingFragment

class LayoutFragment : ViewBindingFragment<FragmentLayoutBinding>(FragmentLayoutBinding::inflate) {

    companion object {
        fun newInstance() = LayoutFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayoutView()
        startFragment(ConstraintFragment())
    }

    private fun initLayoutView() {
        binding.bottomLayout.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.constraint -> {
                    startFragment(ConstraintFragment())
                    true
                }
                R.id.coordinator -> {
                    startFragment(CoordinatorFragment())
                    true
                }
                else -> true

            }
        }

    }

    private fun startFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.coordinator_container, fragment).commit()
    }


}