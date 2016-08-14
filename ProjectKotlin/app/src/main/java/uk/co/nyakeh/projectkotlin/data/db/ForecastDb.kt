package uk.co.nyakeh.projectkotlin.data.db

import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import uk.co.nyakeh.projectkotlin.domain.model.ForecastList
import uk.co.nyakeh.projectkotlin.extensions.clear
import uk.co.nyakeh.projectkotlin.extensions.parseList
import uk.co.nyakeh.projectkotlin.extensions.parseOpt
import uk.co.nyakeh.projectkotlin.extensions.toVarargArray
import java.util.*

class ForecastDb(val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance, val dataMapper: DbDataMapper = DbDataMapper()) {

    fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {
        val dailyRequest = "${DayForecastTable.CITY_ID} = ? AND ${DayForecastTable.DATE} >= ?"
        val dailyForecast = select(DayForecastTable.NAME)
                .whereSimple(dailyRequest, zipCode.toString(), date.toString())
                .parseList { DayForecast(HashMap(it)) }

        val city = select(CityForecastTable.NAME)
                .whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
                .parseOpt { CityForecast(HashMap(it), dailyForecast) }

        if (city != null) dataMapper.convertToDomain(city) else null
    }

    fun saveForecast(forecast: ForecastList) = forecastDbHelper.use {
        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)

        with(dataMapper.convertFromDomain(forecast)) {
            insert(CityForecastTable.NAME, *map.toVarargArray())
            dailyForecast.forEach { insert(DayForecastTable.NAME, *it.map.toVarargArray()) }
        }
    }
}