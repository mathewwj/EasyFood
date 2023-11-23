package cz.muni.fi.pv168.project.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.muni.fi.pv168.project.export.json.deserializers.RecipeJsonDeserializer;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

@JsonDeserialize(using = RecipeJsonDeserializer.class)
public class Recipe extends Entity {
    private String title;
    private String description;
    private int portionCount;
    private String instructions;
    private int timeToPrepare; // in minutes; import java.util.concurrent.TimeUnit
    private int nutritionalValue;
    private Category category;
    /** when modifying, please call {@link Recipe#calculateNutritionalValue()}**/
    private HashMap<Ingredient, AmountInUnit> ingredients;

    public Recipe(){}
    public Recipe(String guid, String title, String description, int portionCount, String instructions, int timeToPrepare, Category category, HashMap<Ingredient, AmountInUnit> ingredientList) {
        super(guid);
        this.title = title;
        this.description = description;
        this.portionCount = portionCount;
        this.instructions = instructions;
        this.timeToPrepare = timeToPrepare;
        this.category = category;
        this.ingredients = ingredientList;
        calculateNutritionalValue();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPortionCount() {
        return portionCount;
    }

    public void setPortionCount(int portionCount) {
        this.portionCount = portionCount;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getTimeToPrepare() {
        return timeToPrepare;
    }

    public void setTimeToPrepare(int timeToPrepare) {
        this.timeToPrepare = timeToPrepare;
    }

    public int getNutritionalValue() {
        return nutritionalValue;
    }

    public void setNutritionalValue(int nutritionalValue) {
        this.nutritionalValue = nutritionalValue;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public HashMap<Ingredient, AmountInUnit> getIngredients() {
        return ingredients;
    }

    public void setIngredients(HashMap<Ingredient, AmountInUnit> ingredients) {
        this.ingredients = ingredients;
        calculateNutritionalValue();
    }
    private void calculateNutritionalValue() {
        this.nutritionalValue = 0;
        for (Map.Entry<Ingredient, AmountInUnit> entry: ingredients.entrySet()) {
            Unit unit = entry.getValue().getUnit();
            int amount = entry.getValue().getAmount();
            Ingredient ingredient = entry.getKey();
            this.nutritionalValue += ingredient.getTotalCalories(unit, amount);
        }
    }

    public void addIngredient(Ingredient ingredient, Unit unit, int amount) {
        this.ingredients.putIfAbsent(ingredient, new AmountInUnit(unit, amount));
        calculateNutritionalValue();
    }

    /**
     * @return true if ingredient found
     */
    public boolean deleteIngredient(Ingredient ingredient) {
        var deletedRecord = this.ingredients.remove(ingredient);
        calculateNutritionalValue();
        return deletedRecord != null;
    }
}
