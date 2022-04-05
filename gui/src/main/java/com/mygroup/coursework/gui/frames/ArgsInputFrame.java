package com.mygroup.coursework.gui.frames;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import com.mygroup.coursework.gui.DataType;
import com.mygroup.coursework.gui.InputFieldAssembler;
import com.mygroup.coursework.gui.net.Request;

public class ArgsInputFrame extends JFrame implements Runnable {
  private final Request selectedRequest;
  private final DataType selectedType;
  private Map<String, String> args;

  public ArgsInputFrame(Request selectedRequest, DataType selectedData) {
    this.selectedRequest = selectedRequest;
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

  public boolean hasArgs() {
    return args != null;
  }

  public Map<String, String> getArgs() {
    return args;
  }

  private JPanel getMainPanel() {
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    addHeader(mainPanel);
    JPanel textPanel = new JPanel();
    addTextPanel(textPanel, mainPanel);
    mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
    return mainPanel;
  }

  private void addHeader(JPanel panel) {
    JLabel label = new JLabel("Enter arguments for request");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    label.setBorder(new EmptyBorder(10, 10, 10, 10));
    panel.add(label);
  }

  private void addTextPanel(JPanel textPanel, JPanel mainPanel) {

    List<JTextField> textFields = new ArrayList<>();
    InputFieldAssembler assembler = new InputFieldAssembler(selectedType.getType());
    List<Field> neededFields = assembler.getAllFieldsForRequest(selectedRequest);

    GridLayout layout = new GridLayout(neededFields.size(), 2, 0, 10);
    textPanel.setLayout(layout);

    neededFields.forEach(field -> {
      JTextField textField = new JTextField();
      textField.setName(assembler.getJsonName(field));
      JLabel label = new JLabel(assembler.getAnnotatedName(field));
      label.setLabelFor(textField);
      textFields.add(textField);
      textPanel.add(label);
      textPanel.add(textField);
    });

    JButton submitButton = new JButton("Submit request");
    submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    submitButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        synchronized (ArgsInputFrame.this) {
          args = textFields.stream()
              .collect(Collectors.toMap(JTextField::getName, JTextField::getText));
          ArgsInputFrame.this.notify();
        }
      }

    });
    mainPanel.add(textPanel);
    mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    mainPanel.add(submitButton);
  }

}
