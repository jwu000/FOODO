package util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.foodoapp.foodo.R;

import java.util.ArrayList;

public class AdapterFavoriteList extends BaseAdapter {

    Context context;
    ArrayList<FavoriteAdapterItem> favorite_list;

    public AdapterFavoriteList(Context context, ArrayList<FavoriteAdapterItem> favorite_list){
        this.context = context;
        this.favorite_list = favorite_list;
    }

    @Override
    public int getCount() {
        return favorite_list.size();
    }

    @Override
    public Object getItem(int i) {
        return favorite_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        FavoriteViewHolder viewHolder;

        if(view == null){
            view = View.inflate(context, R.layout.favorite_list_adapter_item, null);

            viewHolder = new FavoriteViewHolder();
            viewHolder.type = view.findViewById(R.id.favorite_item_adpater_type);
            viewHolder.name = view.findViewById(R.id.favorite_item_adpater_name);

            view.setTag(viewHolder);
        }else{
            viewHolder = (FavoriteViewHolder) view.getTag();
        }

        viewHolder.type.setText(favorite_list.get(i).getType().toUpperCase());
        viewHolder.name.setText(favorite_list.get(i).getName());

        return view;
    }

    static class FavoriteViewHolder {
        TextView type;
        TextView name;
    }

}
