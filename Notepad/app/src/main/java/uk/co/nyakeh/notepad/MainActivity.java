package uk.co.nyakeh.notepad;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends Activity {

    public static final String APP_RUN_COUNT = "appRunCount";
    TextView readingView;
    TextView appRestartsView;
    int appRunCount;
    private static final String DATA_FILE = "my_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appRestartsView = (TextView) findViewById(R.id.applicationRestarts);
        readingView = (TextView) findViewById(R.id.note);
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        appRunCount = preferences.getInt(APP_RUN_COUNT, 0);

        if (appRunCount == 0){
            Toast.makeText(this,"Welcome n00b", Toast.LENGTH_LONG).show();
        }
        appRunCount++;
        appRestartsView.setText(String.valueOf(appRunCount));

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(APP_RUN_COUNT, appRunCount);
        editor.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveTextFile(readingView.getText().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        readingView.setText(getTextFile());
    }

    public String getTextFile() {
        FileInputStream fileInputStream = null;
        String fileData = null;

        try{
            fileInputStream = openFileInput(DATA_FILE);
            int size = fileInputStream.available();
            byte[] buffer = new byte[size];
            fileInputStream.read(buffer);
            fileInputStream.close();
            fileData = new String(buffer,"UTF-8");
        }catch(FileNotFoundException e) {
            Log.e("FILE", "Couldn't find that file");
            e.printStackTrace();
        }catch(Exception e) {
            Log.e("FILE", "Error");
            e.printStackTrace();
        }finally {
            try {
                if (fileInputStream != null){
                    fileInputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return fileData;
    }

    public void saveTextFile(String content) {
        FileOutputStream fileOutputStream = null;
        try{
            fileOutputStream = openFileOutput(DATA_FILE, Context.MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
        }catch(FileNotFoundException e) {
            Log.e("FILE", "Couldn't find that file");
            e.printStackTrace();
        }catch(IOException e) {
            Log.e("FILE", "IO Error");
            e.printStackTrace();
        }finally {
            try {
            if (fileOutputStream != null){
                fileOutputStream.close();
            }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
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
