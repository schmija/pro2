package ui;

import java.awt.BorderLayout;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import model.FeedItem;
import model.TableModel;
import model.ToDoItem;
import rss.RssItem;
import rss.RssParser;

public class ProFrame extends JFrame {

    static int width = 1400;
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

        setLayout(new BoxLayout(getContentPane(), 1));
        JPanel toolbar = new JPanel();
        add(toolbar, BorderLayout.NORTH);
        JPanel toolbar2 = new JPanel();
        add(toolbar2);

        JButton button = new JButton();
        button.setText("Přidat poznámku");
        toolbar.add(button);

        JButton saveButton = new JButton();
        saveButton.setText("Uložit");
        toolbar.add(saveButton);

        JButton loadButton = new JButton();
        loadButton.setText("Načíst");
        toolbar.add(loadButton);


        button.addActionListener(action -> {
            ToDoItem item = new ProDialog().getItem();
            model.add(item);
        });
        saveButton.addActionListener(action -> {
            saveItems();
        });
        loadButton.addActionListener(action -> {
            loadItems();
        });

        JTextField field = new JTextField("Vaše_URL_ADRESA");
        JButton loadUrlBtn = new JButton("Načíst URL");
        toolbar2.add(field);
        toolbar2.add(loadUrlBtn);
        loadUrlBtn.addActionListener(action -> {
            addFeed(field.getText());
        });

        model = new TableModel();

        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        pack();

        setLocationRelativeTo(null); //center okna na monitoru

        //parse();
        readFeeds();
    }

    private void parse(String url) {
        try {

            /*
            RssParser parser
                    = new RssParser(
                    new FileInputStream(
                            new File("download.xml")));
                            */

            //String url = "http://www.eurofotbal.cz/feed/rss/premier-league/";

            URLConnection connection = new URL(url).openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            RssParser parser = new RssParser(stream);

            List<RssItem> rssItems = parser.parseItems();
            for (RssItem rssItem : rssItems) {
                System.out.println(rssItem.toString());
            }
            stream.close();

        } catch (Exception e) {

        }
    }

    private void saveItems() {
        try {
            ObjectOutputStream stream =
                    new ObjectOutputStream(
                            new FileOutputStream(
                                    new File("our.db")
                            )
                    );
            stream.writeObject(model.getItems());
            stream.flush();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadItems() {
        try {
            ObjectInputStream stream = new ObjectInputStream(
                    new FileInputStream(
                            new File("our.db")
                    )
            );
            List<ToDoItem> items = (List<ToDoItem>) stream.readObject();
            stream.close();
            model.setItems(items);
            model.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addFeed(String url) {
        try {

            File file = new File("feed.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(url);
            writer.newLine();
            writer.flush();

        } catch (Exception e) {
        }
    }

    private void readFeeds(){ //časem List<String>
        try {
            List<String> urls = new ArrayList<>();
            File file = new File("feed.txt");

            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            while ((line = reader.readLine()) != null){
                urls.add(line);
            }
            for (String url : urls) { //test
                System.out.println(url);
            }

        } catch (Exception e){

        }
    }

    private List<FeedItem> getAllFeeds(){
        List<FeedItem> feedItems = new ArrayList<>();
        //parse ze souboru
        try {
            File file = new File("feedItems.csv");
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            bufferedReader.readLine(); //přeskočit první řádek
            String line;
            while ((line = bufferedReader.readLine()) != null){
                feedItems.add(FeedItem.parseFromCSV(line));
            }
        }catch (Exception e){

        }

        return feedItems;
    }

    private void saveAllFeeds(List<FeedItem> items){
        try {
            File file = new File("feedItems.csv");
            FileWriter writer = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write("url;addedMillis;shouldShow;alias");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            for (FeedItem item : items){
                bufferedWriter.write(item.toString());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }catch (Exception e){

        }
    }
}
