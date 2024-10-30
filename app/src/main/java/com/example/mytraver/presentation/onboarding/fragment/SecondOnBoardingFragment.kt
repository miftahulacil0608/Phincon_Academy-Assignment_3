package com.example.mytraver.presentation.onboarding.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.mytraver.R
import com.example.mytraver.databinding.FragmentFirstOneBoardingBinding
import com.example.mytraver.databinding.FragmentSecondOnBoardingBinding
import com.example.mytraver.presentation.onboarding.OnBoardingActivity
import com.example.mytraver.presentation.onboarding.OnBoardingViewModel

class SecondOnBoardingFragment : Fragment() {
    private var _binding: FragmentSecondOnBoardingBinding?=null
    private val binding get() = _binding!!
    private val onBoardingViewModel by activityViewModels<OnBoardingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            (activity as OnBoardingActivity).nextPage()
        }
        binding.skip.setOnClickListener {
            onBoardingViewModel.addOnBoardingPassed(true)
            (activity as OnBoardingActivity).finishOnBoarding()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}