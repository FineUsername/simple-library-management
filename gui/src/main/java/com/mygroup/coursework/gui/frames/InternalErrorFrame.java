package com.mygroup.coursework.gui.frames;

import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class InternalErrorFrame extends JFrame implements Runnable {

  @Override
  public void run() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(getMainPanel());
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  private JPanel getMainPanel() {
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    JLabel header = new JLabel("Internal error");
    header.setAlignmentX(Component.CENTER_ALIGNMENT);
    header.setBorder(new EmptyBorder(10, 10, 10, 10));
    mainPanel.add(header);

    JLabel centralLabel = new JLabel("Please try again later");
    centralLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    centralLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
    mainPanel.add(centralLabel);

    mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
    return mainPanel;
  }
}
