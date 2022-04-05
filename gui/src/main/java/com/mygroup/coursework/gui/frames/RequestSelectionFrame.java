package com.mygroup.coursework.gui.frames;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mygroup.coursework.gui.DataType;
import com.mygroup.coursework.gui.net.Request;

public class RequestSelectionFrame extends JFrame implements ActionListener, Runnable {
  private static final int VERTICAL_GAP = 5;
  private final DataType selectedType;
  private Request selectedRequest;

  public RequestSelectionFrame(DataType selectedData) {
    this.selectedType = selectedData;
  }

  @Override
  public void run() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(getMainPanel());
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    synchronized (this) {
      selectedRequest = Request.valueOf(e.getActionCommand());
      notify();
    }
  }

  public Request getSelectedRequest() {
    return selectedRequest;
  }

  public boolean hasSelectedRequest() {
    return selectedRequest != null;
  }

  private JPanel getMainPanel() {
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    addLabel(mainPanel);
    addButtons(mainPanel);
    mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    return mainPanel;
  }

  private void addLabel(JPanel panel) {
    JLabel label = new JLabel("Choose what you want to do with data");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(label);
    panel.add(Box.createRigidArea(new Dimension(0, VERTICAL_GAP)));
  }

  private void addButtons(JPanel panel) {
    selectedType.getSupportedRequests().forEach(request -> {
      JButton button = new JButton(request.toString().replace('_', ' '));
      button.setAlignmentX(Component.CENTER_ALIGNMENT);
      button.setActionCommand(request.toString());
      button.addActionListener(this);
      panel.add(button);
      panel.add(Box.createRigidArea(new Dimension(0, VERTICAL_GAP)));
    });
  }

}
