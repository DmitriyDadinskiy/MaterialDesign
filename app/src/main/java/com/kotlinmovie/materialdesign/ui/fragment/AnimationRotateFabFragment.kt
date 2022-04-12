package com.kotlinmovie.materialdesign.ui.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kotlinmovie.materialdesign.databinding.FragmentAnimationRotateFabBinding


class AnimationRotateFabFragment : Fragment() {
    private val duration = 3000L
    private val duration2 = 4000L
    private var flag = false

    private var _binding: FragmentAnimationRotateFabBinding? = null
    private val binding: FragmentAnimationRotateFabBinding
        get() = _binding!!

    companion object {
        fun newInstance() = AnimationRotateFabFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimationRotateFabBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFabAnimation()
    }

    private fun initFabAnimation() {
        binding.fab.setOnClickListener {
            flag = !flag
            if (flag) {
                ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 0f, 405F)
                    .setDuration(duration2).start()
                ObjectAnimator.ofFloat(binding.oneContainer, View.TRANSLATION_Y, -50f, -260f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.twoContainer, View.TRANSLATION_Y, -20f, -130f)
                    .setDuration(duration).start()

                ObjectAnimator.ofFloat(binding.oneContainer, View.TRANSLATION_X, 330f, 0f)
                    .setDuration(duration2).start()
                ObjectAnimator.ofFloat(binding.twoContainer, View.TRANSLATION_X, 330f, 0f)
                    .setDuration(duration2).start()

                binding.oneContainer.animate()
                    .alpha(1f)
                    .setDuration(duration )
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            binding.oneContainer.isClickable = true
                        }
                    })
                binding.twoContainer.animate()
                    .alpha(1f)
                    .setDuration(duration )
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            binding.twoContainer.isClickable = true
                        }
                    })
                binding.transparentBackground.animate()
                    .alpha(0.5f)
                    .duration = duration
            } else {
                ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 405f, 0f)
                    .setDuration(duration2).start()
                                ObjectAnimator.ofFloat(binding.oneContainer, View.TRANSLATION_Y, -260f, -50f)
                    .setDuration(duration2).start()
                ObjectAnimator.ofFloat(binding.twoContainer, View.TRANSLATION_Y, -130f, -20f)
                    .setDuration(duration2).start()

                ObjectAnimator.ofFloat(binding.oneContainer, View.TRANSLATION_X, 0f, 330f)
                    .setDuration(duration2).start()
                ObjectAnimator.ofFloat(binding.twoContainer, View.TRANSLATION_X, 0f, 330f)
                    .setDuration(duration2).start()



                binding.oneContainer.animate()
                    .alpha(0f)
                    .setDuration(duration2 )
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            binding.oneContainer.isClickable = false
                        }
                    })
                binding.twoContainer.animate()
                    .alpha(0f)
                    .setDuration(duration2 )
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            binding.twoContainer.isClickable = false
                        }
                    })
                binding.transparentBackground.animate()
                    .alpha(0f)
                    .setDuration(duration)
            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}