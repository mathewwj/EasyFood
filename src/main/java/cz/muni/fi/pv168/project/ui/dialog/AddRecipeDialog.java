package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.AmountInUnit;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.model.Unit;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class AddRecipeDialog extends EntityDialog<Recipe> {

    private final JTextField title = new JTextField();
    private final JTextField description = new JTextField();
    private final JTextField portionCount = FieldMaker.makeIntField();
    private final JTextArea instructions = new JTextArea();
    private final JTextField timeToPrepare = FieldMaker.makeIntField();
    private final JComboBox<Category> category = new JComboBox<>();
    private final JLabel ingredientsLabel = new JLabel();
    private final JButton newButton = new JButton();
    private JScrollPane scroll;
    private final JPanel test = new JPanel();

    private final List<Category> categories;
    private final List<Ingredient> ingredients;
    private final List<Unit> units;



    public AddRecipeDialog(List<Category> categories, List<Ingredient> ingredients, List<Unit> units) {
        this.categories = categories;
        this.ingredients = ingredients;
        this.units = units;
        setValues();
        addFields();
        addIngredients();
    }

    private void setValues() {
        panel.setPreferredSize(new Dimension(330, 560));
        title.setText("");
        description.setText("");
        portionCount.setText("1");
        instructions.setText("");
        timeToPrepare.setText("0");
        category.setModel(new javax.swing.DefaultComboBoxModel<>(categories.toArray(new Category[categories.size()])));
        ingredientsLabel.setText("Ingredients");
        newButton.setText("New Ingredient");
        newButton.addActionListener(new AddIngredient());
        test.setLayout(new MigLayout("wrap 1"));
    }

    private void addFields() {
        add("Title:", title);
        add("Description:", description);
        add("Portions:", portionCount);
        JScrollPane scrollInstructions = new JScrollPane(instructions);
        scrollInstructions.setMinimumSize(new Dimension(300,100));
        add("Instructions:", scrollInstructions);
        add("Time to prepare(min):", timeToPrepare);
        add("Category:", category);
        panel.add(ingredientsLabel);
        panel.add(newButton);
        scroll = new JScrollPane(test);
        scroll.setMinimumSize(new Dimension(300,150));
        panel.add(scroll);
    }

    private void addIngredients() {
        addIngredient(null, "0", null);
    }

    public void addIngredient(Object selectedIngredient, String count, Object selectedUnit){
        JPanel newIngredient = new JPanel();
        newIngredient.setLayout(new MigLayout("wrap 4"));

        JComboBox<Ingredient> ingredient = new JComboBox<>();
        ingredient.setModel(new javax.swing.DefaultComboBoxModel<>(ingredients.toArray(new Ingredient[ingredients.size()])));
        if (selectedIngredient != null){
            ingredient.setSelectedItem(selectedIngredient);
        } else {
            ingredient.setSelectedIndex(-1);
        }
        ingredient.addActionListener(new FilterUnits());
        newIngredient.add(ingredient);

        JTextField quantity = new JTextField();
        quantity.setText(count);
        newIngredient.add(quantity);

        JComboBox<Unit> unitBox = new JComboBox<>();
        filterUnits(ingredient);
        if (selectedUnit != null){
            unitBox.setSelectedItem(selectedUnit);
        }
        newIngredient.add(unitBox);

        JButton xButton = new JButton();
        xButton.setText("x");
        xButton.addActionListener(new DeleteIngredient());
        newIngredient.add(xButton);

        test.add(newIngredient);
    }

    public void deleteIngredient(JButton source){
        test.remove(source.getParent());
    }

    @Override
    Recipe getEntity() {
        Recipe recipe = new Recipe();
        HashMap<Ingredient, AmountInUnit> newIgredients = getAllIngredients();
        if(newIgredients == null){
            return null;
        }
        recipe.setIngredients(newIgredients);
        recipe.setTitle(title.getText());
        recipe.setDescription(description.getText());
        recipe.setPortionCount(Integer.parseInt(portionCount.getText().replaceAll(" ", "")));
        recipe.setInstructions(instructions.getText());
        recipe.setTimeToPrepare(Integer.parseInt(timeToPrepare.getText().replaceAll(" ", "")));
        recipe.setCategory((Category) category.getSelectedItem());
        return recipe;
    }

    public HashMap<Ingredient, AmountInUnit> getAllIngredients(){
        HashMap<Ingredient, AmountInUnit> newIgredients = new HashMap<>();
        for (Component component : test.getComponents()){
            Ingredient ingredient = (Ingredient)((JComboBox<Ingredient>)((JPanel)component).getComponent(0)).getSelectedItem();
            int count = Integer.parseInt(((JTextField)((JPanel)component).getComponent(1)).getText());
            Unit unit = (Unit)((JComboBox<Unit>)((JPanel)component).getComponent(2)).getSelectedItem();
            if (newIgredients.containsKey(ingredient)){
                JOptionPane.showConfirmDialog(null, "There are some duplicities in your ingredients.\nNot possible to save.", "Warning", JOptionPane.CLOSED_OPTION);
                return null;
            }
            newIgredients.put(ingredient, new AmountInUnit(unit, count));
        }
        return newIgredients;
    }

    public Unit[] filterUnits(JComboBox<Ingredient> source){
        Ingredient selectedIngredient = (Ingredient)source.getSelectedItem();;
        ArrayList<Unit> filteredUnits = new ArrayList<>();
        for(Unit unit : units){
            if (selectedIngredient != null && selectedIngredient.getDefaultUnit().getIngredientType() == unit.getIngredientType()){
                filteredUnits.add(unit);
            }
        }
        return filteredUnits.toArray(new Unit[filteredUnits.size()]);
    }

    public class AddIngredient implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            addIngredient(null, "0", null);
            panel.revalidate();
            panel.repaint();
        }
    }

    public class DeleteIngredient implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            deleteIngredient((JButton)event.getSource());
            panel.revalidate();
            panel.repaint();
        }
    }

    public class FilterUnits implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JComboBox<Ingredient> source = (JComboBox<Ingredient>)event.getSource();
            Unit[] filteredUnits = filterUnits(source);
            JComboBox<Unit> unitBox = (JComboBox<Unit>)(((JPanel)(source.getParent()))).getComponent(2);
            unitBox.setModel(new javax.swing.DefaultComboBoxModel<>(filteredUnits));
            unitBox.setSelectedItem(((Ingredient)source.getSelectedItem()).getDefaultUnit());
            panel.revalidate();
            panel.repaint();
        }
    }
}

