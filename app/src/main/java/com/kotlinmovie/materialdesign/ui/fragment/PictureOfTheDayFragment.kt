package com.kotlinmovie.materialdesign.ui.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.*
import coil.api.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kotlinmovie.materialdesign.R
import com.kotlinmovie.materialdesign.databinding.FragmentMainPictureOfDayBinding
import com.kotlinmovie.materialdesign.ui.MainActivity
import com.kotlinmovie.materialdesign.viewModel.PictureOfTheDayState
import com.kotlinmovie.materialdesign.viewModel.PictureOfTheDayViewModel
import kotlinx.coroutines.NonDisposableHandle.parent
import org.chromium.base.ThreadUtils.runOnUiThread
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentMainPictureOfDayBinding? = null
    private val binding: FragmentMainPictureOfDayBinding
        get() = _binding!!

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this)[PictureOfTheDayViewModel::class.java]
    }


    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var isMain: Boolean = true


    private var mContext: Context? = null
    private var selectedDate = "2022-02-21"

    private var durationSet = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTodayDate()

    }

    private fun initTodayDate() {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        selectedDate = dateFormat.format(Date())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = context
        _binding = FragmentMainPictureOfDayBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        iniViewModel()
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun iniViewModel() {
        viewModel.getLiveData().observe(viewLifecycleOwner) { this.renderData(it) }
        viewModel.sendServerRequest(date = selectedDate, onError = ::loadingError)
    }

    private fun init() {
        clickIconWikipedia()
        initBottomSheetBehavior()
        initOptionsMenu()
        clickFab()
        clickChips()
    }

    private fun clickChips() {


        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.today_chip1 -> {

                    constraintSetAnimationStart()
                    disappearPictureOfTheDay()
                    viewModel.sendServerRequest(selectedDate, onError = ::loadingError)
                    binding.imageView.visibility = View.INVISIBLE
                    binding.dataTextview.text = "Дата картинки " + selectedDate
                   Thread {
                       Thread.sleep(durationSet )
                       runOnUiThread {
                           binding.TextviewConstraintSet.text = selectedDate
                       }
                   }.start()
                }
                R.id.yestrday_chip2 -> {

                    constraintSetAnimationStart()
                    disappearPictureOfTheDay()
                    viewModel.sendServerRequest(takeDate(-1), onError = ::loadingError)
                    binding.dataTextview.text = "Дата картинки " + takeDate(-1)
                    Thread {
                        Thread.sleep(durationSet )
                        runOnUiThread {
                            binding.TextviewConstraintSet.text = takeDate(-1)
                        }
                    }.start()

                }
                R.id.befoYestrday_chip3 -> {

                    constraintSetAnimationStart()
                    disappearPictureOfTheDay()
                    viewModel.sendServerRequest(takeDate(-2), onError = ::loadingError)
                    binding.dataTextview.text = "Дата картинки " + takeDate(-2)
                    Thread {
                        Thread.sleep(durationSet)
                        runOnUiThread {
                            binding.TextviewConstraintSet.text = takeDate(-2)
                        }
                    }.start()
                }
            }
        }
    }

    private fun takeDate(count: Int): String {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DAY_OF_MONTH, count)
        val format1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        format1.timeZone = TimeZone.getTimeZone("EST")
        return format1.format(currentDate.time)
    }


    private fun loadingError(throwable: Throwable) {

        Toast.makeText(mContext, "не прошла загрузка $throwable", Toast.LENGTH_LONG).show()
        binding.progressBar.visibility = ProgressBar.INVISIBLE;

        animationFadePictureOfTheDay()
        binding.TextviewConstraintSet.text = "ошибка загрузки"
        constraintSetAnimationEnd()
    }

    private fun clickFab() {
        binding.fab.setOnClickListener {
            if (isMain) {
                binding.bottomAppBar.navigationIcon = null //скрыть бургер
                binding.bottomAppBar.fabAlignmentMode =
                    BottomAppBar.FAB_ALIGNMENT_MODE_END //изменить положение кнопки в конец
                binding.fab.setImageDrawable(
                    ContextCompat
                        .getDrawable(requireContext(), R.drawable.ic_back_fab)
                ) // изменить рисунок иконки на стрелочку назад
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen) // меняем меню
            } else {// вернуть как было
                binding.bottomAppBar.navigationIcon = ContextCompat
                    .getDrawable(requireContext(), R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat
                        .getDrawable(requireContext(), R.drawable.ic_plus_fab)
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
            isMain = !isMain
        }
    }

    private fun initOptionsMenu() {
        setHasOptionsMenu(true)
        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)
    }


    private fun renderData(pictureOfTheDayState: PictureOfTheDayState) {
        when (pictureOfTheDayState) {
            is PictureOfTheDayState.Error -> {
                Toast.makeText(mContext, "не прошла загрузка", Toast.LENGTH_LONG).show()
                binding.progressBar.visibility = ProgressBar.INVISIBLE;
                animationFadePictureOfTheDay()
                constraintSetAnimationEnd()
            }
            is PictureOfTheDayState.Loading -> {
//                binding.progressBar.visibility = ProgressBar.VISIBLE;

            }
            is PictureOfTheDayState.Success -> {
                binding.imageView.load(pictureOfTheDayState.serverResponseData.hdurl)

                animationFadePictureOfTheDay()
                constraintSetAnimationEnd()

                binding.included.bottomSheetDescriptionHeader
                    .text = pictureOfTheDayState.serverResponseData.title
                binding.included.bottomSheetDescription.text = pictureOfTheDayState
                    .serverResponseData.explanation
                binding.progressBar.visibility = ProgressBar.INVISIBLE;// не пойму почему не отрабатывает код
            }
        }

    }

    private fun constraintSetAnimationEnd() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.constraintContainer)
        val changeBounds = ChangeBounds()
        changeBounds.interpolator = AnticipateOvershootInterpolator(2.0f)
        changeBounds.duration = durationSet
        TransitionManager.beginDelayedTransition(binding.constraintContainer, changeBounds)

            constraintSet.connect(R.id.TextviewConstraintSet, ConstraintSet.BOTTOM, R.id.main,
                ConstraintSet.BOTTOM)
            constraintSet.connect(R.id.TextviewConstraintSet, ConstraintSet.TOP, R.id.main,
                ConstraintSet.TOP)
            constraintSet.connect(R.id.TextviewConstraintSet, ConstraintSet.END, R.id.main,
                ConstraintSet.END)
        constraintSet.connect(R.id.TextviewConstraintSet, ConstraintSet.START, R.id.main,
            ConstraintSet.START)
            constraintSet.applyTo(binding.constraintContainer)

        }


    private fun constraintSetAnimationStart() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.constraintContainer)
        val changeBounds = ChangeBounds()

        changeBounds.duration = durationSet/2
        TransitionManager.beginDelayedTransition(binding.constraintContainer, changeBounds)
        constraintSet.connect(R.id.TextviewConstraintSet, ConstraintSet.BOTTOM, R.id.main,
            ConstraintSet.TOP)
        constraintSet.connect(R.id.TextviewConstraintSet, ConstraintSet.END, R.id.main,
            ConstraintSet.END)
        constraintSet.connect(R.id.TextviewConstraintSet, ConstraintSet.START, R.id.main,
            ConstraintSet.START)
