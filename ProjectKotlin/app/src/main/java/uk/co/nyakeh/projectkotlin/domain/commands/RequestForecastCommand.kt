package uk.co.nyakeh.projectkotlin.domain.commands

import uk.co.nyakeh.projectkotlin.domain.datasource.ForecastProvider
import uk.co.nyakeh.projectkotlin.domain.model.ForecastList

class RequestForecastCommand(private val zipCode: Long, val forecastProvider: ForecastProvider = ForecastProvider()) : Command<ForecastList> {
    companion object {
        val DAYS = 7
    }

    override fun execute(): ForecastList {
        return forecastProvider.requestByZipCode(zipCode, DAYS)
    }
}