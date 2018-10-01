package ui;

import model.TableModel;
import model.ToDoItem;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;


public class ProFrame extends JFrame {

    static int width = 800;
    static int height = 600;
    private TableModel model;

    public static void main(String... args) {
        ProFrame proFrame = new ProFrame();
        proFrame.init(width, height);
    }

    private void init(int width, int height) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(width, height);
        setTitle("Programování 2");

        JPanel toolBar = new JPanel();
        add(toolBar, BorderLayout.NORTH);

        JButton button = new JButton();
        button.setText("Přidat poznámku");
        toolBar.add(button);

        JButton saveButton = new JButton();
        saveButton.setText("Uložit");
        toolBar.add(saveButton);

        JButton loadButton = new JButton();
        loadButton.setText("Načíst");
        toolBar.add(loadButton);


        button.addActionListener(action -> {
            ToDoItem item = new ToDoItem("Test obsah");
            model.add(item);
        });

        saveButton.addActionListener(action -> {
            saveItems();
        });

        loadButton.addActionListener(action -> {
            loadItems();
        });

        model = new TableModel();

        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        pack();

        setLocationRelativeTo(null); //center monitoru
    }

    private void saveItems() {
        try {

            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(new File("our.db")));
            stream.writeObject(model.getItems());
            stream.flush();
            stream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadItems() {
        try {

            ObjectInputStream stream = new ObjectInputStream(new FileInputStream(new File("our.db")));
            List<ToDoItem> items = (List<ToDoItem>) stream.readObject();
            stream.close();
            model.setItems(items);
            model.fireTableDataChanged();
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }
}
