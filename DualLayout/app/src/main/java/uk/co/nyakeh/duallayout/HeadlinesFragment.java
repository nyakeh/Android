package uk.co.nyakeh.duallayout;



import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HeadlinesFragment extends ListFragment {


    public HeadlinesFragment() {
        // Required empty public constructor
    }
    OnHeadlineSelectedListener callback;

    public interface OnHeadlineSelectedListener{
        public void onArticleSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException exception){
            throw new ClassCastException(activity.toString() + "to do: OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int layout = android.R.layout.simple_list_item_activated_1;
        String[] data = Ipsum.Headlines;

        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, data));
    }

    @Override
    public void onStart() {
        super.onStart();

        Fragment fragment = getFragmentManager().findFragmentById(R.id.article_fragment);
        ListView listView = getListView();

        if (fragment != null && listView != null){
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        callback.onArticleSelected(position);

        l.setItemChecked(position,true);
    }
}
