package com.kotlinmovie.materialdesign.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.kotlinmovie.materialdesign.R
import com.kotlinmovie.materialdesign.databinding.FragmentChipsBinding
import com.kotlinmovie.materialdesign.viewModel.DataModel
import com.kotlinmovie.materialdesign.viewModel.PictureOfTheDayViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


private const val SHARE_PREF_NAME = "SHARE_PREF_NAME"

class ChipsFragment : Fragment() {

    private var _binding: FragmentChipsBinding? = null
    private val binding: FragmentChipsBinding
        get() = _binding!!

    companion object {
        fun newInstance() = ChipsFragment()
    }

    private var chipsData = "2022-02-22"

    private val modelDada: DataModel by activityViewModels()
    private val preferences: SharedPreferences by lazy {
        this.requireActivity().getSharedPreferences(
            SHARE_PREF_NAME, Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        modelDada.filters.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            chipsData = it.toString()
        })
        initChipsClick()
    }

    private fun initChipsClick() {
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val calendar: Calendar = GregorianCalendar()
            when (checkedId) {
                R.id.today_chip1 -> {
                    chipsData = dateFormat.format(Date())
                    Log.e("TAG", "$chipsData ")
                    modelDada.positionChips(chipsData)
                }
                R.id.today_chip2 -> {
                    calendar.add(Calendar.DATE, -1)
                    chipsData = dateFormat.format(calendar.time)
                    Log.e("TAG", "$chipsData ")
                    modelDada.positionChips(chipsData)
                }
                R.id.today_chip3 -> {
                    calendar.add(Calendar.DATE, -2)
                    chipsData = dateFormat.format(calendar.time)
                    Log.e("TAG", "$chipsData ")
                    modelDada.positionChips(chipsData)
                }
                else -> {
                    chipsData = dateFormat.format(Date())
                    modelDada.positionChips(chipsData)
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
