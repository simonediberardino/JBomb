package game.ui;

import java.awt.*;

public class Utility {
    public static int px(int dim) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) ((dim * screenSize.getWidth()) / Dimensions.DEFAULT_SCREEN_SIZE.getWidth());
    }
}
