package uk.co.nyakeh.projectkotlin.domain.commands

import uk.co.nyakeh.projectkotlin.domain.datasource.ForecastProvider
import uk.co.nyakeh.projectkotlin.domain.model.Forecast

class RequestDayForecastCommand(val id: Long, val forecastProvider: ForecastProvider = ForecastProvider()) : Command<Forecast> {
    override fun execute() = forecastProvider.requestForecast(id)
}