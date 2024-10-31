package com.example.mytraver.presentation.dashboard.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytraver.domain.model.DataItem
import com.example.mytraver.domain.usecase.DestinationUseCase
import com.example.mytraver.presentation.StateUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularListFragmentViewModel @Inject constructor(private val useCaseDestination: DestinationUseCase) :
    ViewModel() {
    private val _listPopular: MutableStateFlow<StateUser<List<DataItem>>> =
        MutableStateFlow(StateUser.Loading)
    val listPopular: StateFlow<StateUser<List<DataItem>>> = _listPopular.asStateFlow()
    private val dataItems = mutableListOf<DataItem>()

    private var currentPage = 1
    private var totalPage = 0
    private var searchQuery: String? = null
    private var hasReached = false


    fun loadDataMore() {
        Log.d("currentPage loadDataMore", "$currentPage")
        if (hasReached || (totalPage != 0 && currentPage > totalPage)) return

        viewModelScope.launch {
            hasReached = true
            _listPopular.value = StateUser.Loading
            try {
                val response = useCaseDestination.getListPopular(currentPage, query =searchQuery)
                dataItems.addAll(response.dataItem)
                _listPopular.value = StateUser.Success(dataItems)
                currentPage = response.page + 1
                totalPage = response.totalPage
            } catch (e: Exception) {
                Log.d("Error Load List Recommended", "${e.message}")
                _listPopular.value = StateUser.Error(e.message.toString())
            } finally {
                hasReached = false
            }
        }
    }

    fun searchListRecommendedByType(query: String?) {
        currentPage = 1
        searchQuery = query
        totalPage = 0
        dataItems.clear()
        loadDataMore()
    }

    fun reset() {
        currentPage = 1
        searchQuery = null
        totalPage=0
        dataItems.clear()
    }
}