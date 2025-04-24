package com.example.foodiediary.viewmodels

import java.time.LocalDate

interface DateFilter {

    /**
     * Filters the provided map by dates within a specific range.
     *
     * @param map The map to filter.
     * @param startDate The start date of the range (inclusive).
     * @param endDate The end date of the range (inclusive).
     * @return A new map containing only the entries with dates within the specified range.
     */
    fun filterByDateRange(
        map: Map<String, LocalDate>,
        startDate: LocalDate,
        endDate: LocalDate
    ): Map<String, LocalDate>

    /**
     * Filters the provided map by dates before a specific date.
     *
     * @param map The map to filter.
     * @param beforeDate The date before which entries should be included.
     * @return A new map containing only the entries with dates before the specified date.
     */
    fun filterBeforeDate(map: Map<Any, LocalDate>, beforeDate: LocalDate): Map<Any, LocalDate>

    /**
     * Filters the provided map by dates after a specific date.
     *
     * @param map The map to filter.
     * @param afterDate The date after which entries should be included.
     * @return A new map containing only the entries with dates after the specified date.
     */
    fun filterAfterDate(map: Map<Any, LocalDate>, afterDate: LocalDate): Map<Any, LocalDate>
}