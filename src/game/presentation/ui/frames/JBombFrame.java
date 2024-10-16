package game.presentation.ui.frames;

import com.google.common.io.ByteStreams;
import game.domain.level.levels.world1.World1Level1;
import game.localization.Localization;
import game.presentation.ui.pages.arena.ArenaMenuPanel;
import game.presentation.ui.pages.error.NetworkErrorPage;
import game.presentation.ui.pages.game_over.GameOverPanel;
import game.presentation.ui.pages.init.InitPanel;
import game.presentation.ui.pages.loading.LoadingPanel;
import game.presentation.ui.pages.main_menu.MainMenuPanel;
import game.presentation.ui.pages.multiplayer.MultiplayerPanel;
import game.presentation.ui.pages.pause.PausePanel;
import game.presentation.ui.pages.play.PlayMenuPanel;
import game.presentation.ui.pages.registration.RegistrationAvatar;
import game.presentation.ui.pages.registration.RegistrationUsername;
import game.presentation.ui.pages.server_browser.ServersListMenuPanel;
import game.presentation.ui.panels.menu.ProfilePanel;
import game.presentation.ui.pages.settings.SettingsPanel;
import game.presentation.ui.panels.game.MatchPanel;
import game.presentation.ui.panels.game.PitchPanel;
import game.utils.file_system.Paths;
import game.utils.Utility;
import game.utils.dev.XMLUtils;
import game.utils.ui.ToastUtils;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import game.presentation.ui.viewelements.misc.ToastHandler;

/**
 * The BombermanFrame class creates the main frame of the game and handles the creation and switching
 * <p>
 * between the menu and match panels.
 */
