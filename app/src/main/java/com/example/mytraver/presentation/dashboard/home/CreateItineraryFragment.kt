package com.example.mytraver.presentation.dashboard.home

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mytraver.R
import com.example.mytraver.databinding.FragmentCreateItineraryFromDetailBinding
import com.example.mytraver.domain.model.DetailDestinationResponse
import com.example.mytraver.domain.model.ItineraryData
import com.example.mytraver.presentation.dashboard.home.viewmodel.CreateItineraryFragmentViewModel
import kotlinx.coroutines.launch

class CreateItineraryFragment : Fragment() {
    private var _binding: FragmentCreateItineraryFromDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateItineraryFromDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val dataDetailResponse by lazy {
        arguments?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(KEY_DETAIL_RESPONSE, DetailDestinationResponse::class.java)
            } else {
                it.getParcelable(KEY_DETAIL_RESPONSE)
            }
        }
    }

    private val dataItinerary by lazy {
        arguments?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(KEY_UPDATE_ITINERARY, ItineraryData::class.java)
            } else {
                it.getParcelable(KEY_UPDATE_ITINERARY)
            }
        }
    }


    private val createItineraryFragmentViewModel by activityViewModels<CreateItineraryFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        addOrUpdateItinerary()
        binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.action_createItineraryFromDetailFragment_to_detailDestinationFragment)
        }
}

    private fun addOrUpdateItinerary(){
        if (dataDetailResponse!=null){
            dataDetailResponse?.let {
                setupViewAddItinerary(it)
                createNewItinerary(it)
            }
        }else if (dataItinerary!=null){
            dataItinerary?.let {
                binding.tieNotes.setText(it.notes)
                setupViewUpdateItinerary(it)
                updateItinerary(it)
            }
        }
    }

    private fun setupViewAddItinerary(data: DetailDestinationResponse) {
        Glide.with(requireContext())
            .load(data.image)
            .into(binding.ivDestination)
        binding.tieDuration.setText(resources.getString(R.string.label_duration, data.duration))
        binding.tieDestination.setText(data.name)
    }

    private fun setupViewUpdateItinerary(data: ItineraryData) {
        Glide.with(requireContext())
            .load(data.image)
            .into(binding.ivDestination)
        binding.tieDuration.setText(resources.getString(R.string.label_duration, data.duration))
        binding.tieDestination.setText(data.name)
    }

    private fun createNewItinerary(data: DetailDestinationResponse) {
        binding.btnSubmitItinerary.setOnClickListener {
            val inputData = ItineraryData(
                id = null,
                idDestination = data.id,
                activity = data.activity,
                duration = data.duration,
                location = data.location,
                name = data.name,
                description = data.description,
                popularity = data.popularity,
                type = data.type,
                image = data.image,
                notes = binding.tieNotes.text.toString()
            )
            viewLifecycleOwner.lifecycleScope.launch {
                val addIsSuccess = createItineraryFragmentViewModel.addItinerary(inputData)
                if (addIsSuccess) {
                    //pindah/keluar dan munculin toast
                    Toast.makeText(requireContext(), "Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_createItineraryFromDetailFragment_to_detailDestinationFragment)
                } else {
                    //muncul toast error
                    Toast.makeText(requireContext(), "Data Gagal Ditambahkan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun updateItinerary(data: ItineraryData) {
        binding.btnSubmitItinerary.setOnClickListener {
            val inputData = ItineraryData(
                id = data.id,
                idDestination = data.idDestination,
                activity = data.activity,
                duration = data.duration,
                location = data.location,
                name = data.name,
                description = data.description,
                popularity = data.popularity,
                type = data.type,
                image = data.image,
                notes = binding.tieNotes.text.toString()
            )
            viewLifecycleOwner.lifecycleScope.launch {
                val updateIsSuccess = createItineraryFragmentViewModel.updateItinerary(inputData)
                if (updateIsSuccess) {
                    //pindah/keluar dan munculin toast
                    Toast.makeText(requireContext(), "Data Berhasil Diubah", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_createItineraryFromDetailFragment_to_detailRecommendedDestinationFragment)
                } else {
                    //muncul toast error
                    Toast.makeText(requireContext(), "Data Gagal Diubah", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_DETAIL_RESPONSE = "KEY DETAIL RESPONSE"
        const val KEY_UPDATE_ITINERARY = "KEY UPDATE ITINERARY"
    }
}