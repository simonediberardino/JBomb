package game.ui.panels.settings;

import game.data.DataInputOutput;
import game.localization.Localization;
import game.models.RunnablePar;
import game.ui.panels.BombermanFrame;
import game.ui.viewelements.settings.SettingsElementView;

import javax.swing.*;
import java.awt.*;

import static game.localization.Localization.*;

public class ProfilePanel extends BoxMenuPanel {
    /**
     Constructs a ProfilePanel object.
     @param cardLayout the CardLayout of the parent container
     @param parent the parent container of this panel
     @param frame the BombermanFrame object */
    public ProfilePanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame) {
        super(cardLayout, parent, frame, Localization.get(MY_BOMBERMAN));
    }

    @Override
    protected void addCustomElements() {
        SettingsElementView userName = addTextFieldElementView(Localization.get(USERNAME), DataInputOutput.getUsername(), new RunnablePar() {
            @Override
            public <T> void execute(T par) {
                if(par.toString().isBlank()) return;
                DataInputOutput.setUsername(par.toString().trim());
            }
        });

        SettingsElementView killsElement = addInfoElement(Localization.get(Localization.KILLS), String.valueOf(DataInputOutput.getPlayerDataObject().getKills()));
        SettingsElementView deathsElement = addInfoElement(Localization.get(Localization.DEATHS), String.valueOf(DataInputOutput.getPlayerDataObject().getDeaths()));
        SettingsElementView roundsElement = addInfoElement(Localization.get(Localization.ROUNDS), String.valueOf(DataInputOutput.getPlayerDataObject().getRounds()));
        SettingsElementView lostGamesElement = addInfoElement(Localization.get(Localization.LOST_GAMES), String.valueOf(DataInputOutput.getPlayerDataObject().getLostGames()));
        SettingsElementView pointsElement = addInfoElement(Localization.get(Localization.POINTS), String.valueOf(DataInputOutput.getPlayerDataObject().getPoints()));
        SettingsElementView livesElement = addInfoElement(Localization.get(Localization.LIVES), String.valueOf(DataInputOutput.getPlayerDataObject().getLives()));
    }
}
