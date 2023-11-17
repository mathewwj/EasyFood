package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Unit;
import cz.muni.fi.pv168.project.ui.model.UnitTableModel;

import javax.swing.*;

public class OpenUnitDialog extends EntityDialog<Unit> {

    private final JLabel name = new JLabel();
    private final JLabel conversionRate =  new JLabel();
    private final JLabel ingredientType =  new JLabel();
    private final UnitTableModel unitTableModel;

    private final Unit unit;

    public OpenUnitDialog(Unit unit, UnitTableModel unitTableModel) {
        this.unit = unit;
        this.unitTableModel = unitTableModel; // TODO: implement through Repository/CRUDService
        setValues();
        addFields();
    }

    private void setValues() {
        name.setText("<html>" + "<B>" + unit.getName() + " (" + unit.getAbbreviation() + ")" + "</B>" + "</html>");
        conversionRate.setText("<html>" + "<B>" + "Conversion rate: " + "</B>" + "1 " + unit.getAbbreviation() + " = " + unit.getConversionRate() + " " + unit.getIngredientType().getBaseUnit(unitTableModel).getAbbreviation() + "</html>");
        ingredientType.setText("<html>" + "<B>" + "Unit type: " + "</B>" + unit.getIngredientType() + "</html>");
    }

    private void addFields() {
        add(name, THIN_HEIGHT);
        add(conversionRate, THIN_HEIGHT);
        add(ingredientType, THIN_HEIGHT);
    }

    @Override
    Unit getEntity() {
        return unit;
    }
}


