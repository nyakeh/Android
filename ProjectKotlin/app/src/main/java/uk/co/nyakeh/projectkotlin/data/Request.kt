package uk.co.nyakeh.projectkotlin.data

import android.util.Log
import java.net.URL

public class Request(val url: String) {

    public fun run() {
        val forecastJsonStr = URL(url).readText()
        Log.d(javaClass.getSimpleName(), forecastJsonStr)
    }

}