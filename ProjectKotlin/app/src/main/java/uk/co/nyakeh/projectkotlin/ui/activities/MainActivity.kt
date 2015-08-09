package uk.co.nyakeh.projectkotlin.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import org.jetbrains.anko.async
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread
import uk.co.nyakeh.projectkotlin.R
import uk.co.nyakeh.projectkotlin.domain.commands.RequestForecastCommand
import uk.co.nyakeh.projectkotlin.ui.adapters.ForecastListAdapter

public class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val forecastList: RecyclerView = find(R.id.forecast_list)
        forecastList.setLayoutManager(LinearLayoutManager(this))
        //http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric&cnt=7&q=94043
        async {
            val result = RequestForecastCommand("94043").execute()
            uiThread {
                forecastList.setAdapter(ForecastListAdapter(result))
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.getItemId()
        if (id == R.id.action_settings) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}