package com.example.mytraver.presentation.dashboard.itinerary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytraver.R
import com.example.mytraver.databinding.FragmentItineraryBinding
import com.example.mytraver.domain.model.ItineraryData
import com.example.mytraver.presentation.StateUser
import com.example.mytraver.presentation.adapter.listener.OnItemVerticalAdapterItinerary
import com.example.mytraver.presentation.adapter.RecyclerViewItineraryAdapter
import com.example.mytraver.presentation.dashboard.itinerary.viewmodel.ItineraryFragmentViewModel
import kotlinx.coroutines.launch

class ItineraryFragment : Fragment(), OnItemVerticalAdapterItinerary {
    private var _binding: FragmentItineraryBinding? = null
    private val binding get() = _binding!!

    private lateinit var itineraryListAdapter: RecyclerViewItineraryAdapter

    private val itineraryFragmentViewModel by activityViewModels<ItineraryFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItineraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itineraryFragmentViewModel.getListItinerary()
        initRecyclerView()
        loadDataItinerary()

    }

    private fun initRecyclerView() {
        itineraryListAdapter = RecyclerViewItineraryAdapter(listener = this)
        binding.rvPopularPlace.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPopularPlace.adapter = itineraryListAdapter
    }

    private fun loadDataItinerary() {
        viewLifecycleOwner.lifecycleScope.launch {
            itineraryFragmentViewModel.listItinerary.collect {
                when (it) {
                    StateUser.Loading -> {

                    }

                    is StateUser.Success -> {
                        if(it.data.isEmpty()){
                            binding.btnSearch.visibility = View.INVISIBLE
                            binding.rvPopularPlace.visibility = View.INVISIBLE

                        }else{
                            binding.btnSearch.visibility = View.VISIBLE
                            binding.rvPopularPlace.visibility = View.VISIBLE
                            binding.ivEmptyData.isVisible = false
                            itineraryListAdapter.addNewData(it.data)
                        }
                    }

                    is StateUser.Error -> {
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(item: ItineraryData) {
        val bundle = bundleOf(DetailItineraryFragment.KEY_ID_ITINERARY to item.id)
        findNavController().navigate(R.id.action_navigation_itinerary_to_detailRecommendedDestinationFragment, bundle)
    }


}