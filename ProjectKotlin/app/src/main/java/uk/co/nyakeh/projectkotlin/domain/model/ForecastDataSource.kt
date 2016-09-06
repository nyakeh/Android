package uk.co.nyakeh.projectkotlin.domain.model

interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?
}