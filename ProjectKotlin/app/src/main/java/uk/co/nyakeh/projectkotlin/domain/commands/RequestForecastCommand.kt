package uk.co.nyakeh.projectkotlin.domain.commands

import uk.co.nyakeh.projectkotlin.data.ForecastRequest
import uk.co.nyakeh.projectkotlin.domain.mappers.ForecastDataMapper
import uk.co.nyakeh.projectkotlin.domain.model.ForecastList

class RequestForecastCommand(private val zipCode: String) : Command<ForecastList> {
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
    }
}