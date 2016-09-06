package uk.co.nyakeh.projectkotlin.domain.datasource

import uk.co.nyakeh.projectkotlin.data.db.ForecastDb
import uk.co.nyakeh.projectkotlin.data.server.ForecastServer
import uk.co.nyakeh.projectkotlin.domain.model.Forecast
import uk.co.nyakeh.projectkotlin.domain.model.ForecastDataSource
import uk.co.nyakeh.projectkotlin.domain.model.ForecastList
import uk.co.nyakeh.projectkotlin.extensions.firstResult

class ForecastProvider(val sources: List<ForecastDataSource> = ForecastProvider.SOURCES) {

    companion object {
        val DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
        val SOURCES = listOf(ForecastDb(), ForecastServer())
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList = requestToSources {
        val res = it.requestForecastByZipCode(zipCode, todayTimeSpan())
        if (res != null && res.size() >= days) res else null
    }

    fun requestForecast(id: Long): Forecast = requestToSources {
        it.requestDayForecast(id)
    }

    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS

    private fun <T : Any> requestToSources(f: (ForecastDataSource) -> T?): T = sources.firstResult { f(it) }
}