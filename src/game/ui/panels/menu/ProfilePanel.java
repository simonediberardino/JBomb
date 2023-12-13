package game.ui.panels.menu;

import game.data.DataInputOutput;
import game.events.RunnablePar;
import game.localization.Localization;
import game.ui.frames.BombermanFrame;
import game.ui.panels.models.BoxMenuPanel;
import game.ui.viewelements.settings.SettingsElementView;
import game.utils.Utility;

import javax.swing.*;
import java.awt.*;

import static game.localization.Localization.MY_BOMBERMAN;
import static game.localization.Localization.USERNAME;

public class ProfilePanel extends BoxMenuPanel {
    /**
     * Constructs a ProfilePanel object.
     *
     * @param cardLayout the CardLayout of the parent container
     * @param parent     the parent container of this panel
     * @param frame      the BombermanFrame object
     */
    public ProfilePanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame) {
        super(cardLayout, parent, frame, Localization.get(MY_BOMBERMAN));
    }

    @Override
    protected int getBoxPanelWidth() {
        return Utility.px(800);
    }

    @Override
    protected void addCustomElements() {
        SettingsElementView userName = boxComponentsPanel.addTextFieldElementView(Localization.get(USERNAME), DataInputOutput.getInstance().getUsername(), new RunnablePar() {
            @Override
            public <T> Object execute(T par) {
                if (par.toString().isBlank()) return null;
                DataInputOutput.getInstance().setUsername(par.toString().trim());
                return null;
            }
        });

        SettingsElementView killsElement = boxComponentsPanel.addInfoElement(Localization.get(Localization.KILLS), String.valueOf(DataInputOutput.getInstance().getKills()));
        SettingsElementView deathsElement = boxComponentsPanel.addInfoElement(Localization.get(Localization.DEATHS), String.valueOf(DataInputOutput.getInstance().getDeaths()));
        SettingsElementView roundsElement = boxComponentsPanel.addInfoElement(Localization.get(Localization.ROUNDS), String.valueOf(DataInputOutput.getInstance().getRounds()));
        SettingsElementView lostGamesElement = boxComponentsPanel.addInfoElement(Localization.get(Localization.LOST_GAMES), String.valueOf(DataInputOutput.getInstance().getLostGames()));
        SettingsElementView pointsElement = boxComponentsPanel.addInfoElement(Localization.get(Localization.POINTS), String.valueOf(DataInputOutput.getInstance().getScore()));
        SettingsElementView livesElement = boxComponentsPanel.addInfoElement(Localization.get(Localization.LIVES), String.valueOf(DataInputOutput.getInstance().getLives()));
    }
}
