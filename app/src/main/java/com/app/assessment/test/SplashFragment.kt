package com.app.assessment.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.assessment.test.databinding.FragmentSplashScreenBinding
import kotlinx.coroutines.*

/**
 * This class is used to display splash screen for 4 seconds with logo animation.
 */
class SplashFragment: Fragment() {

    private lateinit var screenBinding: FragmentSplashScreenBinding
    private var job: Job? = Job()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        screenBinding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return screenBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        screenBinding.ivSplashImage.startAnimation(animation)
        animation.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                job?.cancel()
                job = CoroutineScope(Dispatchers.Main).launch {
                    delay(2000)
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                }
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job?.cancel()
    }
}