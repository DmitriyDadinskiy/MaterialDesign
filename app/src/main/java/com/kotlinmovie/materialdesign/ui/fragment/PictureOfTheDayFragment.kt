package com.kotlinmovie.materialdesign.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.*
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.provider.FontRequest
import androidx.core.provider.FontsContractCompat
import androidx.lifecycle.ViewModelProvider
import androidx.transition.*
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kotlinmovie.materialdesign.R
import com.kotlinmovie.materialdesign.databinding.FragmentMainPictureOfDayBinding
import com.kotlinmovie.materialdesign.ui.MainActivity
import com.kotlinmovie.materialdesign.viewModel.PictureOfTheDayState
import com.kotlinmovie.materialdesign.viewModel.PictureOfTheDayViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.chromium.base.ThreadUtils.runOnUiThread
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private const val SHARE_PREF_TUTORIAL = "SHARE_PREF_TUTORIAL"
class PictureOfTheDayFragment : ViewBindingFragment<FragmentMainPictureOfDayBinding>(FragmentMainPictureOfDayBinding::inflate) {

    private val preferences: SharedPreferences by lazy {
        this.requireActivity().getSharedPreferences(
            SHARE_PREF_TUTORIAL, Context.MODE_PRIVATE
        )}

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
        return super.onCreateView(inflater, container, savedInstanceState)
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
        createFont()
        startTutorials()
    }

    private fun startTutorials() {
        val hasVisited: Boolean = preferences.getBoolean(SHARE_PREF_TUTORIAL, false)
        if (!hasVisited){
        val builder = GuideView.Builder(requireContext())
            .setTitle(getString(R.string.title_tutorial))
            .setContentText(getString(R.string.discription_tutorial))
            .setGravity(Gravity.center)
            .setDismissType(DismissType.anywhere)
            .setTargetView(binding.chipGroup)
            .setDismissType(DismissType.anywhere)
            .setGuideListener {
                preferences.edit().let {
                    it.putBoolean(SHARE_PREF_TUTORIAL, true)
                    it.apply()
                }
            }
        builder.build().show()

    }}


    private fun createFont() {

            val request = FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                "Source Serif Pro",
                R.array.com_google_android_gms_fonts_certs)
            val callback = object : FontsContractCompat.FontRequestCallback(){
                override fun onTypefaceRetrieved(typeface: Typeface?) {
                    binding.included.bottomSheetDescription.typeface = typeface
                    super.onTypefaceRetrieved(typeface)
                }
            }
            FontsContractCompat.requestFont(requireContext(),request,callback, Handler(Looper.myLooper()!!))
        }

    @SuppressLint("SetTextI18n")
    private fun clickChips() = @Suppress("DEPRECATION")
    binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
        when (checkedId) {
            R.id.today_chip1 -> {

                    constraintSetAnimationStart()
                    disappearPictureOfTheDay()
                    viewModel.sendServerRequest(selectedDate, onError = ::loadingError)
                    binding.imageView.visibility = View.INVISIBLE
                    binding.dataTextview.text = getString(R.string.DateImages) + " "  + selectedDate
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
                    binding.dataTextview.text = getString(R.string.DateImages) + " " + takeDate(-1)
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
                binding.dataTextview.text = getString(R.string.DateImages) + " "  + takeDate(-2)
                Thread {
                    Thread.sleep(durationSet)
                    runOnUiThread {
                        binding.TextviewConstraintSet.text = takeDate(-2)
                    }
                }.start()
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
        binding.progressBar.visibility = ProgressBar.INVISIBLE

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

    private fun showNasaVideo(videoId: String) {
        with(binding) {
            lifecycle.addObserver(binding.youtubePlayer)

            youtubePlayer.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            })
        }
    }

    private fun renderData(pictureOfTheDayState: PictureOfTheDayState) {
        when (pictureOfTheDayState) {
            is PictureOfTheDayState.Error -> {
                Toast.makeText(mContext, "не прошла загрузка", Toast.LENGTH_LONG).show()
                binding.progressBar.visibility = ProgressBar.INVISIBLE
                animationFadePictureOfTheDay()
                constraintSetAnimationEnd()
            }
            is PictureOfTheDayState.Loading -> {
                binding.progressBar.visibility = ProgressBar.VISIBLE
            }
            is PictureOfTheDayState.Success -> {
                if(pictureOfTheDayState.serverResponseData.mediaType == "video"){
                    binding.youtubePlayer.visibility = View.VISIBLE
                    binding.imageView.visibility = View.INVISIBLE
                    showNasaVideo(pictureOfTheDayState.serverResponseData.url)
                }else {
                    binding.youtubePlayer.visibility = View.INVISIBLE
                    binding.imageView.visibility = View.VISIBLE
                    binding.imageView.load(pictureOfTheDayState.serverResponseData.hdurl)
                }
                animationFadePictureOfTheDay()
                constraintSetAnimationEnd()

                val spannable =
                    SpannableStringBuilder(pictureOfTheDayState.serverResponseData.explanation)
                spannable.insert(0, getString(R.string.descritpion_start))
                spannable.setSpan(
                    BackgroundColorSpan(
                        ContextCompat
                            .getColor(requireContext(), R.color.teal_700)), 0, 20,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannable.indices.forEach {
                    if (spannable[it] == 'o') {
                        spannable.setSpan(
                            ImageSpan(requireContext(), R.drawable.ic_mars),
                            it,
                            it + 1,
                            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                }
                spannable.setSpan(
                    UnderlineSpan(), 0,
                    pictureOfTheDayState.serverResponseData.explanation.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.included.bottomSheetDescription.text = spannable

                val spannableHeader =
                    SpannableStringBuilder(pictureOfTheDayState.serverResponseData.title)
                val clickableSpan = object : ClickableSpan() {
                    override fun onClick(p0: View) {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        Toast.makeText(requireContext(), getString(R.string.clickTextSpan),
                            Toast.LENGTH_SHORT).show()
                    }
                }
                spannableHeader.setSpan(
                    clickableSpan, 0,
                    pictureOfTheDayState.serverResponseData.title.length, 0)
                binding.included.bottomSheetDescriptionHeader.movementMethod =
                    LinkMovementMethod.getInstance()
                binding.included.bottomSheetDescriptionHeader.text = spannableHeader

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

        constraintSet.clear(R.id.TextviewConstraintSet, ConstraintSet.TOP)//отвязать TOP, он никуда не делся

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
        binding.progressBar.visibility = ProgressBar.INVISIBLE
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
                            binding.inputEditText.text.toString()
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
                    .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in,
                        R.anim.slide_out)
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

}