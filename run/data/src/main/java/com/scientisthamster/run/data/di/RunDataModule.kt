package com.scientisthamster.run.data.di

import com.scientisthamster.run.data.CreateRunWorker
import com.scientisthamster.run.data.DeleteRunWorker
import com.scientisthamster.run.data.FetchRunsWorker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::CreateRunWorker)
    workerOf(::FetchRunsWorker)
    workerOf(::DeleteRunWorker)
}