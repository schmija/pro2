package remaster;


import javax.swing.*;
import java.awt.*;

public class InfoDialog extends JDialog {

    public InfoDialog(String article) {
        setLayout(new BorderLayout());
        add(new JLabel(article));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
