package remaster;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import model.FeedItem;
import rss.RssItem;
import rss.RssParser;
import utils.Utils;

public class RssFrame extends JFrame {

    private JPanel content;


    /*
        Zápočet domácí úkol:

        1) tvorba nového souboru blacklist (txt,CSV,.obj)
        - v něm bude podle klíče uloženo který článek necheme zobrazovat
        - vyvoláním popup menu na kartičce přes Nezobrazovat
        - v místech načítání a filtrování článků, tyto z blacklistu nezobrazovat

        2) filtry - nové dialogové okno s tabulkou (1 sloupec)
        - slovo - a v načítání článků pokud obsahuje toto slovo,
        pak článek zobrazujeme, jinak ne...
     *
     *
     */


    public static void main(String[] args) {
        RssFrame frame = new RssFrame();
        frame.init(800, 600);
    }

    public void init(int width, int height) {
        setSize(width, height);
        setTitle("Rss čtečka");
        setLocationRelativeTo(null); //center
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel controlPanel = new JPanel(new BorderLayout());
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(action -> {
            // načíst feed items
            List<FeedItem> items = Utils.getAllFeeds();
            // upravíme v dialogu
            new TableDialog(items).open();
            // a změny uložit
            Utils.saveAllFeeds(items);

            loadCards(); // fixme - toto
        });
        controlPanel.add(editButton, BorderLayout.WEST);

        add(controlPanel, BorderLayout.NORTH);

        content = new JPanel(new WrapLayout());

        // todo - async ?
        loadCards();

        add(new JScrollPane(content), BorderLayout.CENTER);

        setVisible(true);

    }

    public void loadCards() { // TODO async
        content.removeAll();
        List<RssItem> list = loadItems();
        for (RssItem rssItem : list) {
            content.add(new CardView(rssItem, this));
        }
        content.updateUI();
    }

    private List<RssItem> loadItems() {
        List<RssItem> list = new ArrayList<>();

        List<FeedItem> allFeeds = Utils.getAllFeeds();
        for (FeedItem feed : allFeeds) {
            if (feed.isShouldShow()) {
                loadFromFeedItem(list, feed);
            }
        }

        Collections.sort(list, (o1, o2) -> {
            long millis1 = Utils.getMillisFromDateString(o1.getPubDate());
            long millis2 = Utils.getMillisFromDateString(o2.getPubDate());
            return Long.compare(millis2, millis1);
        });
        // todo - sorting - comparator
        // todo - filtry - nastavitelné

        return list;
    }

    private void loadFromFeedItem(List<RssItem> items, FeedItem item) {
        // validace URL
        if (!item.getUrl().contains("http")) {
            return;
        }

        try {
            URLConnection conn = new URL(item.getUrl()).openConnection();
            List<RssItem> nowItems = new RssParser(conn.getInputStream()).parseItems();
            for (RssItem nowItem : nowItems){
                nowItem.setFeedItem(item);
            }
            items.addAll(nowItems);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
