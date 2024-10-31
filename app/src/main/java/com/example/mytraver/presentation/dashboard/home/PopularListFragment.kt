package com.example.mytraver.presentation.dashboard.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytraver.R
import com.example.mytraver.databinding.FragmentPopularListBinding
import com.example.mytraver.domain.model.DataItem
import com.example.mytraver.presentation.StateUser
import com.example.mytraver.presentation.adapter.listener.OnItemVerticalAdapter
import com.example.mytraver.presentation.adapter.RecyclerViewVerticalAdapter
import com.example.mytraver.presentation.dashboard.home.viewmodel.PopularListFragmentViewModel
import kotlinx.coroutines.launch

class PopularListFragment : Fragment(), OnItemVerticalAdapter, OnQueryTextListener {
    private var _binding: FragmentPopularListBinding? = null
    private val binding get() = _binding!!

    private val popularListFragmentViewModel by activityViewModels<PopularListFragmentViewModel>()

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var popularListAdapter: RecyclerViewVerticalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        loadDataPopular()
        scrollRecyclerView()

        binding.btnSearch.setOnQueryTextListener(this@PopularListFragment)
    }

    private fun initRecyclerView() {
        popularListFragmentViewModel.loadDataMore()
        popularListAdapter =
            RecyclerViewVerticalAdapter(listener = this@PopularListFragment, isRecommended = false)
        layoutManager = LinearLayoutManager(requireContext())
        binding.rvPopularPlace.layoutManager = layoutManager
        binding.rvPopularPlace.adapter = popularListAdapter
    }

    private fun loadDataPopular() {
        viewLifecycleOwner.lifecycleScope.launch {
            popularListFragmentViewModel.listPopular.collect {
                when (it) {
                    StateUser.Loading -> {
                        //shimmer
                    }

                    is StateUser.Success -> {
                        popularListAdapter.addNewData(it.data)
                    }

                    is StateUser.Error -> {
                        //toast error
                        Toast.makeText(requireContext(), "${it.error}", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

    private fun scrollRecyclerView() {
        binding.rvPopularPlace.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                if ((visibleItemCount + firstVisibleItem >= totalItemCount) && firstVisibleItem >= 0) {
                    popularListFragmentViewModel.loadDataMore()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        popularListFragmentViewModel.reset()
    }

    override fun onResume() {
        super.onResume()
        popularListFragmentViewModel.reset()
    }

    override fun onClickVerticalItem(item: DataItem) {
        val bundle = bundleOf(DetailDestinationFragment.KEY_ID to item.id)
        binding.btnSearch.clearFocus()
        findNavController().navigate(
            R.id.action_popularListFragment_to_detailDestinationFragment,
            bundle
        )
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        popularListFragmentViewModel.searchListRecommendedByType(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        popularListFragmentViewModel.searchListRecommendedByType(newText)
        return true
    }
}