package game.ui.elements;

import java.awt.*;

public class YellowButton extends BombermanButton{
    public YellowButton(String text) {
        super(text);
    }

    @Override
    public Color getBorderColor() {
        return Color.ORANGE;
    }

    @Override
    public Color getMouseHoverBackgroundColor() {
        return  new Color(255, 102, 0);
    }
}
