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

public class RequestResultFrame extends JFrame implements Runnable {
  private static final int VERTICAL_GAP = 5;
  private final int statusCode;
  private final String result;
  private boolean wantNewRequest;

  public RequestResultFrame(int statusCode, String result) {
    this.statusCode = statusCode;
    this.result = result;
    wantNewRequest = false;
  }

  @Override
  public void run() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(getMainPanel());
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  public boolean wantsNewRequest() {
    return wantNewRequest;
  }

  private JPanel getMainPanel() {
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    addHeader(mainPanel);
    addMainPanel(mainPanel);
    mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    return mainPanel;
  }

  private void addHeader(JPanel panel) {
    JLabel label = new JLabel("Server's answer:");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(label);
    panel.add(Box.createRigidArea(new Dimension(0, VERTICAL_GAP)));
  }

  private void addMainPanel(JPanel panel) {
    JLabel statusCodeLabel = new JLabel(String.valueOf(statusCode));
    statusCodeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(statusCodeLabel);
    panel.add(Box.createRigidArea(new Dimension(0, VERTICAL_GAP)));
    String[] results = splitResultIntoLines();
    for (int i = 0; i < results.length; i++) {
      JLabel label = new JLabel(results[i]);
      label.setAlignmentX(Component.CENTER_ALIGNMENT);
      panel.add(label);
      panel.add(Box.createRigidArea(new Dimension(0, VERTICAL_GAP)));
    }
    JButton newRequestButton = new JButton("Create new request");
    newRequestButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    newRequestButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        synchronized (RequestResultFrame.this) {
          wantNewRequest = true;
          RequestResultFrame.this.notify();
        }
      }

    });
    panel.add(newRequestButton);
    panel.add(Box.createRigidArea(new Dimension(0, VERTICAL_GAP)));
  }

  private String[] splitResultIntoLines() {
    return result.replace("[{", "").replace("}]", "").split("\\},\\{");
  }

}
