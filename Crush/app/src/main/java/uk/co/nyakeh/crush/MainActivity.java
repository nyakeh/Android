package uk.co.nyakeh.crush;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;

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

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
