package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Unit;
import cz.muni.fi.pv168.project.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.model.UnitTableModel;
import cz.muni.fi.pv168.project.wiring.DependencyProvider;

import javax.swing.*;

public class AddIngredientDialog extends EntityDialog<Ingredient> {
    private final JTextField name = new JTextField();
    private final JFormattedTextField nutritionalValue = FieldMaker.makeIntField();
    private final JComboBox<Unit> defaultUnit = new JComboBox<>();
    private final DependencyProvider dependencyProvider;

    public AddIngredientDialog(DependencyProvider dependencyProvider, Validator<Ingredient> ingredientValidator) {
        super(ingredientValidator);
        this.dependencyProvider = dependencyProvider;
        setValues();
        addFields();
    }

    private void setValues() {
        name.setText("");
        nutritionalValue.setText("0");
        defaultUnit.setModel(new javax.swing.DefaultComboBoxModel<>(dependencyProvider.getUnitCrudService().findAll().toArray(new Unit[0])));
    }

    private void addFields() {
        add("Name:", name, THIN_HEIGHT);
        add("Default Unit:", defaultUnit, THIN_HEIGHT);
        add("Nutritional value(kcal) per default unit:", nutritionalValue, THIN_HEIGHT);
    }

    // Helper method to remove commas from a string
    private float removeCommas(String valueWithCommas) {
        return Float.parseFloat(valueWithCommas.replaceAll(",", ""));
    }
    @Override
    Ingredient getEntity() {
        return new Ingredient(name.getText(), (Unit) defaultUnit.getSelectedItem(),
                Float.parseFloat(nutritionalValue.getValue().toString().replaceAll(" ", "")));
    }
}
