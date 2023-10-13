package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.ui.action.AddAction;
import cz.muni.fi.pv168.project.ui.action.DeleteAction;
import cz.muni.fi.pv168.project.ui.action.EditAction;
import cz.muni.fi.pv168.project.ui.action.OpenAction;
import cz.muni.fi.pv168.project.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.project.ui.model.IngredientTableModel;
import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;
import cz.muni.fi.pv168.project.ui.model.UnitTableModel;
import cz.muni.fi.pv168.project.ui.panels.CategoryTablePanel;
import cz.muni.fi.pv168.project.ui.panels.IngredientTablePanel;
import cz.muni.fi.pv168.project.ui.panels.RecipeTablePanel;
import cz.muni.fi.pv168.project.ui.panels.UnitTablePanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class MainWindow {
    private final JFrame frame;
    private final Action addAction;
    private final Action deleteAction;
    private final Action editAction;
    private final Action openAction;

    public MainWindow() {
        frame = createFrame();

        // Generate test objects

        var testDataGenerator = new TestDataGenerator();
        var categories = testDataGenerator.createTestCategories(5);
        var ingredients = testDataGenerator.createTestIngredients(5);
        var units = testDataGenerator.createTestUnits(5);
        var recipes = testDataGenerator.createTestRecipes(10, categories, ingredients, units);

        // Create models
        var recipeTableModel = new RecipeTableModel(recipes);
        var ingredientTableModel = new IngredientTableModel(ingredients);
        var categoryTableModel = new CategoryTableModel(categories);
        var unitTableModel = new UnitTableModel(units);

        // Create panels
        var recipeTablePanel = new RecipeTablePanel(recipeTableModel, this::changeActionsState);
        var ingredientTablePanel = new IngredientTablePanel(ingredientTableModel, this::changeActionsState);
        var categoryTablePanel = new CategoryTablePanel(categoryTableModel, this::changeActionsState);
        var unitTablePanel = new UnitTablePanel(unitTableModel, this::changeActionsState);


        // Set up actions for recipe table
        addAction = new AddAction(recipeTablePanel.getTable(), frame);
        deleteAction = new DeleteAction(recipeTablePanel.getTable(), frame);
        deleteAction.setEnabled(false);
        editAction = new EditAction(recipeTablePanel.getTable(), frame);
        editAction.setEnabled(false);
        openAction = new OpenAction(recipeTablePanel.getTable(), frame);
        openAction.setEnabled(false);

        // Add popup menu, toolbar, menubar
        recipeTablePanel.getTable().setComponentPopupMenu(createTablePopupMenu());
        ingredientTablePanel.getTable().setComponentPopupMenu(createTablePopupMenu());
        categoryTablePanel.getTable().setComponentPopupMenu(createTablePopupMenu());
        unitTablePanel.getTable().setComponentPopupMenu(createTablePopupMenu());
        frame.add(createToolbar(), BorderLayout.BEFORE_FIRST_LINE);
        frame.setJMenuBar(createMenuBar());

        // Add the panels to tabbed pane
        var tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Recipes", recipeTablePanel);
        tabbedPane.addTab("Ingredients", ingredientTablePanel);
        tabbedPane.addTab("Categories", categoryTablePanel);
        tabbedPane.addTab("Units", unitTablePanel);


        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                recipeTablePanel.getTable().clearSelection();
                ingredientTablePanel.getTable().clearSelection();
                unitTablePanel.getTable().clearSelection();
                categoryTablePanel.getTable().clearSelection();
            }
        });
        frame.add(tabbedPane, BorderLayout.CENTER);


        frame.pack();
    }

    public void show() {
        frame.setVisible(true);
    }

    private JFrame createFrame() {
        var frame = new JFrame("Recipes");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return frame;
    }


    private JPopupMenu createTablePopupMenu() {
        var menu = new JPopupMenu();
        menu.add(openAction);
        menu.add(editAction);
        menu.add(addAction);
        menu.add(deleteAction);
        return menu;
    }

    private JMenuBar createMenuBar() {
        var menuBar = new JMenuBar();
        var editMenu = new JMenu("Edit");
        editMenu.add(openAction);
        editMenu.add(editAction);
        editMenu.add(addAction);
        editMenu.add(deleteAction);
        editMenu.setMnemonic('e');
        menuBar.add(editMenu);
        return menuBar;
    }

    private JToolBar createToolbar() {
        var toolbar = new JToolBar();
        toolbar.add(openAction);
        toolbar.add(editAction);
        toolbar.add(addAction);
        toolbar.add(deleteAction);
        return toolbar;
    }

    private void changeActionsState(int selectedItemsCount) {
        openAction.setEnabled(selectedItemsCount == 1);
        editAction.setEnabled(selectedItemsCount == 1);
        deleteAction.setEnabled(selectedItemsCount >= 1);
    }
}
