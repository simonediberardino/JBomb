package game.ui.helpers;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class UppercaseDocumentsFilter extends DocumentFilter {
    @Override
    public void insertString(final FilterBypass fb, final int offset, final String string, final AttributeSet attr) throws BadLocationException {
        super.insertString(fb, offset, string.toUpperCase(), attr);
    }

    @Override
    public void replace(final FilterBypass fb, final int offset, final int length, final String text, final AttributeSet attrs) throws BadLocationException {
        super.replace(fb, offset, length, text.toUpperCase(), attrs);
    }
}