package uk.co.nyakeh.projectkotlin.data.server

import uk.co.nyakeh.projectkotlin.data.db.ForecastDb
import uk.co.nyakeh.projectkotlin.domain.mappers.ServerDataMapper
import uk.co.nyakeh.projectkotlin.domain.model.ForecastDataSource
import uk.co.nyakeh.projectkotlin.domain.model.ForecastList

class ForecastServer(val dataMapper: ServerDataMapper = ServerDataMapper(), val forecastDb: ForecastDb = ForecastDb()) : ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = ForecastByZipCodeRequest(zipCode).execute()
        val converted = dataMapper.convertToDomain(zipCode, result)
        forecastDb.saveForecast(converted)
        return forecastDb.requestForecastByZipCode(zipCode, date)
    }
}