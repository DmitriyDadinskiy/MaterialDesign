package com.kotlinmovie.materialdesign.ui.navigation


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kotlinmovie.materialdesign.R
import com.kotlinmovie.materialdesign.databinding.FragmentBottomNavigationViewBinding
import com.kotlinmovie.materialdesign.ui.fragment.ViewBindingFragment



class BottomNavigationViewFragment : ViewBindingFragment<FragmentBottomNavigationViewBinding>(FragmentBottomNavigationViewBinding :: inflate){
    companion object{
        fun newInstance() = BottomNavigationViewFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment(FragmentEarth())
        initNavigationView()
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
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in,
                R.anim.slide_out)
            .replace(R.id.navigation_container, fragment).commit()
    }
}
