package com.scientisthamster.analytics.domain

interface AnalyticsRepository {

    suspend fun getAnalyticsMetrics(): AnalyticsMetrics
}