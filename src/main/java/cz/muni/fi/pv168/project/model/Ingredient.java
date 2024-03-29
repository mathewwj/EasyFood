package cz.muni.fi.pv168.project.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.muni.fi.pv168.project.export.json.deserializers.IngredientJsonDeserializer;
import cz.muni.fi.pv168.project.export.json.seralizers.IngredientJsonSerializer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

@JsonSerialize(using = IngredientJsonSerializer.class)
@JsonDeserialize(using = IngredientJsonDeserializer.class)
public class Ingredient extends Entity {
    private String name;
    private Unit defaultUnit;
    private float caloriesPerUnit;

    public Ingredient(String guid, String name, Unit defaultUnit, float caloriesPerUnit) {
        super(guid);
        this.name = name;
        this.defaultUnit = defaultUnit;
        this.caloriesPerUnit = caloriesPerUnit; // calories per default unit
    }

    public Ingredient(String name, Unit defaultUnit, float caloriesPerUnit) {
        this.name = name;
        this.defaultUnit = defaultUnit;
        this.caloriesPerUnit = caloriesPerUnit; // calories per default unit
    }

    public Ingredient() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Unit getDefaultUnit() {
        return defaultUnit;
    }

    public void setDefaultUnit(Unit defaultUnit) {
        this.defaultUnit = defaultUnit;
    }

    public float getCaloriesPerUnit() {
        return caloriesPerUnit;
    }

    public int getTotalCalories(Unit anyUnit, int amount) {
        float result = caloriesPerUnit / defaultUnit.getConversionRate() * anyUnit.getConversionRate() * amount;
        return (int) result;
    }

    public int countInstances(List<Recipe> recipes){
        int count = 0;
        for(Recipe recipe : recipes){
            if(recipe.getIngredients().containsKey(this)){
                count++;
            }
        }
        return count;
    }

    public void setCaloriesPerUnit(float caloriesPerUnit) {
        BigDecimal bigDecimal = new BigDecimal(Float.toString(caloriesPerUnit));
        bigDecimal = bigDecimal.setScale(3, RoundingMode.HALF_UP);
        this.caloriesPerUnit = bigDecimal.floatValue();
    }

    @Override
    public String toString() {
        return defaultUnit.getIngredientType().getSymbol() + " " + name;
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Ingredient theirs)) {
            return false;
        }
        return Objects.equals(this.name, theirs.name);
    }

    @Override
    public Ingredient deepClone() {
        return new Ingredient(getGuid(), getName(), getDefaultUnit(), getCaloriesPerUnit());
    }

    @Override
    public void setAll(Entity setObject) {
        if (!(setObject instanceof Ingredient setIngredient)) {
            return;
        }

        this.setName(setIngredient.getName());
        this.setDefaultUnit(setIngredient.getDefaultUnit());
        this.setCaloriesPerUnit(setIngredient.getCaloriesPerUnit());
    }
}

