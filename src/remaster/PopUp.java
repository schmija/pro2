package remaster;

import javax.swing.*;

public class PopUp extends JPopupMenu {

    public PopUp(FeedPopUpListener listener) {
        JMenuItem menu1 = new JMenuItem("Nezobrazovat");
        JMenuItem menu2 = new JMenuItem("Nezobrazovat zdroj");
        JMenuItem menu3 = new JMenuItem("Smazat zdroj");

        menu1.addActionListener( a -> {
            listener.hideFeed();
        });

        menu2.addActionListener(a -> {
            listener.hideFeedSource();
        });

        menu3.addActionListener(a -> {
            listener.deleteFeesSource();
        });

        add(menu1);
        add(menu2);
        add(menu3);
    }
}
