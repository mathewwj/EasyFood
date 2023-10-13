package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public final class DeleteAction extends AbstractAction {

    private final JTable recipeTable;
    private final JFrame jFrame;

    public DeleteAction(JTable table, JFrame jFrame) {
        super("Delete", Icons.DELETE_ICON);
        this.recipeTable = table;
        this.jFrame = jFrame;
        putValue(SHORT_DESCRIPTION, "Deletes selected recipes");
        putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTabbedPane tabbedPane = (JTabbedPane) this.jFrame.getContentPane().getComponent(1);
        int currentTab = tabbedPane.getSelectedIndex();
        System.out.println("Delete ---> " + tabbedPane.getTitleAt(currentTab) );
    }
}
