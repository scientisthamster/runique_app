package com.scientisthamster.auth.data

import android.util.Patterns
import com.scientisthamster.auth.domain.PatternValidator

internal object EmailPatternValidator : PatternValidator {

    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}