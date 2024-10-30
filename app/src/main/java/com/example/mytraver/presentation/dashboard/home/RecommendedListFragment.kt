package com.example.mytraver.presentation.dashboard.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytraver.R
import com.example.mytraver.databinding.FragmentRecomendedListBinding
import com.example.mytraver.domain.model.DataItem
import com.example.mytraver.presentation.StateUser
import com.example.mytraver.presentation.adapter.listener.OnItemVerticalAdapter
import com.example.mytraver.presentation.adapter.RecyclerViewVerticalAdapter
import com.example.mytraver.presentation.dashboard.home.viewmodel.RecommendedFragmentViewModel
import kotlinx.coroutines.launch

class RecommendedListFragment : Fragment(), OnItemVerticalAdapter, SearchView.OnQueryTextListener {
    private var _binding: FragmentRecomendedListBinding? = null
    private val binding get() = _binding!!

    private lateinit var verticalRecommendedAdapter: RecyclerViewVerticalAdapter

    private lateinit var layoutManager: LinearLayoutManager
    private val recommendedViewModel by activityViewModels<RecommendedFragmentViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecomendedListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        loadDataRecommended()
        scrollRecyclerview()

        binding.btnSearchRecommended.setOnQueryTextListener(this)
    }

    private fun initRecyclerView() {
        recommendedViewModel.loadDataMore()
        verticalRecommendedAdapter = RecyclerViewVerticalAdapter(
            listener = this@RecommendedListFragment,
            isRecommended = true
        )
        layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecommended.layoutManager = layoutManager
        binding.rvRecommended.adapter = verticalRecommendedAdapter

    }

    override fun onResume() {
        super.onResume()
        recommendedViewModel.reset()
    }

    private fun loadDataRecommended() {
        viewLifecycleOwner.lifecycleScope.launch {
            recommendedViewModel.listRecommendation.collect { dataState ->
                when (dataState) {
                    is StateUser.Error -> {
                        Toast.makeText(requireContext(), "${dataState.error}", Toast.LENGTH_SHORT)
                            .show()
                    }

                    StateUser.Loading -> {
                    }

                    is StateUser.Success -> {
                        Log.d(
                            "LoadDataRecommendedList", "dataId = ${
                                dataState.data.map { it.id }
                            }} ")
                        verticalRecommendedAdapter.addNewData(dataState.data)

                    }
                }
            }
        }
    }

    private fun scrollRecyclerview() {
        binding.rvRecommended.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                if ((visibleItemCount + firstVisibleItem >= totalItemCount) && firstVisibleItem >= 0) {
                    recommendedViewModel.loadDataMore()
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onClickVerticalItem(item: DataItem) {
        val bundle = bundleOf(DetailDestinationFragment.KEY_ID to item.id)
        binding.btnSearchRecommended.clearFocus()
        binding.btnSearchRecommended.setQuery(null, false)
        findNavController().navigate(
            R.id.action_recommendedListFragment_to_detailDestinationFragment,
            bundle
        )
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        recommendedViewModel.searchListRecommendedByType(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        recommendedViewModel.searchListRecommendedByType(newText)
        return true
    }
}