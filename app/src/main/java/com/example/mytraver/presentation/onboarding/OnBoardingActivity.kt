package com.example.mytraver.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.mytraver.databinding.ActivityOnBoardingBinding
import com.example.mytraver.presentation.adapter.OnBoardingAdapter
import com.example.mytraver.presentation.authentication.AuthenticationActivity
import com.example.mytraver.presentation.dashboard.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityOnBoardingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(
            window, false
        )

        val viewPagerAdapter = OnBoardingAdapter(this)
        binding.viewpager2.adapter = viewPagerAdapter


        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (binding.viewpager2.currentItem == 0){
                    finish()
                }else{
                    binding.viewpager2.currentItem -= 1
                }
            }
        })
    }

     fun nextPage(){
        val nextItem = binding.viewpager2.currentItem+1
        if (nextItem<3){
            binding.viewpager2.currentItem = nextItem
        }
    }

     fun finishOnBoarding(){
        startActivity(Intent(this@OnBoardingActivity, AuthenticationActivity::class.java))
        finish()
    }
}