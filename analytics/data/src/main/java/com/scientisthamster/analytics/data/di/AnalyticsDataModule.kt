package com.scientisthamster.analytics.data.di

import com.scientisthamster.analytics.data.RoomAnalyticsRepository
import com.scientisthamster.analytics.domain.AnalyticsRepository
import com.scientisthamster.core.database.RunDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val analyticsDataModule = module {
    singleOf(::RoomAnalyticsRepository).bind<AnalyticsRepository>()
    single {
        get<RunDatabase>().analyticsDao
    }
}