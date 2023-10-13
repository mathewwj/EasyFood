package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public final class AddAction extends AbstractAction {

    private JTable table;
    private final JFrame jFrame;

    public AddAction(JTable table, JFrame jFrame) {
        super("Add", Icons.ADD_ICON);
        this.table = table;
        this.jFrame = jFrame;
        putValue(SHORT_DESCRIPTION, "Adds new recipe");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTabbedPane tabbedPane = (JTabbedPane) this.jFrame.getContentPane().getComponent(1);
        int currentTab = tabbedPane.getSelectedIndex();
        JPanel panel = (JPanel) tabbedPane.getComponentAt(currentTab);
        JScrollPane scrollPane = (JScrollPane) panel.getComponent(0);
        this.table = (JTable) scrollPane.getViewport().getView();
        System.out.println("Add ---> " + tabbedPane.getTitleAt(currentTab) );
    }
}
