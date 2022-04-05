package com.mygroup.coursework.gui.frames;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SignUpFrame extends JFrame implements Runnable {
  private String username;
  private String password;
  private boolean hasInput = false;

  public boolean hasInput() {
    return hasInput;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public void displayUserAlreadyExists() {
    JOptionPane.showMessageDialog(this, "Username already exists", "Sign up failed",
        JOptionPane.ERROR_MESSAGE);
  }

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
    addLabel(mainPanel);
    addInputPanel(mainPanel);
    mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
    return mainPanel;
  }

  private void addLabel(JPanel panel) {
    JLabel label = new JLabel("Sign up");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    label.setBorder(new EmptyBorder(10, 10, 10, 10));
    panel.add(label);
  }

  private void addInputPanel(JPanel mainPanel) {
    JPanel inputPanel = new JPanel();
    JTextField usernameField = new JTextField();
    JTextField passwordField = new JTextField();
    JTextField passwordConfirmationField = new JTextField();
    JLabel usernameLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JLabel passwordConfirmationLabel = new JLabel("Confirm password");

    GridLayout layout = new GridLayout(3, 2, 0, 10);
    inputPanel.setLayout(layout);
    inputPanel.add(usernameLabel);
    inputPanel.add(usernameField);
    inputPanel.add(passwordLabel);
    inputPanel.add(passwordField);
    inputPanel.add(passwordConfirmationLabel);
    inputPanel.add(passwordConfirmationField);

    JButton signUpButton = new JButton("Sign up");
    signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    signUpButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        synchronized (SignUpFrame.this) {
          username = usernameField.getText();
          password = passwordField.getText();
          String passwordConfirmation = passwordConfirmationField.getText();
          if (!passwordConfirmation.equals(password)) {
            displayPasswordsDontMatch();
            return;
          }
          hasInput = true;
          SignUpFrame.this.notify();
        }
      }

    });
    mainPanel.add(inputPanel);
    mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    mainPanel.add(signUpButton);
  }

  private void displayPasswordsDontMatch() {
    JOptionPane.showMessageDialog(this, "Passwords dont match", "Sign up failed",
        JOptionPane.ERROR_MESSAGE);
  }

}
