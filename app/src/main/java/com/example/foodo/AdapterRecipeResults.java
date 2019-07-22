package com.example.foodo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

public class AdapterRecipeResults  extends BaseAdapter {

    Context context;
    ArrayList<RecipeResultAdapterItem> recipes;

    public AdapterRecipeResults(Context context, ArrayList<RecipeResultAdapterItem> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @Override
    public int getCount() {
        return recipes.size();
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
