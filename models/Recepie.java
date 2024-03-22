package models;

import java.util.List;

public class Recepie {
    private String id;
    private String title;
    private String date;
    private List<String> ingredients;
    private List<String> preparation;
    private int calories;
    private String fatPercentage;
    private String carbohydratesPercentage;
    private String proteinPercentage;

    // Constructeur
    public Recepie(String id, String title, String date, List<String> ingredients, List<String> preparation,
            int calories, String fatPercentage, String carbohydratesPercentage, String proteinPercentage) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.calories = calories;
        this.fatPercentage = fatPercentage;
        this.carbohydratesPercentage = carbohydratesPercentage;
        this.proteinPercentage = proteinPercentage;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getPreparation() {
        return preparation;
    }

    public int getCalories() {
        return calories;
    }

    public String getFatPercentage() {
        return fatPercentage;
    }

    public String getCarbohydratesPercentage() {
        return carbohydratesPercentage;
    }

    public String getProteinPercentage() {
        return proteinPercentage;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setPreparation(List<String> preparation) {
        this.preparation = preparation;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setFatPercentage(String fatPercentage) {
        this.fatPercentage = fatPercentage;
    }

    public void setCarbohydratesPercentage(String carbohydratesPercentage) {
        this.carbohydratesPercentage = carbohydratesPercentage;
    }

    public void setProteinPercentage(String proteinPercentage) {
        this.proteinPercentage = proteinPercentage;
    }
}
