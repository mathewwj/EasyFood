package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Entity;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Unit;
import cz.muni.fi.pv168.project.service.crud.CrudService;
import cz.muni.fi.pv168.project.service.validation.ValidationException;
import cz.muni.fi.pv168.project.service.validation.Validator;
import cz.muni.fi.pv168.project.wiring.DependencyProvider;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BasicTableModel<E extends Entity> extends AbstractTableModel implements EntityTableModel<E> {

    protected final DependencyProvider dependencyProvider;
    protected final Validator<E> entityValidator;
    private final CrudService<E> entityCrudService;
    private List<E> entities;
    private List<Column<E, ?>> columns = List.of();

    public BasicTableModel(DependencyProvider dependencyProvider, Validator<E> entityValidator, CrudService<E> entityCrudService) {
        this.dependencyProvider = dependencyProvider;
        this.entityValidator = entityValidator;
        this.entityCrudService = entityCrudService;
        this.entities = new ArrayList<>(entityCrudService.findAll());
        setColumns();
    }

    private void setColumns() {
        this.columns = makeColumns();
    }

    public abstract List<Column<E, ?>> makeColumns();

    //TODO change to use the CDP
    public abstract void performAddAction(JTable table);

    public abstract void performEditAction(int[] selectedRows, JTable table);

    public abstract void performOpenAction(JTable table, int modelRow);

    public List<E> getEntities() {
        return entities;
    }

    @Override
    public int getRowCount() {
        return entities.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var recipe = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(recipe);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex).getName();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columns.get(columnIndex).getColumnType();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columns.get(columnIndex).isEditable();
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (value != null) {
            var entity = getEntity(rowIndex);
            columns.get(columnIndex).setValue(value, entity);
            updateRow(entity);
        }
    }

    public void deleteRow(int rowIndex) {
        var entityToBeDeleted = getEntity(rowIndex);
        entityCrudService.deleteByGuid(entityToBeDeleted.getGuid())
                .intoException();
        entities.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    // TODO ask why separate collections for mem repository and table
    public void addRow(E entity) {
        entityCrudService.create(entity).intoException();

        int newRowIndex = entities.size();
        entities.add(entity);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void updateRow(E entity) {
        entityCrudService.update(entity)
                .intoException();
        int rowIndex = entities.indexOf(entity);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void refresh() {
        this.entities = new ArrayList<>(entityCrudService.findAll());
        fireTableDataChanged();
    }

    public E getEntity(int rowIndex) {
        return entities.get(rowIndex);
    }

    protected void setAndUpdate(Optional<E> optional, E entity) {
        if (optional.isPresent()) {
            E gotEntity = optional.get();

            long countOfEquivalentEntities = entityCrudService.findAll().stream()
                    .filter(e -> e.equals(gotEntity) && ! e.getGuid().equals(gotEntity.getGuid())).count();
            if (countOfEquivalentEntities != 0) {
                throw new ValidationException("", List.of(gotEntity + " already exists in memory"));
            }

            entity.setAll(gotEntity);
            this.updateRow(entity);
        }
    }
}
