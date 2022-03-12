package com.kotlinmovie.materialdesign.ui.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kotlinmovie.materialdesign.databinding.FragmentSolarBinding

class FragmentSolar: Fragment() {

    private var _binding: FragmentSolarBinding? = null
    private val binding: FragmentSolarBinding
        get() = _binding!!

    companion object {
        fun newInstance() = FragmentSolar()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSolarBinding.inflate(inflater,container,false)
        return binding.root
    }
}