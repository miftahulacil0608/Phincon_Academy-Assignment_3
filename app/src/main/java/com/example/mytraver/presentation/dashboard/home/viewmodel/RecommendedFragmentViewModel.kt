package com.example.mytraver.presentation.dashboard.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytraver.domain.model.DataItem
import com.example.mytraver.domain.model.ListRecommendedResponse
import com.example.mytraver.domain.usecase.DestinationUseCase
import com.example.mytraver.domain.usecase.UserSettingUseCase
import com.example.mytraver.presentation.StateUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendedFragmentViewModel @Inject constructor(
    private val useCaseDestinationUseCase: DestinationUseCase,
    private val userSettingUseCase: UserSettingUseCase
) : ViewModel() {

    private val _listRecommendation: MutableStateFlow<StateUser<List<DataItem>>> =
        MutableStateFlow(StateUser.Loading)
    val listRecommendation: StateFlow<StateUser<List<DataItem>>> =
        _listRecommendation.asStateFlow()
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
            _listRecommendation.value = StateUser.Loading
            try {
                val type = userSettingUseCase.getUserData().first().preferences
                val response = useCaseDestinationUseCase.getListRecommendedByType(
                    currentPage,
                    type,
                    query = searchQuery
                )
                dataItems.addAll(response.dataItem)
                _listRecommendation.value = StateUser.Success(dataItems)
                currentPage = response.page + 1
                totalPage = response.totalPage
            } catch (e: Exception) {
                Log.d("Error Load List Recommended", "${e.message}")
                _listRecommendation.value = StateUser.Error(e.message.toString())
            } finally {
                hasReached = false
            }
        }
    }

    fun searchListRecommendedByType(query: String?) {
        currentPage = 1
        searchQuery = query
        dataItems.clear()
        loadDataMore()
    }

    fun reset() {
        currentPage = 1
        searchQuery = null
        dataItems.clear()
        loadDataMore()
    }
}


