package util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.foodoapp.foodo.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterRestaurantResults extends BaseAdapter {

    Context context;
    ArrayList<RestaurantResultAdapterItem> restaurants;


    public AdapterRestaurantResults(Context context, ArrayList<RestaurantResultAdapterItem> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
    }

    @Override
    public int getCount() {
        return restaurants.size();
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
            view  = View.inflate(context, R.layout.restaurant_result_adapter_item, null);
            viewHolder = new ViewHolder();
            viewHolder.restaurantName =view.findViewById(R.id.restaurant_name);
            viewHolder.ratingText = view.findViewById(R.id.rating_num);
            viewHolder.priceEstimate = view.findViewById(R.id.price_es);
            viewHolder.distance = view.findViewById(R.id.distance_km);
            view.setTag(viewHolder);
        }
        else {
            viewHolder= (ViewHolder) view.getTag();
        }

        String dollarSigns = restaurants.get(i).getPrice();
        String priceEstimate;
        Log.d("the Dollarsigns", dollarSigns);
        if (dollarSigns.equals("$")) {
            priceEstimate = "$1-10";
        }
        else if (dollarSigns.equals("$$")) {
            priceEstimate = "$11-30";
        }
        else if (dollarSigns.equals("$$$")) {
            priceEstimate = "$31-60";
        }
        else {
            priceEstimate = "$61+";
        }
        Log.d("the estimate", priceEstimate);
        viewHolder.restaurantName.setText(restaurants.get(i).getRestaurantName());
        viewHolder.ratingText.setText(new DecimalFormat("#.##").format(restaurants.get(i).getRating()));
        viewHolder.distance.setText(restaurants.get(i).getDistance() + " km");
        viewHolder.priceEstimate.setText(priceEstimate);

        return view;
    }

    static class ViewHolder{
        TextView restaurantName;
        TextView ratingText;
        TextView priceEstimate;
        TextView distance;
    }
}
