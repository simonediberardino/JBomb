package game.ui.viewelements.settings;

import game.events.RunnablePar;
import game.ui.helpers.BombermanTextFieldFilter;
import game.values.BomberColors;
import game.values.Dimensions;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class JBombInputField extends JTextField {
    private final static Color DEFAULT_TEXT_COLOR = Color.WHITE;
    private final static Color DEFAULT_MOUSE_HOVER_COLOR = BomberColors.RED;
    private final int fontSize;
    private Color textColor;
    private Color mouseHoverColor;

    public JBombInputField(String startText, Consumer<String> callback, int fontSize) {
        this(startText, callback, DEFAULT_TEXT_COLOR, DEFAULT_MOUSE_HOVER_COLOR, fontSize, -1);
    }

    public JBombInputField(String startText, Consumer<String> callback) {
        this(startText, callback, DEFAULT_TEXT_COLOR, DEFAULT_MOUSE_HOVER_COLOR, Dimensions.FONT_SIZE_MID, -1);
    }

    public JBombInputField(String startText, Consumer<String> callback, Color textColor, Color mouseHoverColor, int fontSize, int charLimit) {
        this.fontSize = fontSize;
        this.textColor = textColor;
        this.mouseHoverColor = mouseHoverColor;

        setOpaque(false);
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        setText(startText.toUpperCase());

        setHorizontalAlignment(JTextField.CENTER);
        addCaretListener(e -> callback.accept(getText()));
        ((AbstractDocument) getDocument()).setDocumentFilter(new BombermanTextFieldFilter(charLimit));
        setBorder(BorderFactory.createEmptyBorder());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(mouseHoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(textColor);
            }
        });

        setForeground(Color.WHITE);
        setFont(new Font(Font.MONOSPACED, Font.BOLD, fontSize));
    }

    public int getFontSize() {
        return fontSize;
    }

    public Color getTextColor() {
        return textColor;
    }

    public Color getMouseHoverColor() {
        return mouseHoverColor;
    }
}