//        constraintSet.setVerticalBias(R.id.TextviewConstraintSet,0.02f)
        constraintSet.applyTo(binding.constraintContainer)
    }


    private fun animationFadePictureOfTheDay() {
        val transition = TransitionSet()
        val fade = Fade()
        fade.duration = 4000
        transition.addTransition(fade)
        TransitionManager.beginDelayedTransition(binding.transitionsContainer, transition)
        binding.imageView.visibility = View.VISIBLE
    }

    private fun disappearPictureOfTheDay() {
        val transition = TransitionSet()
        val fade = Fade()
        fade.duration = 2000
        transition.addTransition(fade)
        TransitionManager.beginDelayedTransition(binding.transitionsContainer, transition)
        binding.imageView.visibility = View.INVISIBLE
    }

    private fun initBottomSheetBehavior() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.included.bottomSheetContainer)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun clickIconWikipedia() {
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                    "https://en.wikipedia.org/wiki/" +
                            "${binding.inputEditText.text.toString()}"
                )
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {
            R.id.app_bar_fav -> Toast.makeText(
                requireContext(),
                "клик сердечко", Toast.LENGTH_SHORT
            ).show()
            R.id.app_bar_settings -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance()).addToBackStack("")
                    .commit()
            }
            android.R.id.home -> {
                BottomNavigationDrawerFragment()
                    .show(requireActivity().supportFragmentManager, "")
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}