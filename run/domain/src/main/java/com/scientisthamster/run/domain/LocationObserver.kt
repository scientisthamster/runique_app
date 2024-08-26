package com.scientisthamster.run.domain

import com.scientisthamster.core.domain.location.LocationWithAltitude
import kotlinx.coroutines.flow.Flow

interface LocationObserver {

    fun observeLocation(intervalMillis: Long): Flow<LocationWithAltitude>
}