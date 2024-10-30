package com.example.mytraver.presentation.dashboard.home

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
import com.bumptech.glide.Glide
import com.example.mytraver.R
import com.example.mytraver.databinding.FragmentHomeBinding
import com.example.mytraver.domain.model.DataItem
import com.example.mytraver.presentation.StateUser
import com.example.mytraver.presentation.adapter.listener.OnItemVerticalAdapter
import com.example.mytraver.presentation.adapter.listener.OnItemHorizontalAdapter
import com.example.mytraver.presentation.adapter.RecyclerViewRecommendedHomeFragmentAdapter
import com.example.mytraver.presentation.adapter.RecyclerViewVerticalAdapter
import com.example.mytraver.presentation.dashboard.home.viewmodel.HomeFragmentViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), OnItemHorizontalAdapter, OnItemVerticalAdapter {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterRecommendation: RecyclerViewRecommendedHomeFragmentAdapter
    private lateinit var adapterPopular: RecyclerViewVerticalAdapter

    private val homeFragmentViewModel by activityViewModels<HomeFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbarDataUser()
        initRecommendedRecycleView()
        initPopularRecyclerView()
        loadDataRecommendedRecyclerView()
        loadDataPopularRecyclerView()

        binding.swipeRefresh.setOnRefreshListener {
            loadDataRecommendedRecyclerView()
            loadDataPopularRecyclerView()
        }

        binding.tvExploreAll.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_recommendedListFragment)
        }

        binding.tvSeeAll.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_popularListFragment)
        }

    }

    private fun setupToolbarDataUser() {
        homeFragmentViewModel.userData.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it.avatar)
                .centerCrop()
                .circleCrop()
                .into(binding.ivAvatarUser)
            binding.tvUsername.text = resources.getString(R.string.label_say_hello, it.username)
        }
    }

    private fun initRecommendedRecycleView() {
        adapterRecommendation = RecyclerViewRecommendedHomeFragmentAdapter(listener = this)
        binding.rvRecommended.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecommended.adapter = adapterRecommendation
    }

    private fun initPopularRecyclerView() {
        adapterPopular = RecyclerViewVerticalAdapter(listener = this@HomeFragment, isRecommended = false)
        binding.rvPopularPlace.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvPopularPlace.adapter = adapterPopular
    }

    private fun loadDataRecommendedRecyclerView() {
        shimmerShow()
        viewLifecycleOwner.lifecycleScope.launch {
            homeFragmentViewModel.recommendedDestination.collect { data ->
                when (data) {
                    StateUser.Loading -> {
                        //shimeer
                        binding.swipeRefresh.isRefreshing = false
                    }

                    is StateUser.Success -> {
                        delay(1000L)
                        binding.swipeRefresh.isRefreshing = false
                        adapterRecommendation.addNewData(data.data)
                        shimmerStop()
                    }

                    is StateUser.Error -> {
                        binding.swipeRefresh.isRefreshing = false
                        binding.shimmerLayout.stopShimmer()
                        binding.shimmerLayout.isVisible = false
                        Toast.makeText(requireContext(), "${data.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun loadDataPopularRecyclerView() {
        shimmerShow()
        viewLifecycleOwner.lifecycleScope.launch {
            homeFragmentViewModel.popularDestination.collect { data ->
                when (data) {
                    StateUser.Loading -> {
                        binding.swipeRefresh.isRefreshing = false
                    }

                    is StateUser.Success -> {
                        delay(1000L)
                        binding.swipeRefresh.isRefreshing = false
                        adapterPopular.addNewData(data.data)
                        shimmerStop()
                    }

                    is StateUser.Error -> {
                       binding.swipeRefresh.isRefreshing = false
                        binding.shimmerLayout.stopShimmer()
                        binding.shimmerLayout.isVisible = false
                        Toast.makeText(requireContext(), "${data.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun shimmerShow(){
        binding.rvRecommended.visibility = View.INVISIBLE
        binding.rvPopularPlace.visibility = View.GONE
        binding.ivAvatarUser.visibility = View.INVISIBLE
        binding.tvUsername.visibility = View.INVISIBLE
        binding.questionTitle.visibility = View.INVISIBLE
        binding.tvRecommendationPlace.visibility = View.INVISIBLE
        binding.tvExploreAll.visibility = View.INVISIBLE
        binding.tvSeeAll.visibility = View.INVISIBLE
        binding.tvPopularDestination.visibility = View.INVISIBLE
        binding.shimmerLayout.startShimmer()
        binding.shimmerLayout.isVisible = true
    }

    private fun shimmerStop(){
        binding.shimmerLayout.stopShimmer()
        binding.shimmerLayout.isVisible = false
        binding.rvRecommended.visibility = View.VISIBLE
        binding.rvPopularPlace.visibility = View.VISIBLE
        binding.ivAvatarUser.visibility = View.VISIBLE
        binding.tvUsername.visibility = View.VISIBLE
        binding.questionTitle.visibility = View.VISIBLE
        binding.tvRecommendationPlace.visibility = View.VISIBLE
        binding.tvExploreAll.visibility = View.VISIBLE
        binding.tvSeeAll.visibility = View.VISIBLE
        binding.tvPopularDestination.visibility = View.VISIBLE
        binding.rvRecommended.visibility = View.VISIBLE
        binding.rvPopularPlace.visibility = View.VISIBLE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onClickVerticalItem(item: DataItem) {
        val bundle = bundleOf(DetailDestinationFragment.KEY_ID to item.id)
        findNavController().navigate(R.id.action_navigation_home_to_detailDestinationFragment,bundle)

    }

    override fun onClickRecommendedItem(item: DataItem) {
        val bundle = bundleOf(DetailDestinationFragment.KEY_ID to item.id)
        findNavController().navigate(R.id.action_navigation_home_to_detailDestinationFragment,bundle)
    }
}