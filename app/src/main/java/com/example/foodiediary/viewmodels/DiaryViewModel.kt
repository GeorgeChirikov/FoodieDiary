package com.example.foodiediary.viewmodels


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiediary.models.data.database.AppDatabase
import com.example.foodiediary.models.data.entity.Added
import com.example.foodiediary.models.data.repository.AddedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
class DiaryViewModel(context: Context) : ViewModel() {
    private val addedRepository = AddedRepository(AppDatabase.getInstance(context).addedDao())

    // Selected date state
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    // All data state
    private val _allData = MutableStateFlow<List<Added>>(emptyList())
    val allData: StateFlow<List<Added>> = _allData.asStateFlow()

    // Filtered data state
    private val _filteredData = MutableStateFlow<List<Added>>(emptyList())
    val filteredData: StateFlow<List<Added>> = _filteredData.asStateFlow()

    init {
        // Collect all the items from the database
        viewModelScope.launch {
            addedRepository.getAllAdded().collectLatest { addedList ->
                _allData.value = addedList
                filterDataByDate(_selectedDate.value, addedList)
            }
        }
    }

    fun updateSelectedDate(newDate: LocalDate) {
        _selectedDate.value = newDate
        filterDataByDate(newDate, _allData.value)
    }

    private fun filterDataByDate(date: LocalDate, addedList: List<Added>) {
        viewModelScope.launch {
            _filteredData.value = addedList.filter { added ->
                // Convert the timestamp (milliseconds) to LocalDate
                val dateItem = Instant.ofEpochMilli(added.timeStamp)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()

                dateItem == date
            }
        }
    }
}