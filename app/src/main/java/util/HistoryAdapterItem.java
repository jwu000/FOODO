package util;

public class HistoryAdapterItem {


    String date;
    String recipeRestaurantName;
    String type;

    public HistoryAdapterItem(String recipeRestaurantName, String type, String date) {
        this.recipeRestaurantName = recipeRestaurantName;
        this.type = type;
        this.date = date;
    }

    public String getRecipeRestaurantName() {
        return recipeRestaurantName;
    }

    public void setRecipeRestaurantName(String recipeRestaurantName) {
        this.recipeRestaurantName = recipeRestaurantName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
