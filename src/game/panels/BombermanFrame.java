package game.panels;

import game.BomberMan;

import javax.swing.*;
import java.awt.*;


/**

 The BombermanFrame class creates the main frame of the game and handles the creation and switching

 between the menu and match panels.
 */
public class BombermanFrame extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel parent = new JPanel();
    private MatchPanel matchPanel;
    private MenuPanel menuPanel;

    /**

     Creates the main frame and sets its properties.
     */
    public void create() {
        setFrameProperties();
        initMenuPanel();
        finalizeFrame();
        pack();
    }

    private void setFrameProperties() {
        setLayout(new BorderLayout());
        parent.setLayout(cardLayout);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
        add(parent);

    }
    /**

     Initializes the menu panel and adds it to the parent panel.
     */
    private void initMenuPanel() {
        menuPanel = new MenuPanel(cardLayout, parent, this);
        parent.add(menuPanel, MenuPanel.class.getSimpleName());
    }
    /**

     Initializes the match panel and adds it to the parent panel.
     */
    void initGamePanel() {
        matchPanel = new MatchPanel(cardLayout, parent, this);
        matchPanel.initGamePanel();
        parent.add(matchPanel, MatchPanel.class.getSimpleName());
    }
    /**

     Finalizes the frame by setting the key listener and focusable, making the frame visible, and
     showing the menu panel.
     */
    public void finalizeFrame() {
        // Set key listener and focusable
        this.addKeyListener(BomberMan.getInstance().getControllerManager());
        this.setFocusable(true);
        this.requestFocus();

        // Make the frame visible
        this.setVisible(true);
        this.revalidate();
        this.repaint();

        show(MenuPanel.class);
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

    public void show(Class<? extends PagePanel> page){
        // Show the menu panel
        cardLayout.show(parent, page.getSimpleName());
    }
}