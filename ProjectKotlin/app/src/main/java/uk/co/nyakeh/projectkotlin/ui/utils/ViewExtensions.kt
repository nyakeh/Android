package uk.co.nyakeh.projectkotlin.ui.utils

import android.content.Context
import android.view.View

val View.ctx: Context
    get() = getContext()