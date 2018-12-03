package remaster;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class InfoDialog extends JDialog {

    public InfoDialog(String article) {
        //setModal(true)
        setLayout(new BorderLayout());
        add(new JLabel(article));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
