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

public class SignInFrame extends JFrame implements Runnable {

  private String username;
  private String password;
  private boolean hasInput = false;
  private boolean choseSignUp = false;

  public boolean hasInput() {
    return hasInput;
  }

  public boolean choseSignUp() {
    return choseSignUp;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public void displayInvalidCredentials() {
    JOptionPane.showMessageDialog(this, "Invalid username or password", "Sign in failed",
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
    JLabel label = new JLabel("Sign in");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    label.setBorder(new EmptyBorder(10, 10, 10, 10));
    panel.add(label);
  }

  private void addInputPanel(JPanel mainPanel) {
    JPanel inputPanel = new JPanel();
    JTextField usernameField = new JTextField();
    JTextField passwordField = new JTextField();
    JLabel usernameLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");

    GridLayout layout = new GridLayout(2, 2, 0, 10);
    inputPanel.setLayout(layout);
    inputPanel.add(usernameLabel);
    inputPanel.add(usernameField);
    inputPanel.add(passwordLabel);
    inputPanel.add(passwordField);

    JButton signInButton = new JButton("Sign in");
    signInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    signInButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        synchronized (SignInFrame.this) {
          hasInput = true;
          username = usernameField.getText();
          password = passwordField.getText();
          SignInFrame.this.notify();
        }
      }

    });

    JButton signUpButton = new JButton("Sign up");
    signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    signUpButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        synchronized (SignInFrame.this) {
          choseSignUp = true;
          SignInFrame.this.notify();
        }
      }

    });
    mainPanel.add(inputPanel);
    mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    mainPanel.add(signInButton);
    mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    mainPanel.add(signUpButton);
  }

}
