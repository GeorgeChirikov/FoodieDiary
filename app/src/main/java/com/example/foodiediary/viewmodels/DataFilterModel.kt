package com.example.foodiediary.viewmodels

import java.time.LocalDate

class DateFilterModel : DateFilter {
    override fun filterByDateRange(
        map: Map<String, LocalDate>,
        startDate: LocalDate,
        endDate: LocalDate
    ): Map<String, LocalDate> {
        return map.filterValues { date ->
            date.isEqual(startDate)
        }
    }

    override fun filterBeforeDate(map: Map<Any, LocalDate>, beforeDate: LocalDate): Map<Any, LocalDate> {
        return map.filterValues { date ->
            date.isBefore(beforeDate)
        }
    }

    override fun filterAfterDate(map: Map<Any, LocalDate>, afterDate: LocalDate): Map<Any, LocalDate> {
        return map.filterValues { date ->
            date.isAfter(afterDate)
        }
    }
}