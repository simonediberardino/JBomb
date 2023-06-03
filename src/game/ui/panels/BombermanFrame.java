package game.ui.panels;

import game.level.world1.World1Level1;
import game.ui.panels.game.MatchPanel;
import game.ui.panels.game.PitchPanel;
import game.ui.panels.menus.*;
import game.ui.panels.settings.ProfilePanel;
import game.ui.panels.settings.SettingsPanel;
import game.utils.Paths;
import game.utils.Utility;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**

 The BombermanFrame class creates the main frame of the game and handles the creation and switching

 between the menu and match panels.
 */
public class BombermanFrame extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel parentPanel = new JPanel();
    private MatchPanel matchPanel;
    private MainMenuPanel mainMenuPanel;
    private LoadingPanel loadingPanel;
    private GameOverPanel gameOverPanel;
    private PausePanel pausePanel;
    private ProfilePanel profilePanel;
    private SettingsPanel settingsPanel;

    /**

     Creates the main frame and sets its properties.
     */
    public void create() {
        setIconImage(Utility.loadImage("src/game/ui/frame_icon.png"));
        setTitle("JaBomberman");
        setFrameProperties();
        initMenuPanel();
        initLoadingPanel();
        initGameOverPanel();
        initPausePanel();
        initProfilePanel();
        initSettingsPanel();
        finalizeFrame();
        pack();
        setFrameCursor();
    }

    private void setFrameProperties() {
        setLayout(new BorderLayout());
        parentPanel.setLayout(cardLayout);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
        add(parentPanel);

    }
    /**

     Initializes the menu panel and adds it to the parent panel.
     */
    private void initMenuPanel() {
        mainMenuPanel = new MainMenuPanel(cardLayout, parentPanel, this);
        parentPanel.add(mainMenuPanel, MainMenuPanel.class.getSimpleName());
    }

    private void initPausePanel() {
        pausePanel = new PausePanel(cardLayout, parentPanel, this);
        parentPanel.add(pausePanel, PausePanel.class.getSimpleName());
    }
    /**

     Initializes the menu panel and adds it to the parent panel.
     */
    private void initLoadingPanel() {
        loadingPanel = new LoadingPanel(cardLayout, parentPanel, this, new World1Level1());
        parentPanel.add(loadingPanel, LoadingPanel.class.getSimpleName());
    }

    /**
     Initializes the match panel and adds it to the parent panel.
     */
    public void initGamePanel() {
        matchPanel = new MatchPanel(cardLayout, parentPanel, this);
        matchPanel.initGamePanel();
        parentPanel.add(matchPanel, MatchPanel.class.getSimpleName());
    }

    /**

     Initializes the menu panel and adds it to the parent panel.
     */
    private void initGameOverPanel() {
        gameOverPanel = new GameOverPanel(cardLayout, parentPanel, this);
        parentPanel.add(gameOverPanel, GameOverPanel.class.getSimpleName());
    }

    private void initProfilePanel() {
        profilePanel = new ProfilePanel(cardLayout, parentPanel, this);
        parentPanel.add(profilePanel, ProfilePanel.class.getSimpleName());
    }

    /**

     Initializes the menu panel and adds it to the parent panel.
     */
    private void initSettingsPanel() {
        settingsPanel = new SettingsPanel(cardLayout, parentPanel, this);
        parentPanel.add(settingsPanel, SettingsPanel.class.getSimpleName());
    }

    /**

     Finalizes the frame by setting the key listener and focusable, making the frame visible, and
     showing the menu panel.
     */
    private void finalizeFrame() {
        // Set key listener and focusable
        this.setFocusable(true);
        this.requestFocus();

        // Make the frame visible
        this.setVisible(true);
        this.revalidate();
        this.repaint();
    }

    private void setFrameCursor() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(Paths.getCursorPath());
        Cursor c = toolkit.createCustomCursor(image, new Point(getX(), getY()), "img");
        setCursor(c);
    }

    /**
     Gets the pitch panel from the match panel.
     @return the pitch panel
     */
    public PitchPanel getPitchPanel() {
        return matchPanel.getPitchPanel();
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getParentPanel() {
        return parentPanel;
    }

    public MatchPanel getMatchPanel() {
        return matchPanel;
    }

    public MainMenuPanel getMainMenuPanel() {
        return mainMenuPanel;
    }

    public LoadingPanel getLoadingPanel() {
        return loadingPanel;
    }
}