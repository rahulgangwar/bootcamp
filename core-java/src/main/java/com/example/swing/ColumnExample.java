package com.example.swing;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

public class ColumnExample extends JFrame {

  public ColumnExample() {
    super("Abstract Model JTable Test");
    setSize(300, 200);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    DefaultTableModel dtm =
        new DefaultTableModel(
            new String[][] {{"1", "2", "3"}, {"4", "5", "6"}},
            new String[] {"Names", "In", "Order"});
    SortingColumnModel scm = new SortingColumnModel();
    JTable jt = new JTable(dtm, scm);
    jt.createDefaultColumnsFromModel();

    JScrollPane jsp = new JScrollPane(jt);
    getContentPane().add(jsp, BorderLayout.CENTER);
  }

  public static void main(String args[]) {
    ColumnExample ce = new ColumnExample();
    ce.setVisible(true);
  }
}

class SortingColumnModel extends DefaultTableColumnModel {

  public void addColumn(TableColumn tc) {
    super.addColumn(tc);
    int newIndex = sortedIndexOf(tc);
    if (newIndex != tc.getModelIndex()) {
      moveColumn(tc.getModelIndex(), newIndex);
    }
  }

  protected int sortedIndexOf(TableColumn tc) {
    // just do a linear search for now
    int stop = getColumnCount();
    String name = tc.getHeaderValue().toString();

    for (int i = 0; i < stop; i++) {
      if (name.compareTo(getColumn(i).getHeaderValue().toString()) <= 0) {
        return i;
      }
    }
    return stop;
  }
}
