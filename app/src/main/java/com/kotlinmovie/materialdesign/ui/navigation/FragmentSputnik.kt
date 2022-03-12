package com.kotlinmovie.materialdesign.ui.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.material.snackbar.Snackbar
import com.kotlinmovie.materialdesign.databinding.FragmenSputnikBinding
import com.kotlinmovie.materialdesign.databinding.FragmentSolarBinding
import com.kotlinmovie.materialdesign.viewModel.PictureOfTheDayViewModel
import com.kotlinmovie.materialdesign.viewModel.PictureState
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class FragmentSputnik : Fragment() {


    private var _binding: FragmenSputnikBinding? = null
    private val binding: FragmenSputnikBinding
        get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this)[PictureOfTheDayViewModel::class.java]
    }
    private var date = "2016-02-21"
    private var lon = 100.75f
    private var lat = 1.5f
    private var dim = 0.15f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTodayDate()
    }

    private fun initTodayDate() {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar: Calendar = GregorianCalendar()
        calendar.add(Calendar.DATE, -55)
        date = dateFormat.format(calendar.time)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmenSputnikBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getLiveDataState().observe(viewLifecycleOwner) { this.renderData(it) }
        viewModel.getSputnikPicture(
            lon = lon, lat = lat, dateString = date, dim = dim,
            onError = ::loadingError
        )
        super.onViewCreated(view, savedInstanceState)
    }

    private fun loadingError(throwable: Throwable) {
        Snackbar.make(
            binding.root,
            "ошибка загрузки ${throwable.message}",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun renderData(pictureState: PictureState) {
        when (pictureState) {
            is PictureState.Error -> {

            }
            is PictureState.Loading -> {
                binding.progressBarSputnik.visibility = ProgressBar.VISIBLE;
            }
            is PictureState.SuccessSputnik -> {

                binding.sputnikImageView.load(pictureState.serverResponseDataSputnik.url)

                binding.progressBarSputnik.visibility = ProgressBar.GONE;
            }
        }

    }
}