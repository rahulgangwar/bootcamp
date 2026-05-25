package com.example.swing;

import javax.swing.*;
import java.awt.*;

/**
 * The JPanel is a simplest container class. It provides space in which an application can attach
 * any other component. It inherits the JComponents class. It doesn't have title bar.
 */
public class JPanelExample {
  JPanelExample() {
    JFrame jFrame = new JFrame("Panel Example");
    JPanel panel = new JPanel();
    panel.setBounds(40, 80, 200, 200);
    panel.setBackground(Color.gray);

    JButton button1 = new JButton("Button 1");
    button1.setBounds(50, 100, 80, 30);
    button1.setBackground(Color.yellow);

    JButton button2 = new JButton("Button 2");
    button2.setBounds(100, 100, 80, 30);
    button2.setBackground(Color.green);

    panel.add(button1);
    panel.add(button2);

    jFrame.add(panel);
    jFrame.setSize(400, 400);
    jFrame.setLayout(null);
    jFrame.setVisible(true);
  }

  public static void main(String args[]) {
    new JPanelExample();
  }
}
