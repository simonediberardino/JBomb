package game.ui;

import game.BomberMan;

import javax.swing.*;
import java.awt.*;

/**
 * The main game frame that holds the game panel and UI.
 */
public class GameFrame extends JFrame {
    private GamePanel gamePanel; // The main panel that contains the game
    private JPanel parentPanel; // JPanel that fits the JFrame;
    private JPanel gamePanelWithBorders; // JPanel that includes the Game Panel and its borders;
    private JPanel leftPanel; // Borders of the Game Panel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel rightPanel;

    /**
     * Initializes the game frame.
     */
    public GameFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
        BomberMan.getInstance().gameFrame = this; // Set the game frame instance
        initGamePanel(); // Initialize the game panel and components
        finalizeFrame();
    }

    /**
     * Initializes the game panel which includes the game board with borders and background image.
     * It also sets the layout and adds components to the parent panel.
     */
    private void initGamePanel() {
        createParentPanel(); // Create the parent panel for the game frame
        createGamePanelWithBorders(); // Create the game panel that includes its borders
        createGamePanel(); // Create the main game panel that contains the game
        createBorderPanels(); // Create the border panels for the game panel
        addPanelsToGamePanelWithBorders(); // Add the border panels and game panel to the game panel with borders
        addGamePanelWithBordersToParentPanel(); // Add the game panel with borders to the parent panel
        setLayoutAndAddParentPanel(); // Set the layout and add the parent panel to the game frame
        resizeWindowToFitComponents(); // Resize the game frame to fit the components
        startGame(); // Start the game
    }

    /**
     * Create the parent panel for the game frame.
     * It sets the background image of the panel and its layout.
     */
    private void createParentPanel() {
        parentPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                Image backgroundImage = Utility.loadImage(Paths.getBackgroundImage());

                int width = (int) getPreferredSize().getWidth();
                int height = (int) getPreferredSize().getHeight();

                // Scale the background image to fit the size of the panel and draw it
                if (width != 0 && height != 0) {
                    g.drawImage(backgroundImage.getScaledInstance(width, height, 1), 0, 0, null);
                }

                super.paint(g);
            }
        };

        parentPanel.setLayout(new BorderLayout()); // Set the layout of the parent panel
        parentPanel.setOpaque(false); // Set the panel to be transparent
    }

    /**
     * Create the game panel that includes its borders.
     */
    private void createGamePanelWithBorders() {
        gamePanelWithBorders = new JPanel(); // Create a new JPanel for the game panel with borders
        gamePanelWithBorders.setLayout(new BorderLayout()); // Set the layout of the panel to BorderLayout
        gamePanelWithBorders.setOpaque(false); // Set the panel to be transparent
    }

    /**
     * Create the main game panel that contains the game.
     */
    private void createGamePanel() {
        gamePanel = new GamePanel(); // Create a new GamePanel instance
    }

    /**
     * Create the border panels for the game panel.
     * It creates four panels (left, top, bottom, and right), each with the border image of the game.
     */
    /**

     Creates and sets the border panels to the gamePanelWithBorders.
     Calculates the dimensions and border sizes for the panels.
     Uses createLeftPanel, createTopPanel, createBottomPanel, and createRightPanel methods to create the panels.
     Sets the panels as opaque.
     */
    private void createBorderPanels() {
        // calculate width of left and right border panels
        int widthSides = (int) ((getWidth() - (gamePanel.getMaximumSize().getWidth())) / 2);

        // calculate height of top and bottom border panels
        int heightNorthSouth = (int) ((getHeight() - (gamePanel.getMaximumSize().getHeight())) / 2);

        // set the size of the borders
        int borderSize = Utility.px(90);

        // get the images of the border panels from the current level of the game
        Image[] borderImages = BomberMan.getInstance().getCurrentLevel().getPitch();

        // create left panel and set the dimensions and the image
        leftPanel = createLeftPanel(
                widthSides,
                (int) gamePanel.getMaximumSize().getHeight(),
                borderImages[0],
                borderSize
        );

        // create top panel and set the dimensions, the image, and the width of the left panel
        topPanel = createTopPanel(
                (int) gamePanel.getMaximumSize().getWidth(),
                heightNorthSouth,
                borderImages[3],
                borderSize,
                (int) leftPanel.getPreferredSize().getWidth()
        );

        // create bottom panel and set the dimensions, the image, and the width of the left panel
        bottomPanel = createBottomPanel(
                (int) gamePanel.getMaximumSize().getWidth(),
                heightNorthSouth,
                borderImages[1],
                borderSize,
                (int) leftPanel.getPreferredSize().getWidth()
        );

        // create right panel and set the dimensions and the image
        rightPanel = createRightPanel(
                widthSides,
                (int) gamePanel.getMaximumSize().getHeight(),
                borderImages[2],
                borderSize
        );

        // set the border panels as opaque
        leftPanel.setOpaque(false);
        topPanel.setOpaque(false);
        bottomPanel.setOpaque(false);
        rightPanel.setOpaque(false);
    }

    /**

     Adds the border panels and the game panel to gamePanelWithBorders using BorderLayout.
     */
    private void addPanelsToGamePanelWithBorders() {
        gamePanelWithBorders.add(leftPanel, BorderLayout.WEST);
        gamePanelWithBorders.add(topPanel, BorderLayout.NORTH);
        gamePanelWithBorders.add(bottomPanel, BorderLayout.SOUTH);
        gamePanelWithBorders.add(rightPanel, BorderLayout.EAST);
        gamePanelWithBorders.add(gamePanel, BorderLayout.CENTER);
    }
    /**

     Adds gamePanelWithBorders to the parent panel using BorderLayout.
     */
    private void addGamePanelWithBordersToParentPanel() {
        parentPanel.add(gamePanelWithBorders, BorderLayout.CENTER);
    }
    /**

     Sets the layout of the main panel to BorderLayout and adds parentPanel to it.
     */
    private void setLayoutAndAddParentPanel() {
        setLayout(new BorderLayout());
        add(parentPanel, BorderLayout.CENTER);
    }

    /**
     Resizes the window to fit the components.
     */
    private void resizeWindowToFitComponents() {
        pack();
    }

    /**
     Starts the game by calling the start() method of the current level with gamePanel as the argument.
     */
    private void startGame() {
        BomberMan.getInstance().getCurrentLevel().start(gamePanel);
    }

    /**
     * Creates a JPanel for the left side of the game window with an image scaled to the specified border size.
     *
     * @param width      the width of the panel
     * @param height     the height of the panel
     * @param image      the image to be displayed
     * @param borderSize the size of the border for the image
     * @return the JPanel for the left side of the game window
     */
    private JPanel createLeftPanel(int width, int height, Image image, int borderSize) {
        // Create a new JPanel for the left side of the game window
        JPanel leftPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                // Draw the image scaled to the specified border size on the left side of the panel
                g.drawImage(image.getScaledInstance(borderSize, getHeight(), 1), (int) (getPreferredSize().getWidth() - borderSize), 0, null);
            }
        };
        // Set the preferred size of the panel to the specified width and height
        leftPanel.setPreferredSize(new Dimension(width, height));
        // Return the JPanel for the left side of the game window
        return leftPanel;
    }

    /**
     * Creates a JPanel for the top of the game window with an image scaled to the specified border size.
     *
     * @param width          the width of the panel
     * @param height         the height of the panel
     * @param image          the image to be displayed
     * @param borderSize     the size of the border for the image
     * @param leftPanelWidth the width of the left panel
     * @return the JPanel for the top of the game window
     */
    private JPanel createTopPanel(int width, int height, Image image, int borderSize, int leftPanelWidth) {
        // Create a new JPanel for the top of the game window
        JPanel topPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                // Draw the image scaled to the specified border size on the top of the panel
                g.drawImage(image.getScaledInstance(gamePanel.getWidth() + borderSize * 2, borderSize / 2, 1), leftPanelWidth - borderSize, getHeight() - borderSize / 2, null);
            }
        };
        // Set the preferred size of the panel to the specified width and height
        topPanel.setPreferredSize(new Dimension(width, height));
        // Return the JPanel for the top of the game window
        return topPanel;
    }

    /**
     * Creates a JPanel for the bottom of the game window with an image scaled to the specified border size.
     *
     * @param width          the width of the panel
     * @param height         the height of the panel
     * @param image          the image to be displayed
     * @param borderSize     the size of the border for the image
     * @param leftPanelWidth the width of the left panel
     * @return the JPanel for the bottom of the game window
     */
    private JPanel createBottomPanel(int width, int height, Image image, int borderSize, int leftPanelWidth) {
        JPanel bottomPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(image.getScaledInstance(gamePanel.getWidth() + borderSize * 2, borderSize / 2, 1), leftPanelWidth - borderSize, 0, null);
            }
        };
        bottomPanel.setPreferredSize(new Dimension(width, height));
        return bottomPanel;
    }

    private JPanel createRightPanel(int width, int height, Image image, int borderSize) {
        // Create a new JPanel for the right side of the game window
        JPanel rightPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                // Draw the image scaled to the specified border size on the right side of the panel
                g.drawImage(image.getScaledInstance(borderSize, getHeight(), 1), 0, 0, null);
            }
        };
        // Set the preferred size of the panel to the specified width and height
        rightPanel.setPreferredSize(new Dimension(width, height));
        // Return the JPanel for the left side of the game window
        return rightPanel;
    }

    public void finalizeFrame(){
        // Set key listener and focusable
        this.addKeyListener(BomberMan.getInstance().getControllerManager());
        this.setFocusable(true);
        this.requestFocus();

        // Make the frame visible
        this.setVisible(true);
        this.revalidate();
        this.repaint();

        revalidate();
        repaint();
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}