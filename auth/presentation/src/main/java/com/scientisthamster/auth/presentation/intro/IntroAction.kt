package com.scientisthamster.auth.presentation.intro

internal sealed interface IntroAction {

    data object OnSignInClick : IntroAction

    data object OnSingUpClick : IntroAction
}