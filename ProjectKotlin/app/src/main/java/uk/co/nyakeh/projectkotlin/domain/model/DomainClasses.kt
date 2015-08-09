package uk.co.nyakeh.projectkotlin.domain.model

data class ForecastList(val city: String, val country: String, val dailyForecast: List<Forecast>) {

    public fun get(position: Int): Forecast = dailyForecast[position]

    public fun size(): Int {
        return dailyForecast.size()
    }
}

data class Forecast(val date: String, val description: String, val high: Int, val low: Int)