public class JBombFrame extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel parentPanel = new JPanel() {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            ToastHandler toastHandler = ToastHandler.getInstance();
            if (toastHandler.getText() == null) {
                return;
            }
            toastHandler.showToast((Graphics2D) g);
            repaint();
        }
    };

    private MatchPanel matchPanel;
    private MainMenuPanel mainMenuPanel;
    private PlayMenuPanel playMenuPanel;
    private MultiplayerPanel multiplayerPanel;
    private ServersListMenuPanel serversListMenuPanel;
    private LoadingPanel loadingPanel;
    private GameOverPanel gameOverPanel;
    private PausePanel pausePanel;
    private ProfilePanel profilePanel;
    private SettingsPanel settingsPanel;
    private ArenaMenuPanel arenaMenuPanel;
    private InitPanel initPanel;
    private NetworkErrorPage networkErrorPage;
    private RegistrationUsername registrationUsername;
    private RegistrationAvatar registrationAvatar;


    /**
     * Creates the main frame and sets its properties.
     */
    public void create() {
        setIconImage(Utility.INSTANCE.loadImage(Paths.getIconPath()));
        setTitle(Localization.get(Localization.APP_NAME));
        setFrameProperties();
        initInitPanel();
        initMenuPanel();
        initPlayMenuPanel();
        initMultiplayerPanel();
        initServersListMenuPanel();
        initLoadingPanel();
        initGameOverPanel();
        initPausePanel();
        initProfilePanel();
        initSettingsPanel();
        initArenaMenuPanel();
        initErrorMenuPanel();
        initRegistrationPanel();
        finalizeFrame();
        pack();
        setFrameCursor();
    }

    private void setFrameProperties() {
        setLayout(new BorderLayout());
        parentPanel.setLayout(cardLayout);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Dimension fullScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if("true".equals(XMLUtils.readConfig("full_screen"))) {
            setPreferredSize(fullScreenSize);
            setUndecorated(true);
        }else {
            setPreferredSize(new Dimension((int) (fullScreenSize.getWidth() / 2), (int) (fullScreenSize.getHeight() / 2)));
        }

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        add(parentPanel);

    }

    /**
     * Initializes the menu panel and adds it to the parent panel.
     */
    private void initMenuPanel() {
        mainMenuPanel = new MainMenuPanel(cardLayout, parentPanel, this);
        parentPanel.add(mainMenuPanel, MainMenuPanel.class.getSimpleName());
    }

    private void initInitPanel() {
        initPanel = new InitPanel(cardLayout, parentPanel, this);
        parentPanel.add(initPanel, InitPanel.class.getSimpleName());
    }

    private void initPlayMenuPanel () {
        playMenuPanel = new PlayMenuPanel(cardLayout, parentPanel, this);
        parentPanel.add(playMenuPanel, PlayMenuPanel.class.getSimpleName());
    }

    private void initMultiplayerPanel() {
        multiplayerPanel = new MultiplayerPanel(cardLayout, parentPanel, this);
        parentPanel.add(multiplayerPanel, MultiplayerPanel.class.getSimpleName());
    }

    private void initServersListMenuPanel () {
        serversListMenuPanel = new ServersListMenuPanel(cardLayout, parentPanel, this);
        parentPanel.add(serversListMenuPanel, ServersListMenuPanel.class.getSimpleName());
    }

    private void initPausePanel() {
        pausePanel = new PausePanel(cardLayout, parentPanel, this);
        parentPanel.add(pausePanel, PausePanel.class.getSimpleName());
    }

    /**
     * Initializes the menu panel and adds it to the parent panel.
     */
    private void initLoadingPanel() {
        loadingPanel = new LoadingPanel(cardLayout, parentPanel, this, new World1Level1());
        parentPanel.add(loadingPanel, LoadingPanel.class.getSimpleName());
    }

    /**
     * Initializes the match panel and adds it to the parent panel.
     */
    public void initGamePanel() {
        matchPanel = new MatchPanel(cardLayout, parentPanel, this);
        matchPanel.initGamePanel();
        parentPanel.add(matchPanel, MatchPanel.class.getSimpleName());
    }

    /**
     * Initializes the menu panel and adds it to the parent panel.
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
     * Initializes the menu panel and adds it to the parent panel.
     */
    private void initSettingsPanel() {
        settingsPanel = new SettingsPanel(cardLayout, parentPanel, this);
        parentPanel.add(settingsPanel, SettingsPanel.class.getSimpleName());
    }

    /**
     * Initializes the arena panel and adds it to the parent panel.
     */
    private void initArenaMenuPanel() {
        arenaMenuPanel = new ArenaMenuPanel(cardLayout, parentPanel, this);
        parentPanel.add(arenaMenuPanel, ArenaMenuPanel.class.getSimpleName());
    }

    private void initErrorMenuPanel()  {
        networkErrorPage = new NetworkErrorPage(cardLayout, parentPanel, this);
        parentPanel.add(networkErrorPage, NetworkErrorPage.class.getSimpleName());
    }

    private void initRegistrationPanel() {
        registrationUsername = new RegistrationUsername(cardLayout, parentPanel, this);
        parentPanel.add(registrationUsername, RegistrationUsername.class.getSimpleName());

        registrationAvatar = new RegistrationAvatar(cardLayout, parentPanel, this);
        parentPanel.add(registrationAvatar, RegistrationAvatar.class.getSimpleName());
    }

    /**
     * Finalizes the frame by setting the key listener and focusable, making the frame visible, and
     * showing the menu panel.
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
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // Use ClassLoader to load the image resource from the JAR file
        try (InputStream inputStream = classLoader.getResourceAsStream(Paths.getCursorPath())) {
            if (inputStream == null) {
                return;
            }

            byte[] imageBytes = ByteStreams.toByteArray(inputStream);

            Image image = toolkit.createImage(imageBytes);
            Cursor c = toolkit.createCustomCursor(image, new Point(getX(), getY()), "img");
            setCursor(c);
        } catch (Exception ignored) {
            // Exception handling logic can be added here if needed
        }
    }


    /**
     * Gets the pitch panel from the match panel.
     *
     * @return the pitch panel
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

    public void cleanGame() {
        matchPanel.removeAll();
    }
}