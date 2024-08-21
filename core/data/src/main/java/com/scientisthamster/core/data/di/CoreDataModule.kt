package com.scientisthamster.core.data.di

import com.scientisthamster.core.data.networking.HttpClientFactory
import org.koin.dsl.module

val coreDataModule = module {
    single { HttpClientFactory().build() }
}