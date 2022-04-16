package com.kotlinmovie.materialdesign.ui.fragment

import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kotlinmovie.materialdesign.R
import com.kotlinmovie.materialdesign.databinding.BottomNavigationLayoutBinding
import com.kotlinmovie.materialdesign.ui.coordinator.LayoutActivity
import com.kotlinmovie.materialdesign.ui.navigation.BottomNavigationViewActivity
import com.kotlinmovie.materialdesign.ui.navigation.NavigationActivity


class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding: BottomNavigationLayoutBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickMenu()
    }

    private fun initClickMenu() {
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_earth -> {
                    startActivity(Intent(requireContext(),BottomNavigationViewActivity::class.java))

                }
                R.id.navigation_pager -> {
                    startActivity(Intent(requireContext(),NavigationActivity::class.java))


                }
                R.id.navigation_layout -> {
                    startActivity(Intent(requireContext(),LayoutActivity ::class.java))

                }
                R.id.animation_rotate_fab ->{
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.container, AnimationRotateFabFragment.newInstance()).addToBackStack("")
                        .commit()
                }
            }
            dismiss()
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
