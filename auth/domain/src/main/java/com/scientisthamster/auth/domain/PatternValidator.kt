package com.scientisthamster.auth.domain

interface PatternValidator {

    fun matches(value: String): Boolean
}