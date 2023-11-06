package cz.muni.fi.pv168.project.ui.model;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class GeneralTableModel<T> extends AbstractTableModel implements EntityTableModel<T> {
//    private final Class<T> type;
    private final List<T> entities;
    private final List<Column<T, ?>> columns;

    public GeneralTableModel(List<T> entities) {
        this.entities = new ArrayList<>(entities);
        this.columns = TableModelColumns.getColumns(getClassOfEntities());
    }

    /** gets type of T**/
    private Class<T> getClassOfEntities() {
        Class<? extends List> listClass = entities.getClass();
        ParameterizedType genericSuperclass = (ParameterizedType) listClass.getGenericSuperclass();
        return (Class<T>) genericSuperclass.getActualTypeArguments()[0];
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
        var recipe = getEntity(rowIndex);
        columns.get(columnIndex).setValue(value, recipe);
    }

    @Override
    public T getEntity(int rowIndex) {
        return entities.get(rowIndex);
    }

    public void deleteRow(int rowIndex) {
        entities.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(T entity) {
        int newRowIndex = entities.size();
        entities.add(entity);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void updateRow(T entity) {
        int rowIndex = entities.indexOf(entity);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
    public List<T> getEntities() {
        return entities;
    }
}
