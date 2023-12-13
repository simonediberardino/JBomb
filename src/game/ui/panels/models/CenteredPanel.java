package game.ui.panels.models;

import javax.swing.*;
import java.awt.*;

public class CenteredPanel extends JPanel {
    @Override
    public Component add(Component comp) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension((int) getPreferredSize().getWidth(), (int) comp.getPreferredSize().getHeight()));
        panel.add(comp);
        return super.add(panel);
    }
}
