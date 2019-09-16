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

        ViewHolder viewHolder;

        if (view == null) {
            view = View.inflate(context, R.layout.recipe_result_adapter_item, null);

            viewHolder = new ViewHolder();
            viewHolder.recipeName = view.findViewById(R.id.recipe_name);
            viewHolder.totalCost = view.findViewById(R.id.total_cost);
            viewHolder.numServings= view.findViewById(R.id.number_of_servings);
            viewHolder.numIngredients = view.findViewById(R.id.number_of_ingredients);
            viewHolder.recipeTime = view.findViewById(R.id.recipe_time);

            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        Log.d("doing listview stuff", "JAWD");
        viewHolder.recipeName.setText(recipes.get(i).getRecipeName());
        viewHolder.totalCost.setText(new DecimalFormat("#.##").format(recipes.get(i).getTotalPrice()));
        viewHolder.numServings.setText(""+recipes.get(i).getServings());
        viewHolder.numIngredients.setText(""+recipes.get(i).getNumberOfIngridents());
        viewHolder.recipeTime.setText(""+recipes.get(i).getTimeNeeded());



        return view;
    }

    static class ViewHolder {
        TextView recipeName;
        TextView totalCost;
        TextView numIngredients;
        TextView recipeTime;
        TextView numServings;
    }
}
