package com.example.foodo;

import java.net.URI;

public class RecipeResultAdapterItem {

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    String recipeName;
    float totalPrice;
    float pricePerServing;
    int numberOfIngridents;
    int timeNeeded;
    int servings;
    URI recipeImage;
    String[] instructions;
    String[] ingridents;

    public RecipeResultAdapterItem(String recipeName, float totalPrice, float pricePerServing, int numberOfIngridents, int timeNeeded, int servings, URI recipeImage, String[] instructions, String[] ingridents) {
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

    public int getServings() {
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

    public void setServings(int servings) {
        this.servings = servings;
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
