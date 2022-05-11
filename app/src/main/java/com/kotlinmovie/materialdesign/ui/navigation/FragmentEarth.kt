package com.kotlinmovie.materialdesign.ui.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.ProgressBar.INVISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.kotlinmovie.materialdesign.BuildConfig
import com.kotlinmovie.materialdesign.databinding.FragmentEarthBinding
import com.kotlinmovie.materialdesign.viewModel.PictureOfTheDayViewModel
import com.kotlinmovie.materialdesign.viewModel.PictureState


class FragmentEarth:Fragment() {
    private var _binding: FragmentEarthBinding? = null
    private val binding: FragmentEarthBinding
        get() = _binding!!

    private val viewModelEarth: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this)[PictureOfTheDayViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModelEarth.getLiveDataState().observe(viewLifecycleOwner){this.renderData(it)}
        viewModelEarth.getEpicPicture {  }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun renderData(pictureState: PictureState) {
        when(pictureState){
            is PictureState.Error -> {
                Toast.makeText(requireContext(), "не прошла загрузка", Toast.LENGTH_LONG).show()
            }
            is PictureState.Loading->{
                binding.progressBarEarth.visibility = ProgressBar.VISIBLE
            }
            is PictureState.SuccessEarthEpic ->{
                val strDate = pictureState.serverResponseData.last().date.split(" ").first()
                val image =pictureState.serverResponseData.last().image
                val url = "https://api.nasa.gov/EPIC/archive/natural/" +
                        strDate.replace("-","/",true) +
                        "/png/" +
                        image +
                        ".png?api_key=${BuildConfig.NASA_API_KEY}"
                binding.earthView.load(url)

                binding.progressBarEarth.visibility = INVISIBLE
            }
            else -> {}
        }

    }
}