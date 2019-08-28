package model;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MessageTableDynamique extends AbstractTableModel {
    private final List<Message> messages = new ArrayList<Message>();

    private final String[] entetes = {"IsLast", "Content"};

    public MessageTableDynamique(ArrayList<Message> list) {
        super();
        for (int i = 0; i<list.size();i++){
            messages.add(list.get(i));
        }


    }

    public int getRowCount() {
        return messages.size();
    }

    public int getColumnCount() {
        return entetes.length;
    }

    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return messages.get(rowIndex).getIsLast();
            case 1:
                return messages.get(rowIndex).getContent();

            default:
                return null; //Ne devrait jamais arriver
        }
    }

    public void addMessage(Message ami) {
        messages.add(ami);

        fireTableRowsInserted(messages.size() -1, messages.size() -1);
    }

    public void removeMessage(int rowIndex) {
        messages.remove(rowIndex);

        fireTableRowsDeleted(rowIndex, rowIndex);
    }
}
