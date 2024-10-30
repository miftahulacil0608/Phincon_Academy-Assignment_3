package com.example.mytraver.presentation.typepreference

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mytraver.R
import com.example.mytraver.databinding.ActivityChooseTypeReferenceBinding
import com.example.mytraver.domain.model.TypeReferences
import com.example.mytraver.presentation.adapter.RecyclerViewTypeReferencesAdapter
import com.example.mytraver.presentation.adapter.listener.OnItemTypeReferencesListener
import com.example.mytraver.presentation.dashboard.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseTypeReferenceActivity : AppCompatActivity(), OnItemTypeReferencesListener {
    private val binding by lazy {
        ActivityChooseTypeReferenceBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: RecyclerViewTypeReferencesAdapter

    private var typeReference: String? = null

    private val chooseTypeReferenceViewModel by viewModels<ChooseTypeReferenceViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()
        buttonBack()

        binding.btnSubmit.setOnClickListener {
            lifecycleScope.launch {
                typeReference?.let {
                    handleReferencesSelection(it)
                }
            }
        }
    }

    private fun buttonBack() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private suspend fun handleReferencesSelection(typeReferences: String) {
        val isTypeReferenceEmpty = chooseTypeReferenceViewModel.typeReference.value.isNullOrEmpty()
        Log.d("typeReference", "handleReferencesSelection: ${chooseTypeReferenceViewModel.typeReference.value}")
        val response = chooseTypeReferenceViewModel.chooseTypeReference(typeReferences.lowercase())

        when {
            response && isTypeReferenceEmpty -> {
                startActivity(Intent(this@ChooseTypeReferenceActivity, MainActivity::class.java))
                finish()
            }

            response -> {
                finish()
            }

            else -> {
                Toast.makeText(
                    this@ChooseTypeReferenceActivity,
                    "Unexpected Error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initRecyclerView() {
        adapter = RecyclerViewTypeReferencesAdapter(
            listType = data(),
            listener = this@ChooseTypeReferenceActivity
        )
        binding.rvTypeReferences.layoutManager =
            GridLayoutManager(this@ChooseTypeReferenceActivity, 2)
        binding.rvTypeReferences.adapter = adapter
    }

    private fun data(): List<TypeReferences> {
        return listOf(
            TypeReferences("Alam", R.drawable.iv_nature),
            TypeReferences("Kuliner", R.drawable.iv_culinary),
            TypeReferences("Museum", R.drawable.iv_museum),
            TypeReferences("Pasar", R.drawable.iv_market),
        )
    }

    override fun onItemClick(item: String) {
        typeReference = item
    }
}