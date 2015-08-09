package uk.co.nyakeh.projectkotlin.domain.mappers

import uk.co.nyakeh.projectkotlin.data.Forecast
import uk.co.nyakeh.projectkotlin.data.ForecastResult
import uk.co.nyakeh.projectkotlin.domain.model
import uk.co.nyakeh.projectkotlin.domain.model.ForecastList
import java.text.DateFormat
import java.util.Locale
import uk.co.nyakeh.projectkotlin.data.Forecast as DataForecast

class ForecastDataMapper {
    fun convertFromDataModel(forecast: ForecastResult): ForecastList {
        return ForecastList(forecast.city.name, forecast.city.country, convertForecastToDomain(forecast.list))
    }

    private fun convertForecastToDomain(list: List<Forecast>): List<model.Forecast> {
        return list map { convertForecastItemToDomain(it) }
    }

    private fun convertForecastItemToDomain(forecast: Forecast): model.Forecast {
        return model.Forecast(convertDate(forecast.dt), forecast.weather[0].description, forecast.temp.max.toInt(), forecast.temp.min.toInt(), generateIconUrl(forecast .weather[0].icon))
    }

    private fun convertDate(date: Long): String {
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return df.format(date * 1000)
    }

    private fun generateIconUrl(iconCode: String) = "http://openweathermap.org/img/w/$iconCode.png"
}