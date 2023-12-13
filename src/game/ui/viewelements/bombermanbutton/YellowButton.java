package game.ui.viewelements.bombermanbutton;

import game.values.BomberColors;

import java.awt.*;

public class YellowButton extends BombermanButton {
    public YellowButton(String text) {
        super(text);
    }

    public YellowButton(String text, int fontSize) {
        super(text, fontSize);
    }
    @Override
    public Color getBorderColor() {
        return BomberColors.ORANGE;
    }

    @Override
    public Color getMouseHoverBackgroundColor() {
        return new Color(255, 102, 0);
    }
}
