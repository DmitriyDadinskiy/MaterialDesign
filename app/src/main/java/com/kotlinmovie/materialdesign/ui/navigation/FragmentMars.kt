package com.kotlinmovie.materialdesign.ui.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.kotlinmovie.materialdesign.databinding.FragmentMarsBinding
import com.kotlinmovie.materialdesign.viewModel.PictureState
import com.kotlinmovie.materialdesign.viewModel.PictureOfTheDayViewModel

class FragmentMars: Fragment() {
    private var _binding: FragmentMarsBinding? = null
    private val binding: FragmentMarsBinding
        get() = _binding!!

    companion object {
        fun newInstance() = FragmentMars()
    }
    private val viewModelMars: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this)[PictureOfTheDayViewModel::class.java]
    }
    private var yesterdayDate = "2016-02-21"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        initYesterdayDate()
    }

//    private fun initYesterdayDate() {
//        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//        yesterdayDate = dateFormat.format(Date())
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModelMars.getLiveDataState().observe(viewLifecycleOwner){this.renderData(it)}
        viewModelMars.getMarsPicture(earthDate = yesterdayDate, onError =  :: loadingError)
        super.onViewCreated(view, savedInstanceState)

    }

    private fun loadingError(throwable: Throwable) {
        Snackbar.make(binding.root,
            "ошибка загрузки ${throwable.message}",
            Snackbar.LENGTH_SHORT).show()
    }
    private fun renderData(pictureState: PictureState) {
        when (pictureState) {
            is PictureState.Error -> {
                Toast.makeText(requireContext(), "не прошла загрузка", Toast.LENGTH_LONG).show()
            }
            is PictureState.Loading -> {
                binding.progressBarMars.visibility = ProgressBar.VISIBLE

            }
            is PictureState.SuccessMars -> {
                if (pictureState.serverResponseDataMars.photos.isEmpty()){
                    Snackbar.make(binding.root,
                        "В этот день curiosity не сделал ни одного снимка",
                        Snackbar.LENGTH_SHORT).show()
                }else{
                    binding.marsImagesView.load(pictureState.serverResponseDataMars.photos.first().imgSrc) //почему-то марс не загружает картинку, хотя с сервера она приходит
                }
                binding.progressBarMars.visibility = ProgressBar.INVISIBLE

            }
            else -> {}
        }
    }

}