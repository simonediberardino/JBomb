package game.ui.elements;

import game.utils.Dimensions;
import game.utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class InventoryElementView extends JPanel {
    public static int SIZE = Dimensions.DEFAULT_INVENTORY_ICON_SIZE;
    private final int fontSize = Utility.px(30);
    private JLabel icon;
    private JLabel occurrents;

    public InventoryElementView() {
        icon = new JLabel();
        occurrents = new JLabel("", SwingConstants.CENTER);
        occurrents.setFont(new Font(Font.MONOSPACED, Font.BOLD, fontSize));

        setLayout(new GridLayout(1, 2));
        add(icon);
        add(occurrents);
        setOpaque(false);
    }

    public void setIcon(ImageIcon imageIcon) {
        icon.setIcon(imageIcon);
    }

    public void setNumItems(int numItems) {
        occurrents.setText(String.valueOf(numItems));
    }

}