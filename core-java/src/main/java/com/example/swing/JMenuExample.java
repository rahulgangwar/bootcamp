package com.example.swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JMenuExample {
  JFrame frame;
  JMenuBar menuBar;
  JMenu fileMenu, editMenu, helpMenu;
  JMenuItem cutMenuItem, copyMenuItem, pasteMenuItem, selectAllMenuItem;
  JTextArea textArea;

  JMenuExample() {
    frame = new JFrame();
    cutMenuItem = new JMenuItem("cut");
    copyMenuItem = new JMenuItem("copy");
    pasteMenuItem = new JMenuItem("paste");
    selectAllMenuItem = new JMenuItem("selectAll");

    TextActionListener listener = new TextActionListener();

    cutMenuItem.addActionListener(listener);
    copyMenuItem.addActionListener(listener);
    pasteMenuItem.addActionListener(listener);
    selectAllMenuItem.addActionListener(listener);

    menuBar = new JMenuBar();
    fileMenu = new JMenu("File");
    editMenu = new JMenu("Edit");
    helpMenu = new JMenu("Help");

    editMenu.add(cutMenuItem);
    editMenu.add(copyMenuItem);
    editMenu.add(pasteMenuItem);
    editMenu.add(selectAllMenuItem);

    menuBar.add(fileMenu);
    menuBar.add(editMenu);
    menuBar.add(helpMenu);

    textArea = new JTextArea();
    textArea.setBounds(5, 5, 360, 320);

    frame.add(menuBar);
    frame.add(textArea);

    frame.setJMenuBar(menuBar);
    frame.setLayout(null);
    frame.setSize(400, 400);
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    new JMenuExample();
  }

  private class TextActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == cutMenuItem) textArea.cut();
      if (e.getSource() == pasteMenuItem) textArea.paste();
      if (e.getSource() == copyMenuItem) textArea.copy();
      if (e.getSource() == selectAllMenuItem) textArea.selectAll();
    }
  }
}
