package game.ui.pages;

import game.Bomberman;
import game.data.DataInputOutput;
import game.events.RunnablePar;
import game.level.WorldSelectorLevel;
import game.ui.frames.BombermanFrame;
import game.ui.panels.menu.AvatarMenuPanel;
import game.ui.panels.menu.ProfilePanel;
import game.ui.panels.menu.SettingsPanel;
import game.ui.panels.menu.UsernameProfilePanel;
import game.ui.viewelements.bombermanbutton.RedButton;
import game.ui.viewelements.bombermanbutton.YellowButton;
import game.utils.Paths;
import game.utils.XMLUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static game.localization.Localization.*;

/**
 * The MenuPanel class represents the main menu screen of the game.
 */
public class MainMenuPanel extends BaseMenu {
    /**
     * Constructs a MenuPanel with the specified CardLayout, parent JPanel, and BombermanFrame.
     *
     * @param cardLayout the CardLayout to use
     * @param parent     the parent JPanel
     * @param frame      the BombermanFrame
     */
    public MainMenuPanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame) {
        super(cardLayout, parent, frame);
    }

    @Override
    protected int getButtonsPadding() {
        return 3;
    }

    @Override
    protected List<JButton> getButtons() {
        return Arrays.asList(createStartLevelButton(), createStartArenaButton(), createProfileButton(), createSettingsButton(), createQuitButton());
    }

    @Override
    protected JPanel getRightPanel() {
        String[] avatarPaths = XMLUtils.parseXmlArray(Paths.getSkinsXml(), "skins");
        assert avatarPaths != null;

        RunnablePar getSkinRunnable = new RunnablePar() {
            @Override
            public <T> Object execute(T par) {
                return Paths.getEntitiesFolder() + "/player/" + DataInputOutput.getInstance().getSkin();
            }
        };

        // Define a Consumer to handle the skin change
        Consumer<Integer> skinChangeConsumer = indexAdder -> {
            String currAvatar = (String) getSkinRunnable.execute(null);

            int newIndex = 0;
            for (int i = 0; i < avatarPaths.length; i++) {
                String avatarPath = avatarPaths[i];
                if (currAvatar.endsWith("/" + avatarPath)) {
                    newIndex = i + indexAdder;
                    break;
                }
            }

            if (newIndex >= avatarPaths.length) {
                newIndex = 0;
            } else if (newIndex < 0) {
                newIndex = avatarPaths.length - 1;
            }

            DataInputOutput.getInstance().setSkin(avatarPaths[newIndex]);
        };

        return new AvatarMenuPanel(getSkinRunnable, skinChangeConsumer);
    }

    @Override
    protected JPanel getLeftPanel() {
        RunnablePar getUsernameRunnable = new RunnablePar() {
            @Override
            public <T> Object execute(T par) {
                return DataInputOutput.getInstance().getUsername();
            }
        };

        // Define a Consumer to handle the username change
        Consumer<String> usernameChangeConsumer = username -> {
            if (username.isBlank()) return;
            DataInputOutput.getInstance().setUsername(username.trim());
        };

        return new UsernameProfilePanel(getUsernameRunnable, usernameChangeConsumer);
    }

    /**
     * Creates the startLevelButton and adds it to the listButtonsPanel.
     */
    private JButton createStartLevelButton() {
        JButton startLevelButton = new YellowButton(get(PLAY));
        startLevelButton.addActionListener((v) -> Bomberman.startLevel(new WorldSelectorLevel()));
        return startLevelButton;
    }

    private JButton createStartArenaButton() {
        JButton startLevelButton = new YellowButton(get(START_ARENA));
        startLevelButton.addActionListener((v) -> Bomberman.showActivity(ArenaMenuPanel.class));
        return startLevelButton;
    }

    /**
     * Creates the profileButton and adds it to the listButtonsPanel.
     */
    private JButton createProfileButton() {
        JButton profileButton = new YellowButton(get(PROFILE));
        profileButton.addActionListener(l -> Bomberman.showActivity(ProfilePanel.class));
        return profileButton;
    }

    private JButton createSettingsButton() {
        JButton settingsButton = new YellowButton(get(SETTINGS));
        settingsButton.addActionListener(l -> Bomberman.showActivity(SettingsPanel.class));
        return settingsButton;
    }

    /**
     * Creates the exitButton and adds it to the listButtonsPanel.
     */
    private JButton createQuitButton() {
        JButton exitButton = new RedButton(get(QUIT));
        exitButton.addActionListener(v -> System.exit(0));
        return exitButton;
    }

    @Override
    public void onShowCallback() {

    }
}