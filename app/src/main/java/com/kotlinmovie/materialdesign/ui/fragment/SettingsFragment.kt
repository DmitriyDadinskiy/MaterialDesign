package com.kotlinmovie.materialdesign.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment

import com.kotlinmovie.materialdesign.R
import com.kotlinmovie.materialdesign.databinding.FragmentSettingsBinding
import com.kotlinmovie.materialdesign.ui.MainActivity
import com.kotlinmovie.materialdesign.ui.LightGreen
import com.kotlinmovie.materialdesign.ui.BlueGrey
import com.kotlinmovie.materialdesign.ui.Cyan


private const val SHARE_PREF_NAME = "SHARE_PREF_NAME"

class SettingsFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding!!


    companion object {
        fun newInstance() = SettingsFragment()
    }

    private var positionSwitch = false

    private val preferences: SharedPreferences by lazy {
        this.requireActivity().getSharedPreferences(
            SHARE_PREF_NAME, Context.MODE_PRIVATE
        )
    }
    private lateinit var parentActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = requireActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSelectTheme()


    }

    private fun initSelectTheme() {
        binding.MyBlueGreyRadioButton3.setOnClickListener(this)
        binding.MyCyanRadioButton2.setOnClickListener(this)
        binding.MyLightGreenRadioButton.setOnClickListener(this)

        if (Build.VERSION.SDK_INT <= 28) {
            binding.nightThemeSwitch1.visibility = View.GONE
        }
        binding.nightThemeSwitch1.setOnCheckedChangeListener { buttonView, isChecked ->
            positionSwitch = if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                isChecked
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                isChecked
            }
        }

    }

    override fun onClick(v: View?) {

        if (v != null) {
            when (v.id) {
                R.id.MyLightGreen_radioButton -> {
                    parentActivity.setCurrentTheme(LightGreen)
                    parentActivity.recreate()
                }
                R.id.MyBlueGrey_radioButton3 -> {
                    parentActivity.setCurrentTheme(BlueGrey)
                    parentActivity.recreate()
                }
                R.id.MyCyan_radioButton2 -> {
                    parentActivity.setCurrentTheme(Cyan)
                    parentActivity.recreate()
                }

            }
        }

    }



    override fun onStart() {
        binding.nightThemeSwitch1.isChecked = preferences.getBoolean(SHARE_PREF_NAME, false)
        super.onStart()
    }


    override fun onStop() {
        preferences.edit().let {
            it.putBoolean(SHARE_PREF_NAME, positionSwitch)
            it.commit()
        }
        super.onStop()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}


