package game.presentation.ui.helpers;

import javax.swing.*;
import java.awt.*;

public class Padding extends JLabel {
    public Padding(int width, int height) {
        setOpaque(false);
        setPreferredSize(new Dimension(width, height));
    }
}
