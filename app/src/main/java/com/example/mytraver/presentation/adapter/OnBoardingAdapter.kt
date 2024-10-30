package com.example.mytraver.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mytraver.presentation.onboarding.fragment.FirstOneBoardingFragment
import com.example.mytraver.presentation.onboarding.fragment.SecondOnBoardingFragment
import com.example.mytraver.presentation.onboarding.fragment.ThirdOnBoardingFragment

class OnBoardingAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->FirstOneBoardingFragment()
            1-> SecondOnBoardingFragment()
            else->ThirdOnBoardingFragment()
        }
    }
}