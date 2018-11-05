package remaster;

import model.TableModel;

import javax.swing.*;
import java.awt.*;

public class TableDialog extends JDialog {

    public TableDialog(){
        setModal(true);
        setLayout(new BorderLayout());


        JPanel toolbar = new JPanel();
        //TODO přidat tlačítka na přidávání feedů, TextField, atd.
        JButton finishButton = new JButton("Dokončit");
        toolbar.add(finishButton, BorderLayout.EAST);
        add(toolbar, BorderLayout.NORTH);

        TableModel model = new TableModel();
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        finishButton.addActionListener(action -> {
            setVisible(false);
        });

        pack();
        setLocationRelativeTo(null);
    }

    public void open(){
        //TODO v budeoucnu nárat, co se stalo v dialogu
        setVisible(true);
    }
}
