package game.presentation.ui.pages.pause;

import game.JBomb;
import game.presentation.ui.frames.JBombFrame;
import game.presentation.ui.pages.BaseMenu;
import game.presentation.ui.viewelements.bombermanbutton.YellowButton;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static game.localization.Localization.*;

public class PausePanel extends BaseMenu {
    public PausePanel(CardLayout cardLayout, JPanel parent, JBombFrame frame) {
        super(cardLayout, parent, frame);
    }

    @Override
    protected int getButtonsPadding() {
        return 1;
    }

    @Override
    protected List<JButton> getButtons() {
        return Arrays.asList(createResumeButton(), createQuitButton());
    }

    @Override
    protected JPanel getRightPanel() {
        return null;
    }

    @Override
    protected JPanel getLeftPanel() {
        return null;
    }

    private JButton createResumeButton() {
        JButton profileButton = new YellowButton(get(RESUME_GAME));
        profileButton.addActionListener(l -> JBomb.match.toggleGameState());
        return profileButton;
    }

    private JButton createQuitButton() {
        JButton exitButton = new YellowButton(get(QUIT_GAME));
        exitButton.addActionListener(v -> JBomb.quitMatch());
        return exitButton;
    }

    @Override
    public void onShowCallback() {

    }
}
