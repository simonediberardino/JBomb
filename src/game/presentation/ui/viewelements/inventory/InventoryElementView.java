package game.presentation.ui.viewelements.inventory;

import game.values.Dimensions;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class InventoryElementView extends JPanel {
    public static int SIZE = Dimensions.DEFAULT_INVENTORY_ICON_SIZE;
    private final int fontSize = Dimensions.FONT_SIZE_MID;
    private final JLabel icon;
    private final JLabel occurrents;
    private final Border border = BorderFactory.createEmptyBorder(
            Dimensions.DEFAULT_Y_PADDING,
            Dimensions.DEFAULT_Y_PADDING,
            Dimensions.DEFAULT_Y_PADDING,
            Dimensions.DEFAULT_Y_PADDING
    );

    public InventoryElementView() {
        icon = new JLabel();
        occurrents = new JLabel("", SwingConstants.CENTER);
        occurrents.setFont(new Font(Font.MONOSPACED, Font.BOLD, fontSize));
        occurrents.setBorder(border);
        setBorder(border);
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