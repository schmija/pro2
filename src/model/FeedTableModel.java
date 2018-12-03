package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class FeedTableModel extends AbstractTableModel {

    String[] columns = new String[]{"Url", "Show"};
    List<FeedItem> items;

    public FeedTableModel() {
        items = new ArrayList<>();
    }

    // todo constructor se vstupem items
    public void add(FeedItem item) {
        items.add(item);
        fireTableDataChanged();
    }

    public void remove(int position) {
        if (position < 0 || position >= items.size()) {
            return;
        }
        items.remove(position);
        fireTableDataChanged();
    }

    public void setItems(List<FeedItem> items) {
        this.items = items;
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return items.get(rowIndex).getUrl();
            case 1:
                return items.get(rowIndex).isShouldShow();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                items.get(rowIndex).setUrl((String) aValue);
                break;
            case 1:
                items.get(rowIndex).setShouldShow((Boolean) aValue);
                break;
        }
        super.setValueAt(aValue, rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Boolean.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
        //return columnIndex == 0 || columnIndex == 1; // nebo return true;
    }
}
