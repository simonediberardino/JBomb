package game.presentation.ui.viewelements.settings;

import game.domain.events.models.RunnablePar;
import game.presentation.ui.elements.JBombLabel;
import game.presentation.ui.elements.JBombLabelMultiLine;
import game.presentation.ui.helpers.BombermanTextFieldFilter;
import game.values.BomberColors;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JBombTextFieldTagged extends SettingsElementView {
    private final JBombLabel name;
    private final JTextField value;
    private final Color defaultColor = Color.WHITE;
    private final Color mouseHoverColor = BomberColors.RED;

    public JBombTextFieldTagged(JPanel gridPanel, String title, String startText, RunnablePar callback, int charLimit) {
        this(gridPanel, title, startText, callback, charLimit, new RunnablePar() {
            @Nullable
            @Override
            public <T> Object execute(T par) {
                return null;
            }
        });
    }

    public JBombTextFieldTagged(
            JPanel gridPanel,
            String title,
            String startText,
            RunnablePar callback,
            int charLimit,
            RunnablePar onClickCallback
    ) {
        super(gridPanel); // Call the constructor of the superclass and pass gridPanel as a parameter.
        name = new JBombLabel(title); // Create a new JLabel named "name" with the specified title and center alignment.
        value = new JTextField(1);

        value.setHorizontalAlignment(JTextField.CENTER);
        value.setOpaque(false); // Set the opaque property of the "value" text field to false, allowing the background to be transparent.
        value.setText(startText.toUpperCase());
        value.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("Focus");
                onClickCallback.execute(value);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        value.addCaretListener(e -> callback.execute(value.getText())); // Add a caret listener to the "value" text field that executes the "callback" function with the current text of the field when the caret position changes.
        ((AbstractDocument) value.getDocument()).setDocumentFilter(new BombermanTextFieldFilter(charLimit)); // Set a document filter on the "value" text field to enforce uppercase text input.
        value.setBorder(BorderFactory.createEmptyBorder());

        // Adding mouse listeners to the textfield;
        value.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                value.setForeground(mouseHoverColor); // Set the foreground color of the "value" text field to "mouseHoverColor" when the mouse enters.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                value.setForeground(defaultColor); // Set the foreground color of the "value" text field to "defaultColor" when the mouse exits.
            }
        });

        value.setForeground(Color.WHITE);
        value.setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE));

        add(name); // Add the "name" label to the SettingsElementView object.
        add(value); // Add the "value" text field to the SettingsElementView object.
    }

    public void updateText(String text) {
        value.setText(text.toUpperCase());
    }

    public JBombTextFieldTagged(JPanel gridPanel, String title, String startText, RunnablePar callback) {
        this(gridPanel, title, startText, callback, -1);
    }
}
