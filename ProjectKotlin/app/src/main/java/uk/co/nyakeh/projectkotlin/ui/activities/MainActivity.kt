package uk.co.nyakeh.projectkotlin.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.async
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread
import uk.co.nyakeh.projectkotlin.R
import uk.co.nyakeh.projectkotlin.domain.commands.RequestForecastCommand
import uk.co.nyakeh.projectkotlin.ui.adapters.ForecastListAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forecastList.layoutManager = LinearLayoutManager(this)

        async() {
            val result = RequestForecastCommand(94043).execute()
            uiThread {
                val adapter = ForecastListAdapter(result) {
                    startActivity<DetailActivity>(DetailActivity.ID to it.id, DetailActivity.CITY_NAME to result.city)
                }
                forecastList.adapter = adapter
                title = "${result.city} (${result.country})"
            }
        }
    }
}