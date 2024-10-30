package com.example.mytraver.presentation.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mytraver.databinding.ActivitySplashScreenBinding
import com.example.mytraver.presentation.authentication.AuthenticationActivity
import com.example.mytraver.presentation.dashboard.MainActivity
import com.example.mytraver.presentation.onboarding.OnBoardingActivity
import com.example.mytraver.presentation.typepreference.ChooseTypeReferenceActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    private val splashScreenViewModel by viewModels<SplashScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        lifecycleScope.launch {
            delay(2500L)

            splashScreenViewModel.userSettingData.observe(this@SplashScreenActivity) {
                when {
                    !it.onBoardingPassed -> {
                        startActivity(
                            Intent(
                                this@SplashScreenActivity,
                                OnBoardingActivity::class.java
                            )
                        )
                        finish()
                    }

                    it.token.isEmpty() -> {
                        startActivity(
                            Intent(
                                this@SplashScreenActivity,
                                AuthenticationActivity::class.java
                            )
                        )
                        finish()
                    }

                    it.preferences.isEmpty() -> {
                        startActivity(
                            Intent(
                                this@SplashScreenActivity,
                                ChooseTypeReferenceActivity::class.java
                            )
                        )
                        finish()
                    }

                    else -> {
                        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}