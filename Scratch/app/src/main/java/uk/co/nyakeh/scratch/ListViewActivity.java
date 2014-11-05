package uk.co.nyakeh.scratch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class ListViewActivity extends Activity {
    Place[] myPlacesArray = new Place[]{
            new Place("Art House", 78701 , "launcher_logo" ,"This place is tasteful"),
            new Place("Bike Shop", 78702, "ic_launcher","Cool bikes"),
            new Place("Camera Fix", 78702, "launcher_logo","These guys always rip me off"),
            new Place("YETspace", 78702, "launcher_logo", "I LOVE this place"),
            new Place("Secret Space Pad", 94103, "ic_launcher","Not very secret, are they?"),
            new Place("Taylor’s Tailor", 60610, "launcher_logo" , "Looking good.."),
            new Place("Boathouse", 78701, "ic_launcher" ,"That place is full of pirates!"),
            new Place("Not Apple Store", 78702, "ic_launcher", "Android rules!"),
            new Place("Tool Battleground", 78702, "ic_launcher", "That place is dangerous"),
            new Place("Travelpediocity", 78702, "ic_launcher" ,"This is where i booked my summer trip"),
            new Place("UFO Pick-a-part", 90210, "launcher_logo","Out of this world stuff here."),
            new Place("Spawrk’s House", 99999, "launcher_logo", "The music is always so good"),
    };

    String[] myStringArray = new String[]{
            "Art House",
            "Bike Shop",
            "Camera Fix",
            "YETspace",
            "Secret Space Pad",
            "Taylor’s Tailor",
            "Boathouse",
            "Not Apple Store",
            "Tool Battleground",
            "Travelpediocity",
            "UFO Pick-a-part",
            "Spawrk’s House",
    };

    private ListView mListView;
    private PlaceAdapter mPlaceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        mListView = (ListView) findViewById(R.id.myListView);

        mPlaceAdapter = new PlaceAdapter(getApplicationContext(),R.layout.row,myPlacesArray);

        if(mListView != null){
            mListView.setAdapter(mPlaceAdapter);
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.v("PLACE", myPlacesArray[position].mNameOfPlace);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_view, menu);
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
