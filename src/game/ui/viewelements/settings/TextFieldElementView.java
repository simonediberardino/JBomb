package game.ui.viewelements.settings;

import game.models.RunnablePar;
import game.ui.helpers.UppercaseDocumentsFilter;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class TextFieldElementView extends SettingsElementView{
    private final JLabel name;
    private final JTextField value;
    private final RunnablePar callback;

    public TextFieldElementView(JPanel gridPanel, String title, String startText, RunnablePar callback) {
        super(gridPanel);
        // Creating two JLabels to display the title and value
        name = new JLabel(title, SwingConstants.CENTER);
        value = new JTextField( 20);

        this.callback = callback;

        value.setOpaque(false);
        value.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        value.setText(startText.toUpperCase());
        value.addCaretListener(e -> callback.execute(value.getText()));
        ((AbstractDocument) value.getDocument()).setDocumentFilter(new UppercaseDocumentsFilter());
        value.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Setting foreground color of the labels to white
        name.setForeground(Color.WHITE);
        value.setForeground(Color.WHITE);
        // Setting font for the labels
        name.setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE));
        value.setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE));

        // Adding name and value labels to the SettingsElementView object
        add(name);
        add(value);
    }
}
