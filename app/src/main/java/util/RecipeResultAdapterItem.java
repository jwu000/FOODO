package util;

import org.json.JSONArray;

import java.net.URI;

public class RecipeResultAdapterItem {

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    String recipeName;
    double totalPrice;
    double pricePerServing;
    int numberOfIngridents;
    int timeNeeded;
    int servings;
    URI recipeImage;
    JSONArray instructions;
    JSONArray ingridents;

    public RecipeResultAdapterItem(String recipeName, double totalPrice, double pricePerServing, int numberOfIngridents, int timeNeeded, int servings, URI recipeImage, JSONArray instructions, JSONArray ingridents) {
        this.recipeName = recipeName;
        this.totalPrice = totalPrice;
        this.pricePerServing = pricePerServing;
        this.numberOfIngridents = numberOfIngridents;
        this.timeNeeded = timeNeeded;
        this.servings = servings;
        this.recipeImage = recipeImage;
        this.instructions = instructions;
        this.ingridents = ingridents;
    }


    public double getTotalPrice() {
        return totalPrice;
    }

    public double getPricePerServing() {
        return pricePerServing;
    }

    public int getNumberOfIngridents() {
        return numberOfIngridents;
    }

    public int getTimeNeeded() {
        return timeNeeded;
    }

    public int getServings() {
        return servings;
    }

    public URI getRecipeImage() {
        return recipeImage;
    }

    public JSONArray getInstructions() {
        return instructions;
    }

    public JSONArray getIngridents() {
        return ingridents;
    }



    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPricePerServing(double pricePerServing) {
        this.pricePerServing = pricePerServing;
    }

    public void setNumberOfIngridents(int numberOfIngridents) {
        this.numberOfIngridents = numberOfIngridents;
    }

    public void setTimeNeeded(int timeNeeded) {
        this.timeNeeded = timeNeeded;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setRecipeImage(URI recipeImage) {
        this.recipeImage = recipeImage;
    }

    public void setInstructions(JSONArray instructions) {
        this.instructions = instructions;
    }

    public void setIngridents(JSONArray ingridents) {
        this.ingridents = ingridents;
    }


}
