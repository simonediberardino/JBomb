package game.presentation.ui.pages;

import game.presentation.ui.frames.JBombFrame;
import game.presentation.ui.panels.menu.avatar.AvatarMenuPanelFactory;
import game.presentation.ui.panels.menu.username.UsernameMenuPanelFactory;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractMainMenuPanel extends BaseMenu {
    public AbstractMainMenuPanel(CardLayout cardLayout, JPanel parent, JBombFrame frame) {
        super(cardLayout, parent, frame);
    }

    @Override
    protected JPanel getRightPanel() {
        return new AvatarMenuPanelFactory().build();
    }

    @Override
    protected JPanel getLeftPanel() {
        return new UsernameMenuPanelFactory().build();
    }
}