package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.ui.model.BasicTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public final class OpenAction extends GeneralAction {

    public OpenAction() {
        super("Open", Icons.OPEN_ICON);
        putValue(SHORT_DESCRIPTION, "Open");
        putValue(MNEMONIC_KEY, KeyEvent.VK_O);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl O"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTable table = super.getTable();
        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length != 1) {
            throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
        }
        if (table.isEditing()) {
            table.getCellEditor().cancelCellEditing();
        }
        int modelRow = table.convertRowIndexToModel(selectedRows[0]);
        BasicTableModel tableModel = (BasicTableModel) table.getModel();
        tableModel.performOpenAction(table, modelRow);
    }

    @Override
    public void setShortDescription() {
        putValue(SHORT_DESCRIPTION, "Open " + getCurrentTabName());
    }
}
