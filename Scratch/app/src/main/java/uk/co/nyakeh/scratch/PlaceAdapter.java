package uk.co.nyakeh.scratch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Nyakeh on 05/11/2014.
 */
public class PlaceAdapter extends ArrayAdapter<Place> {
    Context mContext;
    int mLayoutResourceId;
    Place mData[] = null;

    public PlaceAdapter(Context context, int resource, Place[] data) {
        super(context, resource, data);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mData = data;
    }

    @Override
    public Place getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        row = inflater.inflate(mLayoutResourceId, parent, false);

        TextView nameView = (TextView) row.findViewById(R.id.nameTextView);
        TextView zipView = (TextView) row.findViewById(R.id.zipcodeTextView);
        ImageView imageView = (ImageView) row.findViewById(R.id.imageView);

        Place place = mData[position];

        nameView.setText(place.mNameOfPlace);
        zipView.setText(String.valueOf(place.mZipCode));

        int resId = mContext.getResources().getIdentifier(place.mNameOfImage,"drawable", mContext.getPackageName());
        imageView.setImageResource(resId);

        return row;
    }
}
