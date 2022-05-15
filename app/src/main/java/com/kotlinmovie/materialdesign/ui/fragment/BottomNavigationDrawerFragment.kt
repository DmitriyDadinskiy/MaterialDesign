package com.kotlinmovie.materialdesign.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kotlinmovie.materialdesign.R
import com.kotlinmovie.materialdesign.databinding.BottomNavigationLayoutBinding
import com.kotlinmovie.materialdesign.ui.coordinator.LayoutFragment
import com.kotlinmovie.materialdesign.ui.navigation.BottomNavigationViewFragment
import com.kotlinmovie.materialdesign.ui.navigation.NavigationFragment
import com.kotlinmovie.materialdesign.ui.recyclerView.RecyclerViewFragment


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
                    requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out)
                        .replace(R.id.container, BottomNavigationViewFragment.newInstance())
                        .addToBackStack("")
                        .commit()
                }
                R.id.navigation_pager -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in,
                        R.anim.slide_out)
                        .replace(R.id.container, NavigationFragment.newInstance()).addToBackStack("")
                        .commit()

                }
                R.id.navigation_layout -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in,
                            R.anim.slide_out)
                        .replace(R.id.container, LayoutFragment.newInstance()).addToBackStack("")
                        .commit()

                }
                R.id.animation_rotate_fab ->{
                    requireActivity().supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in,
                            R.anim.slide_out)
                        .replace(R.id.container, AnimationRotateFabFragment.newInstance())
                        .addToBackStack("")
                        .commit()
                }
                R.id.recycler ->{
                    requireActivity().supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in,
                            R.anim.slide_out)
                        .replace(R.id.container, RecyclerViewFragment.newInstance()).addToBackStack("")
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
