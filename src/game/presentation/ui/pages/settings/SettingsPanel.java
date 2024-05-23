package game.presentation.ui.pages.settings;

import game.data.data.DataInputOutput;
import game.domain.events.models.RunnablePar;
import game.localization.Localization;
import game.audio.AudioManager;
import game.presentation.ui.frames.JBombFrame;
import game.presentation.ui.panels.models.BoxMenuPanel;
import game.presentation.ui.viewelements.settings.SettingsElementView;
import game.presentation.ui.viewelements.settings.SlideElementView;
import game.utils.Utility;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

import static game.localization.Localization.*;

public class SettingsPanel extends BoxMenuPanel {
    public SettingsPanel(CardLayout cardLayout, JPanel parent, JBombFrame frame) {
        super(cardLayout, parent, frame, Localization.get(SETTINGS));
    }

    @Override
    protected int getBoxPanelWidth() {
        return Utility.INSTANCE.px(800);
    }

    private SettingsElementView createTextFieldElementView(String label, String keyChar, RunnablePar runnablePar) {
        return boxComponentsPanel.addTextFieldElementView(label, keyChar, runnablePar, new RunnablePar() {
            @Nullable
            @Override
            public <T> Object execute(T par) {
                return null;
            }
        }, 1);
    }

    private SlideElementView createSlideElementView(String title, int currValue, RunnablePar callback) {
        return boxComponentsPanel.addSlideElementView(title, currValue, callback);
    }

    private RunnablePar createKeyRunnable(final Consumer<Integer> keySetter) {
        return new RunnablePar() {
            @Override
            public <T> Object execute(T par) {
                char c = par.toString().isEmpty() ? ' ' : par.toString().charAt(0);
                keySetter.accept((int) c);
                return null;
            }
        };
    }

    @Override
    protected void addCustomElements() {
        SettingsElementView forwardKey = createTextFieldElementView(
                Localization.get(KEY_FORWARD),
                DataInputOutput.getInstance().getForwardKeyChar(),
                createKeyRunnable(integer -> DataInputOutput.getInstance().setForwardKey(integer))
        );

        SettingsElementView leftKey = createTextFieldElementView(
                Localization.get(KEY_LEFT),
                DataInputOutput.getInstance().getLeftKeyChar(),
                createKeyRunnable(integer -> DataInputOutput.getInstance().setLeftKey(integer))
        );

        SettingsElementView backKey = createTextFieldElementView(
                Localization.get(KEY_BACK),
                DataInputOutput.getInstance().getBackKeyChar(),
                createKeyRunnable(integer -> DataInputOutput.getInstance().setBackKey(integer))
        );

        SettingsElementView rightKey = createTextFieldElementView(
                Localization.get(KEY_RIGHT),
                DataInputOutput.getInstance().getRightKeyChar(),
                createKeyRunnable(integer -> DataInputOutput.getInstance().setRightKey(integer))
        );

        SettingsElementView bombKey = createTextFieldElementView(
                Localization.get(KEY_BOMB),
                DataInputOutput.getInstance().getBombKeyChar(),
                createKeyRunnable(integer -> DataInputOutput.getInstance().setBombKey(integer))
        );

        SettingsElementView interactKey = createTextFieldElementView(
                Localization.get(KEY_INTERACT),
                DataInputOutput.getInstance().getInteractKeyChar(),
                createKeyRunnable(integer -> DataInputOutput.getInstance().setInteractKey(integer))
        );

        SettingsElementView audio = createSlideElementView(Localization.get(AUDIO_VOLUME),
                DataInputOutput.getInstance().getVolume(), new RunnablePar() {
                    @Override
                    public <T> Object execute(T par) {
                        DataInputOutput.getInstance().setVolume((Integer) par);
                        AudioManager.getInstance().stopBackgroundSong();
                        AudioManager.getInstance().playBackgroundSong();
                        return null;
                    }
                });
    }

}
