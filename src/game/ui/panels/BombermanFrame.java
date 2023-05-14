package game.ui.panels;

import game.Bomberman;
import game.level.world1.World1Level1;
import game.level.world2.World2Level5;
import game.ui.panels.game.MatchPanel;
import game.ui.panels.game.PitchPanel;
import game.ui.panels.menus.LoadingPanel;
import game.ui.panels.menus.MainMenuPanel;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;


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

    /**

     Creates the main frame and sets its properties.
     */
    public void create() {
        setFrameProperties();
        initMenuPanel();
        initLoadingPanel();
        finalizeFrame();
        pack();
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

     Finalizes the frame by setting the key listener and focusable, making the frame visible, and
     showing the menu panel.
     */
    public void finalizeFrame() {
        // Set key listener and focusable
        this.setFocusable(true);
        this.requestFocus();

        // Make the frame visible
        this.setVisible(true);
        this.revalidate();
        this.repaint();

        revalidate();
        repaint();
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