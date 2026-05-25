package com.example.swing;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.EventListener;
import java.util.List;

public class EmployeeTableModel implements TableModel {
  private final List<Employee> employeeList;
  private final String[] columnNames = new String[] {"Id", "Name", "Hourly Rate", "Part Time"};
  private final Class[] columnClass =
      new Class[] {Integer.class, String.class, Double.class, Boolean.class};
  protected EventListenerList listenerList = new EventListenerList();

  public EmployeeTableModel(List<Employee> employeeList) {
    this.employeeList = employeeList;
  }

  @Override
  public String getColumnName(int column) {
    return columnNames[column];
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    return columnClass[columnIndex];
  }

  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public int getRowCount() {
    return employeeList.size();
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Employee row = employeeList.get(rowIndex);
    if (0 == columnIndex) {
      return row.getId();
    } else if (1 == columnIndex) {
      return row.getName();
    } else if (2 == columnIndex) {
      return row.getHourlyRate();
    } else if (3 == columnIndex) {
      return row.isPartTime();
    }
    return null;
  }

  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    System.out.println("EmployeeTableModel.setValueAt");
    System.out.println(
        "aValue = " + aValue + ", rowIndex = " + rowIndex + ", columnIndex = " + columnIndex);
    Employee row = employeeList.get(rowIndex);
    if (0 == columnIndex) {
      row.setId((Integer) aValue);
    } else if (1 == columnIndex) {
      row.setName((String) aValue);
    } else if (2 == columnIndex) {
      row.setHourlyRate((Double) aValue);
    } else if (3 == columnIndex) {
      row.setPartTime((Boolean) aValue);
    }
    System.out.println("Final List: " + employeeList);

    fireTableCellUpdated(rowIndex, columnIndex);
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return true;
  }

  public void addTableModelListener(TableModelListener l) {
    listenerList.add(TableModelListener.class, l);
  }

  public void removeTableModelListener(TableModelListener l) {
    listenerList.remove(TableModelListener.class, l);
  }

  public TableModelListener[] getTableModelListeners() {
    return listenerList.getListeners(TableModelListener.class);
  }

  public void fireTableDataChanged() {
    fireTableChanged(new TableModelEvent(this));
  }

  public void fireTableStructureChanged() {
    fireTableChanged(new TableModelEvent(this, TableModelEvent.HEADER_ROW));
  }

  public void fireTableRowsInserted(int firstRow, int lastRow) {
    fireTableChanged(
        new TableModelEvent(
            this, firstRow, lastRow, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
  }

  public void fireTableRowsUpdated(int firstRow, int lastRow) {
    fireTableChanged(
        new TableModelEvent(
            this, firstRow, lastRow, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE));
  }

  public void fireTableRowsDeleted(int firstRow, int lastRow) {
    fireTableChanged(
        new TableModelEvent(
            this, firstRow, lastRow, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
  }

  public void fireTableCellUpdated(int row, int column) {
    fireTableChanged(new TableModelEvent(this, row, row, column));
  }

  public void fireTableChanged(TableModelEvent e) {
    System.out.println("EmployeeTableModel.fireTableChanged");
    Object[] listeners = listenerList.getListenerList();
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == TableModelListener.class) {
        ((TableModelListener) listeners[i + 1]).tableChanged(e);
      }
    }
  }

  public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
    return listenerList.getListeners(listenerType);
  }
}
