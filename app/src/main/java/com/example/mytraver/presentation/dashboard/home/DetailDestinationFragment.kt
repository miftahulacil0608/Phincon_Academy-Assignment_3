package com.example.mytraver.presentation.dashboard.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mytraver.R
import com.example.mytraver.databinding.FragmentDetailDestinationBinding
import com.example.mytraver.databinding.FragmentRecomendedListBinding
import com.example.mytraver.domain.model.DataItem
import com.example.mytraver.domain.model.DetailDestinationResponse
import com.example.mytraver.domain.model.ItineraryData
import com.example.mytraver.presentation.StateUser
import com.example.mytraver.presentation.dashboard.home.viewmodel.DetailDestinationFragmentViewModel
import kotlinx.coroutines.launch

class DetailDestinationFragment : Fragment() {
    private var _binding: FragmentDetailDestinationBinding? = null
    private val binding get() = _binding!!

    private val id by lazy { arguments?.getInt(KEY_ID) }

    private val detailDestinationFragmentViewModel by activityViewModels<DetailDestinationFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailDestinationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id?.let {
            detailDestinationFragmentViewModel.getDetailDestination(it)
        }
        loadDetailDestination()


        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun loadDetailDestination() {
        viewLifecycleOwner.lifecycleScope.launch {
            detailDestinationFragmentViewModel.dataDetailDestination.collect {
                when (it) {
                    StateUser.Loading -> {
                        //shimmer
                    }

                    is StateUser.Success -> {
                        //shimmer off
                        setupView(it.data)
                        toCreateItinerary(it.data)
                    }

                    is StateUser.Error -> {
                        //munculin view data kosong gitu aja
                        Toast.makeText(requireContext(), "${it.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun toCreateItinerary(item:DetailDestinationResponse){
        binding.btnCreateItinerary.setOnClickListener {
            val data = DetailDestinationResponse(
                id = item.id,
                type = item.type,
                name = item.name,
                image = item.image,
                description = item.description,
                popularity = item.popularity,
                activity = item.activity,
                duration = item.duration,
                location = item.location
            )
            val bundle = bundleOf(CreateItineraryFragment.KEY_DETAIL_RESPONSE to data)
            findNavController().navigate(R.id.action_detailDestinationFragment_to_createItineraryFromDetailFragment, bundle)
        }
    }

    private fun setupView(item: DetailDestinationResponse) {
        binding.apply {
            Glide.with(requireContext())
                .load(item.image)
                .centerCrop()
                .into(binding.ivDestination)
            tvTitleDestination.text = item.name
            tvLocation.text = item.location
            ratingBar.rating = item.popularity.toFloat()
            tvRating.text = item.popularity
            tvDetailDescription.text = item.description
            tvDuration.text = resources.getString(R.string.label_duration, item.duration)
            tvTypeOfActivity.text = item.activity
            tvTypeOfReference.text = item.type
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_ID = "KEY ID"

    }


}