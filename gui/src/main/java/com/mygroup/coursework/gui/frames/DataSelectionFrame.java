package com.mygroup.coursework.gui.frames;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.mygroup.coursework.gui.DataType;
import com.mygroup.coursework.gui.exceptions.UnknownCommandException;

public class DataSelectionFrame extends JFrame implements ActionListener, Runnable {
  private static final int VERTICAL_GAP = 5;
  private static final Map<String, DataType> TYPE_NAMES_FOR_BUTTONS = new HashMap<>();
  static {
    TYPE_NAMES_FOR_BUTTONS.put("Books", DataType.BOOK);
    TYPE_NAMES_FOR_BUTTONS.put("Journal", DataType.JOURNAL_ENTRY);
    TYPE_NAMES_FOR_BUTTONS.put("BookTypes", DataType.BOOK_TYPE);
    TYPE_NAMES_FOR_BUTTONS.put("Clients", DataType.CLIENT);
  }
  private DataType selectedType;

  @Override
  public void run() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(getMainPanel());
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  public DataType getSelectedData() {
    return selectedType;
  }

  public boolean hasSelectedData() {
    return selectedType != null;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    synchronized (this) {
      selectedType = TYPE_NAMES_FOR_BUTTONS.get(command);
      if (selectedType == null) {
        throw new UnknownCommandException(
            String.format("Unknown command %s in data selection frame", command));
      }
      notify();
    }
  }

  private JPanel getMainPanel() {
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    addHeader(mainPanel);
    addButtons(mainPanel);
    mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    return mainPanel;
  }

  private void addHeader(JPanel panel) {
    JLabel label = new JLabel("Choose what data you want to access");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(label);
    panel.add(Box.createRigidArea(new Dimension(0, VERTICAL_GAP)));
  }

  private void addButtons(JPanel panel) {
    JButton booksButton = new JButton("Books");
    JButton journalButton = new JButton("Journal");
    JButton clientsButton = new JButton("Clients");
    JButton bookTypesButton = new JButton("BookTypes");

    booksButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    journalButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    clientsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    bookTypesButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    booksButton.setActionCommand("Books");
    journalButton.setActionCommand("Journal");
    clientsButton.setActionCommand("Clients");
    bookTypesButton.setActionCommand("BookTypes");

    booksButton.addActionListener(this);
    journalButton.addActionListener(this);
    clientsButton.addActionListener(this);
    bookTypesButton.addActionListener(this);

    panel.add(booksButton);
    panel.add(Box.createRigidArea(new Dimension(0, VERTICAL_GAP)));
    panel.add(journalButton);
    panel.add(Box.createRigidArea(new Dimension(0, VERTICAL_GAP)));
    panel.add(clientsButton);
    panel.add(Box.createRigidArea(new Dimension(0, VERTICAL_GAP)));
    panel.add(bookTypesButton);
    panel.add(Box.createRigidArea(new Dimension(0, VERTICAL_GAP)));
  }

}
