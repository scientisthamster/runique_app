package com.scientisthamster.auth.presentation.intro

sealed interface IntroAction {

    data object OnSignInClick : IntroAction

    data object OnSingUpClick : IntroAction
}