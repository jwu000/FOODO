package util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class AdapterReviews extends BaseAdapter {

    Context context;
    ArrayList<ReviewAdapterItem> reviews;

    public AdapterReviews(Context context, ArrayList<ReviewAdapterItem> reviews) {
        this.context = context;
        this.reviews = reviews;
    }


    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
