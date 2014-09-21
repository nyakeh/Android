package uk.co.nyakeh.crush;



import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DataLoaderFragment extends Fragment {

    /**
     * Classes wishing to be notified of loading progress/completion
     * implement this.
     */
    public interface ProgressListener {
        /**
         * Notifies that the task has completed
         */
        public void onCompletion();

        /**
         * Notifies of progress
         * @param value int value from 0-100
         */
        public void onProgressUpdate(int value);
    }

    private ProgressListener mProgressListener;
    private Double mResult = Double.NaN;
    private LoadingTask mTask;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Keep this Fragment around even during config changes
        setRetainInstance(true);
    }

    public Double getResult() {
        return mResult;
    }

    /**
     * Returns true if a result has already been calculated
     * @return true if a result has already been calculated
     * @see #getResult()
     */
    public boolean hasResult() {
        return !Double.isNaN(mResult);
    }

    /**
     * Removes the ProgressListener
     * @see #setProgressListener(ProgressListener)
     */
    public void removeProgressListener() {
        mProgressListener = null;
    }

    /**
     * Sets the ProgressListener to be notified of updates
     * @param listener ProgressListener to notify
     * @see #removeProgressListener()
     */
    public void setProgressListener(ProgressListener listener) {
        mProgressListener = listener;
    }

    /**
     * Starts loading the data
     */
    public void startLoading() {
        mTask = new LoadingTask();
        mTask.execute();
    }

    private class LoadingTask extends AsyncTask<Void, Integer, Double>
    {
        @Override
        protected Double doInBackground(Void... params) {
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // This is returned & ignored so that 'onPostExecute' will execute calling 'onCompletion' on the process listener
            return 123456789d;
        }

        @Override
        protected void onPostExecute(Double result) {
            mTask = null;
            if (mProgressListener != null) {
                mProgressListener.onCompletion();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (mProgressListener != null) {
                mProgressListener.onProgressUpdate(values[0]);
            }
        }
    }
}
