package game.ui.panels.settings;

import game.data.DataInputOutput;
import game.localization.Localization;
import game.models.RunnablePar;
import game.ui.panels.BombermanFrame;
import game.ui.viewelements.settings.SettingsElementView;

import javax.swing.*;
import java.awt.*;

import static game.localization.Localization.SETTINGS;

public class SettingsPanel extends BoxMenuPanel{
    /**
     * Constructs a ProfilePanel object.
     *
     * @param cardLayout the CardLayout of the parent container
     * @param parent     the parent container of this panel
     * @param frame      the BombermanFrame object
     */
    public SettingsPanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame) {
        super(cardLayout, parent, frame, Localization.get(SETTINGS));
    }

    // TODO NEEDED REFACTOR!!!!
    @Override
    protected void addCustomElements() {
        SettingsElementView forwardKey = addTextFieldElementView("FORWARD", DataInputOutput.getForwardKeyChar(), new RunnablePar() {
            @Override
            public <T> void execute(T par) {
                char c = par.toString().isBlank() ? ' ' : par.toString().charAt(0);

                DataInputOutput.getPlayerDataObject().setForwardKey(c);
            }
        }, 1);

        SettingsElementView leftKey = addTextFieldElementView("LEFT", DataInputOutput.getLeftKeyChar(), new RunnablePar() {
            @Override
            public <T> void execute(T par) {
                char c = par.toString().isBlank() ? ' ' : par.toString().charAt(0);

                DataInputOutput.getPlayerDataObject().setLeftKey(c);
            }
        }, 1);

        SettingsElementView backKey = addTextFieldElementView("BACK", DataInputOutput.getBackKeyChar(), new RunnablePar() {
            @Override
            public <T> void execute(T par) {
                char c = par.toString().isBlank() ? ' ' : par.toString().charAt(0);

                DataInputOutput.getPlayerDataObject().setBackKey(c);
            }
        }, 1);

        SettingsElementView rightKey = addTextFieldElementView("RIGHT", DataInputOutput.getRightKeyChar(), new RunnablePar() {
            @Override
            public <T> void execute(T par) {
                char c = par.toString().isBlank() ? ' ' : par.toString().charAt(0);

                DataInputOutput.getPlayerDataObject().setRightKey(c);
            }
        }, 1);

        SettingsElementView bombKey = addTextFieldElementView("BOMB", DataInputOutput.getBombKeyChar(), new RunnablePar() {
            @Override
            public <T> void execute(T par) {
                char c = par.toString().isBlank() ? ' ' : par.toString().charAt(0);
                DataInputOutput.getPlayerDataObject().setBombKey(c);
            }
        }, 1);

    }
}
