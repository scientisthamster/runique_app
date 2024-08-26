package com.scientisthamster.run.location.di

import com.scientisthamster.run.domain.LocationObserver
import com.scientisthamster.run.location.AndroidLocationObserver
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val locationModule = module {
    singleOf(::AndroidLocationObserver).bind<LocationObserver>()
}