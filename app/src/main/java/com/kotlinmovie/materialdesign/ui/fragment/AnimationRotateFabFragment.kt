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
    private val durationStartY = 1500L
    private val durationBase = 2500L
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
                    .setDuration(durationBase).start()
                ObjectAnimator.ofFloat(binding.oneContainer, View.TRANSLATION_Y, -50f, -260f)
                    .setDuration(durationStartY).start()
                ObjectAnimator.ofFloat(binding.twoContainer, View.TRANSLATION_Y, -20f, -130f)
                    .setDuration(durationStartY).start()

                ObjectAnimator.ofFloat(binding.oneContainer, View.TRANSLATION_X, 330f, 0f)
                    .setDuration(durationBase).start()
                ObjectAnimator.ofFloat(binding.twoContainer, View.TRANSLATION_X, 330f, 0f)
                    .setDuration(durationBase).start()

                binding.oneContainer.animate()
                    .alpha(1f)
                    .setDuration(durationStartY )
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            binding.oneContainer.isClickable = true
                        }
                    })
                binding.twoContainer.animate()
                    .alpha(1f)
                    .setDuration(durationStartY )
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            binding.twoContainer.isClickable = true
                        }
                    })
                binding.transparentBackground.animate()
                    .alpha(0.5f)
                    .duration = durationStartY
            } else {
                ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 405f, 0f)
                    .setDuration(durationBase).start()
                                ObjectAnimator.ofFloat(binding.oneContainer, View.TRANSLATION_Y, -260f, -50f)
                    .setDuration(durationBase).start()
                ObjectAnimator.ofFloat(binding.twoContainer, View.TRANSLATION_Y, -130f, -20f)
                    .setDuration(durationBase).start()

                ObjectAnimator.ofFloat(binding.oneContainer, View.TRANSLATION_X, 0f, 330f)
                    .setDuration(durationBase).start()
                ObjectAnimator.ofFloat(binding.twoContainer, View.TRANSLATION_X, 0f, 330f)
                    .setDuration(durationBase).start()



                binding.oneContainer.animate()
                    .alpha(0f)
                    .setDuration(durationBase )
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            binding.oneContainer.isClickable = false
                        }
                    })
                binding.twoContainer.animate()
                    .alpha(0f)
                    .setDuration(durationBase )
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            binding.twoContainer.isClickable = false
                        }
                    })
                binding.transparentBackground.animate()
                    .alpha(0f)
                    .setDuration(durationStartY)
            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}