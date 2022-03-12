package com.kotlinmovie.materialdesign.ui.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kotlinmovie.materialdesign.databinding.FragmentSystemBinding

class FragmentSystem:Fragment() {

    private var _binding: FragmentSystemBinding? = null
    private val binding: FragmentSystemBinding
        get() = _binding!!

    companion object {
        fun newInstance() = FragmentSystem()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSystemBinding.inflate(inflater,container,false)
        return binding.root
    }

}