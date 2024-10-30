package com.example.mytraver.presentation.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.mytraver.R
import com.example.mytraver.databinding.ActivityAuthenticationBinding
import com.example.mytraver.presentation.StateUser
import com.example.mytraver.presentation.dashboard.MainActivity
import com.example.mytraver.presentation.onboarding.OnBoardingActivity
import com.example.mytraver.presentation.typepreference.ChooseTypeReferenceActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAuthenticationBinding.inflate(layoutInflater)
    }

    private val authenticationViewModel by viewModels<AuthenticationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener {
            val email = binding.tieEmail.text.toString().trim()
            val password = binding.tiePassword.text.toString()
            //TODO checking
            lifecycleScope.launch {
                authenticationViewModel.login(email, password).collect{
                    when(it){
                        StateUser.Loading ->{
                            //loading
                        }
                        is StateUser.Success -> {
                            Toast.makeText(this@AuthenticationActivity, "Berhasil masuk", Toast.LENGTH_SHORT).show()
                            val data = authenticationViewModel.preferences.value
                            Log.d("data", "onCreate: $data")
                            if (data.isNullOrEmpty()) {
                                startActivity(Intent(this@AuthenticationActivity, ChooseTypeReferenceActivity::class.java))
                                finish()
                            } else if (data.isNotEmpty()) {
                                startActivity(Intent(this@AuthenticationActivity, MainActivity::class.java))
                                finish()
                            }
                        }
                        is StateUser.Error -> Toast.makeText(this@AuthenticationActivity, "Gagal Masuk", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}