package com.example.foodo;

import java.net.URI;

public class RecipeResultAdapterItem {

    float totalPrice;
    float pricePerServing;
    int numberOfIngridents;
    int timeNeeded;
    int servings;
    URI recipeImage;
    String[] instructions;
    String[] ingridents;

    public RecipeResultAdapterItem(float totalPrice, float pricePerServing, int numberOfIngridents, int timeNeeded, int servins, URI recipeImage, String[] instructions, String[] ingridents) {
        this.totalPrice = totalPrice;
        this.pricePerServing = pricePerServing;
        this.numberOfIngridents = numberOfIngridents;
        this.timeNeeded = timeNeeded;
        this.servings = servins;
        this.recipeImage = recipeImage;
        this.instructions = instructions;
        this.ingridents = ingridents;
    }


    public float getTotalPrice() {
        return totalPrice;
    }

    public float getPricePerServing() {
        return pricePerServing;
    }

    public int getNumberOfIngridents() {
        return numberOfIngridents;
    }

    public int getTimeNeeded() {
        return timeNeeded;
    }

    public int getServins() {
        return servings;
    }

    public URI getRecipeImage() {
        return recipeImage;
    }

    public String[] getInstructions() {
        return instructions;
    }

    public String[] getIngridents() {
        return ingridents;
    }



    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPricePerServing(float pricePerServing) {
        this.pricePerServing = pricePerServing;
    }

    public void setNumberOfIngridents(int numberOfIngridents) {
        this.numberOfIngridents = numberOfIngridents;
    }

    public void setTimeNeeded(int timeNeeded) {
        this.timeNeeded = timeNeeded;
    }

    public void setServins(int servins) {
        this.servings = servins;
    }

    public void setRecipeImage(URI recipeImage) {
        this.recipeImage = recipeImage;
    }

    public void setInstructions(String[] instructions) {
        this.instructions = instructions;
    }

    public void setIngridents(String[] ingridents) {
        this.ingridents = ingridents;
    }


}
