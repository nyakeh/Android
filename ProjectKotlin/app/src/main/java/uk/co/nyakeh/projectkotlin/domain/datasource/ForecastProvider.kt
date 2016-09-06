package uk.co.nyakeh.projectkotlin.domain.datasource

import uk.co.nyakeh.projectkotlin.data.db.ForecastDb
import uk.co.nyakeh.projectkotlin.data.server.ForecastServer
import uk.co.nyakeh.projectkotlin.domain.model.ForecastDataSource
import uk.co.nyakeh.projectkotlin.domain.model.ForecastList
import uk.co.nyakeh.projectkotlin.extensions.firstResult

class ForecastProvider(val sources: List<ForecastDataSource> = ForecastProvider.SOURCES) {

    companion object {
        val DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
        val SOURCES = listOf(ForecastDb(), ForecastServer())
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList = sources.firstResult { requestSource(it, days, zipCode) }

    private fun requestSource(source: ForecastDataSource, days: Int, zipCode: Long): ForecastList? {
        val res = source.requestForecastByZipCode(zipCode, todayTimeSpan())
        return if (res != null && res.size() >= days) res else null
    }

    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS
}