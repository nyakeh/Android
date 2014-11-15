package uk.co.nyakeh.notepad;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    public static final String APP_RUN_COUNT = "appRunCount";
    TextView readingView;
    TextView appRestartsView;
    int appRunCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appRestartsView = (TextView) findViewById(R.id.applicationRestarts);

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        appRunCount = preferences.getInt(APP_RUN_COUNT, 0);

        if (appRunCount == 0){
            Toast.makeText(this,"YoYoYo", Toast.LENGTH_LONG).show();
        }
        appRunCount++;
        appRestartsView.setText(String.valueOf(appRunCount));

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(APP_RUN_COUNT, appRunCount);
        editor.commit();
    }

    public String getTextFile() {
        return null;
    }

    public void saveTextFile(String content) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
