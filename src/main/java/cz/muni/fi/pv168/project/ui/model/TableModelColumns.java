package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.model.Unit;

import java.util.Collections;
import java.util.List;

public class TableModelColumns {
    private TableModelColumns() {}

    private static final List<Column<Recipe, ?>> recipeColumns = List.of(
            Column.readonly("Title", String.class, Recipe::getTitle),
            Column.readonly("Description", String.class, Recipe::getDescription),
            Column.readonly("Category", Category.class, Recipe::getCategory),
            Column.readonly("Preparation time (min)", Integer.class, Recipe::getTimeToPrepare),
            Column.readonly("Nutritional value (kcal)", Integer.class, Recipe::getNutritionalValue)
    );

    private static final List<Column<Ingredient, ?>> ingredientColumns = List.of(
            Column.readonly("Name", String.class, Ingredient::getName),
            Column.readonly("Base unit", Unit.class, Ingredient::getBaseUnit),
            Column.readonly("Nutritional value (kcal)", Integer.class, Ingredient::getCaloriesPerUnit)
    );

    private static final List<Column<Category, ?>> categoryColumns = List.of(
            Column.readonly("Name", Category.class, Category::getItself)
    );

    private static final List<Column<Unit, ?>> unitColumns = List.of(
            Column.readonly("Name", String.class, Unit::getName),
            Column.readonly("Abbreviation", String.class, Unit::getAbbreviation)
    );

    public static <T> List<Column<T, ?>> getColumns(Class<T> clazz) {
        if (clazz == Recipe.class) {
            return castColumns(recipeColumns);
        }
        if (clazz == Ingredient.class) {
            return castColumns(ingredientColumns);
        }
        if (clazz == Category.class) {
            return castColumns(categoryColumns);
        }
        if (clazz == Unit.class) {
            return castColumns(unitColumns);
        }

        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    private static <T> List<Column<T, ?>> castColumns(List<? extends Column<?, ?>> columns) {
        return (List<Column<T, ?>>) columns;
    }
}
