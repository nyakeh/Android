package uk.co.nyakeh.projectkotlin.extensions

import android.content.Context
import android.view.View

val View.ctx: Context
    get() = context