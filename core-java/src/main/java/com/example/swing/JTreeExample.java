package com.example.swing;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class JTreeExample {
  JFrame jFrame;

  JTreeExample() {
    jFrame = new JFrame();
    DefaultMutableTreeNode style = new DefaultMutableTreeNode("Style");
    DefaultMutableTreeNode color = new DefaultMutableTreeNode("Color");
    DefaultMutableTreeNode font = new DefaultMutableTreeNode("Font");

    style.add(color);
    style.add(font);

    color.add(new DefaultMutableTreeNode("red"));
    color.add(new DefaultMutableTreeNode("blue"));

    font.add(new DefaultMutableTreeNode("10"));
    font.add(new DefaultMutableTreeNode("20"));

    JTree jTree = new JTree(style);
    jFrame.add(jTree);
    jFrame.setSize(200, 200);
    jFrame.setVisible(true);
  }

  public static void main(String[] args) {
    new JTreeExample();
  }
}
