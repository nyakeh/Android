package uk.co.nyakeh.projectkotlin.ui

import android.app.Application
import uk.co.nyakeh.projectkotlin.ui.utils.DelegatesExt

class App : Application() {

    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
