package uk.co.nyakeh.projectkotlin.domain.mappers

import uk.co.nyakeh.projectkotlin.data.server.Forecast
import uk.co.nyakeh.projectkotlin.data.server.ForecastResult
import uk.co.nyakeh.projectkotlin.domain.model.ForecastList
import java.text.DateFormat
import java.util.*
import uk.co.nyakeh.projectkotlin.domain.model.Forecast as ModelForecast

class ServerDataMapper {
    fun convertToDomain(zipCode: Long, forecast: ForecastResult) = with(forecast) {
        ForecastList(zipCode, forecast.city.name, forecast.city.country, convertForecastListToDomain(forecast.list))
    }

    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> {
        return list.map { convertForecastItemToDomain(it) }
    }

    private fun convertForecastItemToDomain(forecast: Forecast) = with(forecast)  {
        ModelForecast(-1, dt * 1000, weather[0].description, temp.max.toInt(), temp.min.toInt(), generateIconUrl(weather[0].icon))
    }

    private fun convertDate(date: Long): String {
        val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return dateFormat.format(date * 1000)
    }

    private fun generateIconUrl(iconCode: String) = "http://openweathermap.org/img/w/$iconCode.png"
}