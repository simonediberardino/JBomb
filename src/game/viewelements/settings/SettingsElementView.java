package game.viewelements.settings;

import game.values.Dimensions;
import game.utils.Utility;

import javax.swing.*;
import java.awt.*;

import static game.values.Dimensions.*;

public class SettingsElementView extends JPanel {
    public static final int FONT_SIZE = Utility.px(40);
    private static final int LEFT_PADDING = DEFAULT_PADDING * 3;
    private static final int RIGHT_PADDING = DEFAULT_PADDING * 10;

    private final JLabel name;
    private final JLabel value;

    public SettingsElementView(JPanel gridPanel, String title, String val) {
        // Creating two JLabels to display the title and value
        name = new JLabel(title, SwingConstants.CENTER);
        value = new JLabel(val, SwingConstants.RIGHT);

        // Setting foreground color of the labels to white
        name.setForeground(Color.WHITE);
        value.setForeground(Color.WHITE);

        // Setting empty border for the labels
        name.setBorder(BorderFactory.createEmptyBorder(0, LEFT_PADDING,0, 0));
        value.setBorder(BorderFactory.createEmptyBorder(0, 0,0, RIGHT_PADDING));

        // Setting font for the labels
        name.setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE));
        value.setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE));

        // Setting empty border for the SettingsElementView object
        setBorder(BorderFactory.createEmptyBorder(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));

        // Setting layout for the SettingsElementView object
        // GridLayout with 0 rows and 2 columns
        setLayout(new GridLayout(0, 2));

        // Setting preferred size of the SettingsElementView object
        // Same width as gridPanel and height as font size plus default padding
        setPreferredSize(new Dimension((int) (gridPanel.getPreferredSize().getWidth()), FONT_SIZE + DEFAULT_PADDING));

        // Adding name and value labels to the SettingsElementView object
        add(name);
        add(value);

        // Making the SettingsElementView object opaque (transparent) so that the background shows
        setOpaque(false);
    }
}
