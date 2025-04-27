package com.example.foodiediary.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {
    private val placeholderData = mapOf(
        "Item1" to LocalDate.of(2025, 4, 24),
        "Item2" to LocalDate.of(2025, 4, 24),
        "Item3" to LocalDate.of(2025, 4, 10),
        "Item4" to LocalDate.of(2025, 4, 10),
        "Item5" to LocalDate.of(2025, 4, 30)
    )

    private val dateFilter = DateFilterModel()

    // Selected date state
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    // Filtered data state
    private val _filteredData = MutableStateFlow<Map<String, LocalDate>>(emptyMap())
    val filteredData: StateFlow<Map<String, LocalDate>> = _filteredData

    init {
        // Initial filtering with today's date
        filterDataByDate(_selectedDate.value)
    }

    fun updateSelectedDate(newDate: LocalDate) {
        _selectedDate.value = newDate
        filterDataByDate(newDate)
    }

    private fun filterDataByDate(date: LocalDate) {
        viewModelScope.launch {
            val filteredMap = dateFilter.filterByDateRange(placeholderData, date, date)
            _filteredData.value = filteredMap
        }
    }
}