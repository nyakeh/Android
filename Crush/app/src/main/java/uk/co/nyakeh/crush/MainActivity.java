package uk.co.nyakeh.crush;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import io.oauth.OAuth;
import io.oauth.OAuthCallback;
import io.oauth.OAuthData;
import io.oauth.OAuthRequest;

public class MainActivity extends Activity implements DataLoaderFragment.ProgressListener {
    private String[] mNavDrawerTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle = "Crush";
    private CharSequence mDrawerTitle = "Crush";

    private static final String TAG_DATA_LOADER = "dataLoader";
    private static final String TAG_SPLASH_SCREEN = "splashScreen";

    private DataLoaderFragment mDataLoaderFragment;
    private SplashScreenFragment mSplashScreenFragment;

    public MainActivity() {
    }

    @Override
    public void onCompletion() {
        PlaceholderFragment placeholderFragment = new PlaceholderFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.container, placeholderFragment)
                .remove(mSplashScreenFragment)
                .commit();
        mDataLoaderFragment = null;
    }

    @Override
    public void onProgressUpdate(int progress) {
        mSplashScreenFragment.setProgress(progress);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fm = getFragmentManager();
        mDataLoaderFragment = (DataLoaderFragment) fm.findFragmentByTag(TAG_DATA_LOADER);
        if (mDataLoaderFragment == null) {
            mDataLoaderFragment = new DataLoaderFragment();
            mDataLoaderFragment.setProgressListener(this);
            mDataLoaderFragment.startLoading();
            fm.beginTransaction().add(mDataLoaderFragment, TAG_DATA_LOADER).commit();
        } else {
            if (checkCompletionStatus()) {
                return;
            }
        }

        // Show loading fragment
        mSplashScreenFragment = (SplashScreenFragment) fm.findFragmentByTag(TAG_SPLASH_SCREEN);
        if (mSplashScreenFragment == null) {
            mSplashScreenFragment = new SplashScreenFragment();
            fm.beginTransaction().add(R.id.container, mSplashScreenFragment, TAG_SPLASH_SCREEN).commit();
        }

        mNavDrawerTitles = getResources().getStringArray(R.array.nav_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mNavDrawerTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mDataLoaderFragment != null) {
            checkCompletionStatus();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mDataLoaderFragment != null) {
            mDataLoaderFragment.removeProgressListener();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Checks if data is done loading, if it is, the result is handled
     * @return true if data is done loading
     */
    private boolean checkCompletionStatus() {
        if (mDataLoaderFragment.hasResult()) {
            onCompletion();
            FragmentManager fm = getFragmentManager();
            mSplashScreenFragment = (SplashScreenFragment) fm.findFragmentByTag(TAG_SPLASH_SCREEN);
            if (mSplashScreenFragment != null) {
                fm.beginTransaction().remove(mSplashScreenFragment). commit();
            }
            return true;
        }
        mDataLoaderFragment.setProgressListener(this);
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar item click
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Create default empty 'homefragment'
        Fragment fragment = new HomeFragment();
        if(position == 1) { // If AddCrush go to specific fragment
            fragment = new AddCrushFragment();
        } else {
            Bundle args = new Bundle();
            args.putInt(HomeFragment.ARG_PLANET_NUMBER, position);
            fragment.setArguments(args);
        }
        // Insert fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mNavDrawerTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        Button loginBtn;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            loginBtn = (Button) rootView.findViewById(R.id.btn_login);
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final OAuth oauth = new OAuth(getActivity());
                    oauth.initialize("BpuDpu-omlcZqSRanOmIud3W7Ng");

                    OAuthCallback callback = new OAuthCallback() {
                        @Override
                        public void onFinished(OAuthData data) {
                            if (!data.status.equals("success")) {
                                Toast toast = Toast.makeText(rootView.getContext(), "Fail: " + data.error, Toast.LENGTH_LONG);
                                toast.show();
                            }
                            //TODO change request to something async
                            // Let's skip the NetworkOnMainThreadException for the purpose of this sample.
                            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

                            data.http(data.provider.equals("facebook") ? "/me" : "/1.1/account/verify_credentials.json", new OAuthRequest() {
                                private URL url;
                                private URLConnection con;

                                @Override
                                public void onSetURL(String _url) {
                                    try {
                                        url = new URL(_url);
                                        con = url.openConnection();
                                    } catch (Exception e) { e.printStackTrace(); }
                                }

                                @Override
                                public void onSetHeader(String header, String value) {
                                    con.addRequestProperty(header, value);
                                }

                                @Override
                                public void onReady() {
                                    try {
                                        BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                        StringBuilder total = new StringBuilder();
                                        String line;
                                        while ((line = r.readLine()) != null) {
                                            total.append(line);
                                        }
                                        JSONObject result = new JSONObject(total.toString());

                                        Toast toast = Toast.makeText(rootView.getContext(), "Hello " +result.getString("name"), Toast.LENGTH_LONG);
                                        toast.show();

                                        // TODO Store returned fb data
                                        //{"id":"975569925793885","first_name":"Nyakeh","timezone":1,"email":"nyakeh@live.co.uk","verified":true,"name":"Nyakeh Rogers","locale":"en_US","link":"https:\/\/www.facebook.com\/app_scoped_user_id\/975569925793885\/","last_name":"Rogers","gender":"male","updated_time":"2014-07-15T19:46:12+0000"}
                                    } catch (Exception e) { e.printStackTrace(); }
                                }

                                @Override
                                public void onError(String message) {
                                    Toast toast = Toast.makeText(rootView.getContext(), "Error " +message, Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            });
                        }
                    };
                    oauth.popup("facebook", callback);
                }
            });

            return rootView;
        }
    }
}
