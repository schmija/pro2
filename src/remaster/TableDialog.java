package remaster;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.FeedItem;
import model.FeedTableModel;
import model.TableModel;

public class TableDialog extends JDialog {

    FeedTableModel model;

    public TableDialog(List<FeedItem> feedItems){
        setModal(true);
        setLayout(new BorderLayout());

        JPanel toolbar = new JPanel();
        // TODO přidat tlačítka na přidávání feedů
        // TODO TextField apod
        // TODO po kliknutí na přidat vyčistit textové pole

        JButton finishBtn = new JButton("Dokončit");
        toolbar.add(finishBtn, BorderLayout.EAST);

        JTextField text = new JTextField();
        toolbar.add(text, BorderLayout.CENTER);
        text.setPreferredSize(new Dimension(300, 30));

        JPanel btnLayout = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Přidat");
        JButton rmvBtn = new JButton("Smazat");
        btnLayout.add(addBtn);
        btnLayout.add(rmvBtn);
        toolbar.add(btnLayout, BorderLayout.WEST);
        addBtn.addActionListener(action -> {
            model.add(new FeedItem(text.getText()));
            text.setText("");
        });

        add(toolbar, BorderLayout.NORTH);

        model = new FeedTableModel();
        model.setItems(feedItems);
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        rmvBtn.addActionListener(a -> {
            model.remove(table.getSelectedRow());
        });

        finishBtn.addActionListener(action -> {
            setVisible(false);
        });

        pack();
        setLocationRelativeTo(null);
    }

    public void open(){
        // todo v budoucnu navracovat co se tu stalo do frame
        setVisible(true);
    }
}
