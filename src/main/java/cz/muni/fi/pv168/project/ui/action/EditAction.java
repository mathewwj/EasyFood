package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public final class EditAction extends AbstractAction {

    private final JTable recipeTable;
    private final JFrame jFrame;


    public EditAction(JTable table, JFrame jFrame) {
        super("Edit", Icons.EDIT_ICON);
        this.recipeTable = table;
        this.jFrame = jFrame;
        putValue(SHORT_DESCRIPTION, "Edits selected recipe");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTabbedPane tabbedPane = (JTabbedPane) this.jFrame.getContentPane().getComponent(1);
        int currentTab = tabbedPane.getSelectedIndex();
        System.out.println("Edit ---> " + tabbedPane.getTitleAt(currentTab) );
    }
}
