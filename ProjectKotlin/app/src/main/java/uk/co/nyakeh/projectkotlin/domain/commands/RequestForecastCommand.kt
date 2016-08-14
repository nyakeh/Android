package uk.co.nyakeh.projectkotlin.domain.commands

import uk.co.nyakeh.projectkotlin.data.server.ForecastRequest
import uk.co.nyakeh.projectkotlin.domain.mappers.ForecastDataMapper
import uk.co.nyakeh.projectkotlin.domain.model.ForecastList

class RequestForecastCommand(private val zipCode: Long) : Command<ForecastList> {
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(zipCode, forecastRequest.execute())
    }
}