package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public final class OpenAction extends AbstractAction {

    private final JTable recipeTable;
    private final JFrame jFrame;

    public OpenAction(JTable table, JFrame jFrame) {
        super("Open", Icons.OPEN_ICON);
        this.recipeTable = table;
        this.jFrame = jFrame;
        putValue(SHORT_DESCRIPTION, "Opens recipe");
        putValue(MNEMONIC_KEY, KeyEvent.VK_O);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl O"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTabbedPane tabbedPane = (JTabbedPane) this.jFrame.getContentPane().getComponent(1);
        int currentTab = tabbedPane.getSelectedIndex();
        System.out.println("Open ---> " + tabbedPane.getTitleAt(currentTab) );
    }
}
