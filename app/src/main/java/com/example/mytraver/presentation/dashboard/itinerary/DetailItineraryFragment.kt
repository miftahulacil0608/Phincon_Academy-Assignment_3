package com.example.mytraver.presentation.dashboard.itinerary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mytraver.R
import com.example.mytraver.databinding.FragmentDetailItineraryBinding
import com.example.mytraver.domain.model.ItineraryData
import com.example.mytraver.presentation.StateUser
import com.example.mytraver.presentation.dashboard.home.CreateItineraryFragment
import com.example.mytraver.presentation.dashboard.itinerary.viewmodel.DetailItineraryFragmentViewModel
import kotlinx.coroutines.launch

class DetailItineraryFragment() : Fragment() {
    private var _binding: FragmentDetailItineraryBinding? = null
    private val binding get() = _binding!!

    private val detailItineraryFragmentViewModel by activityViewModels<DetailItineraryFragmentViewModel>()
    private val idItinerary by lazy {
        arguments?.let {
            it.getInt(KEY_ID_ITINERARY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailItineraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        idItinerary?.let {
            detailItineraryFragmentViewModel.getDetailItinerary(it)
        }
        loadDetailData()

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun loadDetailData() {
        viewLifecycleOwner.lifecycleScope.launch {
            detailItineraryFragmentViewModel.listItinerary.collect {
                when (it) {
                    StateUser.Loading -> {

                    }

                    is StateUser.Success -> {
                        //shimmer off
                        setupDetailData(it.data)
                        editData(it.data)
                        deleteData(it.data)
                    }

                    is StateUser.Error -> {
                        //shimmer off
                        //show nothing data
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

    private fun setupDetailData(item: ItineraryData) {
        binding.apply {
            Glide.with(requireContext())
                .load(item.image)
                .into(ivDestination)
            tvDuration.text = resources.getString(R.string.label_duration, item.duration)
            tvTitleDestination.text = item.name
            tvLocation.text = item.location
            ratingBar.rating = item.popularity.toFloat()
            tvRating.text = item.popularity
            tvDetailDescription.text = item.description
            tvTypeOfActivity.text = item.activity
            tvTypeOfReference.text = item.type
            tvDetailNotes.text = item.notes
        }
    }

    private fun editData(item:ItineraryData){
        binding.btnEdit.setOnClickListener {
            val bundle = bundleOf(CreateItineraryFragment.KEY_UPDATE_ITINERARY to item)
            findNavController().navigate(R.id.action_detailRecommendedDestinationFragment_to_createItineraryFromDetailFragment, bundle)
        }
    }

    private fun deleteData(item: ItineraryData) {
        binding.btnDelete.setOnClickListener {
            detailItineraryFragmentViewModel.deleteDataItinerary(item)
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_ID_ITINERARY = "KEY ID ITINERARY"
    }
}