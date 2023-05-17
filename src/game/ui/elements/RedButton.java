package game.ui.elements;

import java.awt.*;

public class RedButton extends BombermanButton{
    public RedButton(String text) {
        super(text);
    }

    @Override
    public Color getBorderColor() {
        return new Color(255, 87, 51);
    }

    @Override
    public Color getMouseHoverBackgroundColor() {
        return Color.RED;
    }
}
