package remaster;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class FeedPopup extends JPopupMenu {

    public FeedPopup(FeedPopupListener listener) {

        JMenuItem menu1 = new JMenuItem("Nezobrazovat");
        JMenuItem menu2 = new JMenuItem("Nezobrzovat zdroj");
        JMenuItem menu3 = new JMenuItem("Smazat zdroj");

        menu1.addActionListener(a -> {
            listener.hideFeed();
        });
        menu2.addActionListener(a -> {
            listener.hideFeedSource();
        });
        menu3.addActionListener(a -> {
            listener.deleteFeedSource();
        });

        add(menu1);
        add(menu2);
        add(menu3);
    }
}
