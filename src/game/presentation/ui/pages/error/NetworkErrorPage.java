package game.presentation.ui.pages.error;

import game.localization.Localization;
import game.presentation.ui.elements.JBombLabelMultiLine;
import game.presentation.ui.frames.BombermanFrame;
import game.presentation.ui.panels.models.BoxMenuPanel;
import game.utils.file_system.Paths;

import javax.swing.*;
import java.awt.*;

public class NetworkErrorPage extends BoxMenuPanel {
    private static String error = "";


    public NetworkErrorPage(CardLayout cardLayout, JPanel parent, BombermanFrame frame) {
        super(cardLayout,
                parent, frame,
                Localization.get(Localization.GAME_ENDED),
                Paths.getMainMenuWallpaper(),
                true
        );
    }

    @Override
    protected int getBoxPanelWidth() {
        return 1000;
    }

    @Override
    protected void addCustomElements() {
        String error = NetworkErrorPage.error;

        if (error.isEmpty()) {
            error = Localization.get(Localization.GAME_ENDED);
        }

        int parentWidth = boxComponentsPanel.getPreferredSize().width;
        int margin = parentWidth / 4;
        int maxWidth = parentWidth - margin;

        JBombLabelMultiLine jLabel = new JBombLabelMultiLine(
                error,
                maxWidth
        );

        boxComponentsPanel.addComponent(jLabel);
    }

    public static void setError(String error) {
        NetworkErrorPage.error = error;
    }
}
