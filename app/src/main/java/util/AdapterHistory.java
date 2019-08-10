package util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.foodo.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;


public class AdapterHistory extends BaseAdapter {

    Context context;
    ArrayList<HistoryAdapterItem> history;

    public AdapterHistory(Context context, ArrayList<HistoryAdapterItem> history) {
        this.context = context;
        this.history = history;
    }

    @Override
    public int getCount() {
        return history.size();
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
            view = View.inflate(context, R.layout.history_adapter_item, null);
            viewHolder = new ViewHolder();
            viewHolder.timeStamp = view.findViewById(R.id.history_date);
            viewHolder.action = view.findViewById(R.id.history_action);
            view.setTag(viewHolder);
        }
        else {
            viewHolder= (ViewHolder)view.getTag();
        }

        HistoryAdapterItem theHistory = history.get(i);
        Log.d("the DATE CHECK", theHistory.getDate());
        Log.d("check conversion", String.valueOf(Long.parseLong(theHistory.getDate())));
        Date historyDate =new Date(Long.parseLong(theHistory.getDate()));
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String actualDate =dateFormat.format(historyDate);
        viewHolder.timeStamp.setText(actualDate);
        if (theHistory.getType().equals("recipe")) {
            viewHolder.action.setText("Cooked " + theHistory.getRecipeRestaurantName());
        }
        else if (theHistory.getType().equals("restaurant")) {
            viewHolder.action.setText("Ate out at " + theHistory.getRecipeRestaurantName());
        }

        return view;


    }

    static class ViewHolder {
        TextView timeStamp;
        TextView action;
    }
}
