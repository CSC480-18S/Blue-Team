package Views;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;


public class BoardCellRenderer extends JLabel implements TableCellRenderer {
    public BoardCellRenderer() { super(); }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int rowIndex, int vColIndex) {
        try{
            setIcon((Icon) value);
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
        }catch (Exception e) {}
        return this;
    }
}
