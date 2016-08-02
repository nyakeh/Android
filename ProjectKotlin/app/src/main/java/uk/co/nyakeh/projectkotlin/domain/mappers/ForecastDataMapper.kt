package uk.co.nyakeh.projectkotlin.domain.mappers

import uk.co.nyakeh.projectkotlin.data.Forecast
import uk.co.nyakeh.projectkotlin.data.ForecastResult
import uk.co.nyakeh.projectkotlin.domain.model.ForecastList
import java.text.DateFormat
import java.util.*
import uk.co.nyakeh.projectkotlin.domain.model.Forecast as ModelForecast

class ForecastDataMapper {
    fun convertFromDataModel(forecast: ForecastResult): ForecastList {
        return ForecastList(forecast.city.name, forecast.city.country, convertForecastListToDomain(forecast.list))
    }

    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> {
        return list.map { convertForecastItemToDomain(it) }
    }

    private fun convertForecastItemToDomain(forecast: Forecast): ModelForecast {
        return ModelForecast(convertDate(forecast.dt), forecast.weather[0].description,
                forecast.temp.max.toInt(), forecast.temp.min.toInt())
    }

    private fun convertDate(date: Long): String {
        val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return dateFormat.format(date * 1000)
    }
}