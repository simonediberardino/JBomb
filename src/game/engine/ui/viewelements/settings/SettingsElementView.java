package game.engine.ui.viewelements.settings;

import game.values.Dimensions;

import javax.swing.*;
import java.awt.*;

import static game.values.Dimensions.DEFAULT_Y_PADDING;

public abstract class SettingsElementView extends JPanel {
    protected final int FONT_SIZE = Dimensions.FONT_SIZE_MID;
    protected final int LEFT_PADDING = DEFAULT_Y_PADDING * 3;
    protected final int RIGHT_PADDING = DEFAULT_Y_PADDING * 10;

    protected SettingsElementView(JPanel gridPanel) {
        // Setting empty border for the SettingsElementView object
        setBorder(BorderFactory.createEmptyBorder(DEFAULT_Y_PADDING, LEFT_PADDING, DEFAULT_Y_PADDING, RIGHT_PADDING));

        // Setting layout for the SettingsElementView object
        // GridLayout with 0 rows and 2 columns
        setLayout(new GridLayout(0, 2));

        // Setting preferred size of the SettingsElementView object
        // Same width as gridPanel and height as font size plus default padding
        setPreferredSize(new Dimension((int) (gridPanel.getPreferredSize().getWidth()), FONT_SIZE + DEFAULT_Y_PADDING));

        setOpaque(false);
    }
}
