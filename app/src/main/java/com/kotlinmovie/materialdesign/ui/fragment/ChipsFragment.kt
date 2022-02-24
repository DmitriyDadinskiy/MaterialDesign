package com.kotlinmovie.materialdesign.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Build.ID
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.ChipGroup
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



        private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this)[PictureOfTheDayViewModel::class.java]
    }
    private val modelDada: DataModel by viewModels()
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChipsClick()
//        modelDada.positionChips.value = chipsData
        viewModel.sendServerRequest(chipsData, onError = ::loadingError)    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initChipsClick() {
        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == 1) {
                val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                chipsData = dateFormat.format(Date())
                viewModel.sendServerRequest(chipsData, onError = ::loadingError)
                Log.e("TAG", "$chipsData ")

            }
            if (checkedId == 2) {

                val calendar: Calendar = GregorianCalendar()
                calendar.add(Calendar.DATE, -1)
                val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                chipsData = dateFormat.format(calendar.time)
                viewModel.sendServerRequest(chipsData, onError = ::loadingError)
                Log.e("TAG", "$chipsData ")

            if (checkedId == 3) {
                val calendar: Calendar = GregorianCalendar()
                calendar.add(Calendar.DATE, -2)
                val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                chipsData = dateFormat.format(calendar.time)
                viewModel.sendServerRequest(chipsData, onError = ::loadingError)
                Log.e("TAG", "$chipsData ")

            }
            }

        }
    }





    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}

fun loadingError(throwable: Throwable) {

}


