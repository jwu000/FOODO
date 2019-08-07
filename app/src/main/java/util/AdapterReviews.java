package util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.foodo.R;

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
        ViewHolder viewHolder;

        if (view == null) {
            view = View.inflate(context, R.layout.review_adapter_item, null);
            viewHolder = new ViewHolder();
            viewHolder.reviewerName = view.findViewById(R.id.review_username);
            viewHolder.reviewRating = view.findViewById(R.id.review_rating);
            viewHolder.reviewDate = view.findViewById(R.id.review_date);
            viewHolder.reviewText = view.findViewById(R.id.review_text);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ReviewAdapterItem theReview = reviews.get(i);
        //Log.d("the review rating", theReview.getRating())
        viewHolder.reviewerName.setText(theReview.getUsername());
        viewHolder.reviewRating.setText(String.valueOf(theReview.getRating()));
        viewHolder.reviewDate.setText(theReview.getTimeCreated());
        viewHolder.reviewText.setText(theReview.getReviewText());

        return view;
    }

    static class ViewHolder {
        TextView reviewerName;
        TextView reviewRating;
        TextView reviewDate;
        TextView reviewText;
    }
}
