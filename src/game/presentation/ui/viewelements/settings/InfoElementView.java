package game.presentation.ui.viewelements.settings;

import game.presentation.ui.fonts.CustomFonts;
import game.utils.Utility;

import javax.swing.*;
import java.awt.*;

public class InfoElementView extends SettingsElementView {
    private final JLabel name;
    private final JLabel value;

    public InfoElementView(JPanel gridPanel, String title, String val) {
        super(gridPanel);
        // Creating two JLabels to display the title and value
        name = new JLabel(title, SwingConstants.CENTER);
        value = new JLabel(val, SwingConstants.RIGHT);

        // Setting foreground color of the labels to white
        name.setForeground(Color.WHITE);
        value.setForeground(Color.WHITE);

        // Setting font for the labels
        name.setFont(CustomFonts.getFont(CustomFonts.FONT_PIXELATED, FONT_SIZE));
        value.setFont(CustomFonts.getFont(CustomFonts.FONT_PIXELATED, FONT_SIZE));

        // Adding name and value labels to the SettingsElementView object
        add(name);
        add(value);
    }
}
