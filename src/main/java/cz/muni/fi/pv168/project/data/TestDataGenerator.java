package cz.muni.fi.pv168.project.data;

import cz.muni.fi.pv168.project.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class TestDataGenerator {
    private final Random random = new Random();


    public Category createTestCategory(int index) {
        return new Category("Category " + index, Category.DEFAULT_COLOR);
    }

    public Ingredient createTestIngredient(int index) {
        int pick = random.nextInt(IngredientType.values().length);
        return new Ingredient("Ingredient " + index, IngredientType.values()[pick], random.nextInt(10, 100));
    }

    public Unit createTestUnit(int index) {
        int pick = random.nextInt(IngredientType.values().length);
        return new Unit("Unit " + index, IngredientType.values()[pick]);
    }

    public Recipe createTestRecipe(int index, Category category, List<Ingredient> ingredient, Unit unit) {
        return new Recipe("Recipe " + index,
                "Description for " + index,
                random.nextInt(1, 10),
                "Instruction for Recipe "+ index,
                random.nextInt(10, 100),
                category,
                ingredient
                );
    }

    public List<Category> createTestCategories(int count){
        return IntStream.range(0, count)
                .mapToObj(this::createTestCategory)
                .collect(Collectors.toList());
    }

    public List<Ingredient> createTestIngredients(int count){
        return IntStream.range(0, count)
                .mapToObj(this::createTestIngredient)
                .collect(Collectors.toList());
    }

    public List<Unit> createTestUnits(int count){
        return IntStream.range(0, count)
                .mapToObj(this::createTestUnit)
                .collect(Collectors.toList());
    }

    public List<Recipe> createTestRecipes(int count,List<Category> categories, List<Ingredient> ingredients, List<Unit> units){
        List<Recipe> recipes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            recipes.add(createTestRecipe(i, selectRandom(categories), selectRandomMultiple(ingredients), selectRandom(units)));
        }
        return recipes;
    }

    private <T> List<T> selectRandomMultiple(List<T> data) {
        int nextSize = random.nextInt(1, data.size());
        List<T> sublist = new ArrayList<>(nextSize);
        for (int i = 0; i < nextSize; i++) {
            T selected = selectRandom(data);
            if (!sublist.contains(selected))
                sublist.add(selected);
        }
        return sublist;
    }

    private <T> T selectRandom(List<T> data) {
        int index = random.nextInt(data.size());
        return data.get(index);
    }
}