package uk.co.nyakeh.crush;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
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
    private static final String TAG_DATA_LOADER = "dataLoader";
    private static final String TAG_SPLASH_SCREEN = "splashScreen";

    private DataLoaderFragment mDataLoaderFragment;
    private SplashScreenFragment mSplashScreenFragment;

    @Override
    public void onCompletion() {
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction()
                .add(R.id.container, new PlaceholderFragment())
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
            fm.beginTransaction().add(android.R.id.content, mSplashScreenFragment, TAG_SPLASH_SCREEN).commit();
        }
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                    EditText ET_email = (EditText) rootView.findViewById(R.id.fld_account_email);
                    EditText ET_password = (EditText) rootView.findViewById(R.id.fld_account_password);
                    String email = ET_email.getText().toString();
                    String password = ET_password.getText().toString();

                    Toast toast = Toast.makeText(v.getContext(), "Nice Try " + email + " : " + password, Toast.LENGTH_LONG);
                    toast.show();

                    final OAuth oauth = new OAuth(getActivity());
                    oauth.initialize("BpuDpu-omlcZqSRanOmIud3W7Ng");
                    JSONObject options = new JSONObject();
                    try {
                        options.put("username", email);
                        options.put("password", password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    OAuthCallback callback = new OAuthCallback() {
                        @Override
                        public void onFinished(OAuthData data) {
                            if (!data.status.equals("success")) {
                                Toast toast = Toast.makeText(rootView.getContext(), "Fail: " + data.error, Toast.LENGTH_LONG);
                                toast.show();
                            }
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

                                        Toast toast = Toast.makeText(rootView.getContext(), "Hello Inside" +result.getString("name") + "     " + result, Toast.LENGTH_LONG);
                                        toast.show();
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
                    oauth.popup("facebook", options, callback);

                }
            });

            return rootView;
        }
    }
}
