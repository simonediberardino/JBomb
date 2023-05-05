package game.panels;

import game.BomberMan;
import game.models.PagePanel;
import game.utils.Paths;
import game.utils.Utility;

import javax.swing.*;
import java.awt.*;

public class MatchPanel extends PagePanel {
    private PitchPanel pitchPanel; // The main panel that contains the game
    private JPanel leftPanel; // Borders of the Game Panel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel rightPanel;

    public MatchPanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame) {
        super(cardLayout, parent, frame);
    }

    /**
     * Initializes the game panel which includes the game board with borders and background image.
     * It also sets the layout and adds components to the parent panel.
     */
    public void initGamePanel() {
        createParentPanel(); // Create the parent panel for the game frame
        createGamePanel(); // Create the main game panel that contains the game
        createBorderPanels(); // Create the border panels for the game panel
        addPanelsToGamePanelWithBorders(); // Add the border panels and game panel to the game panel with borders
        addGamePanelWithBordersToParentPanel(); // Add the game panel with borders to the parent panel
        setLayoutAndAddParentPanel(); // Set the layout and add the parent panel to the game frame
        resizeWindowToFitComponents(); // Resize the game frame to fit the components
        startGame(); // Start the game
    }

    @Override
    public void paint(Graphics g) {
        Image backgroundImage = Utility.loadImage(Paths.getBackgroundImage());

        int width = (int) frame.getPreferredSize().getWidth();
        int height = (int) frame.getPreferredSize().getHeight();

        // Scale the background image to fit the size of the panel and draw it
        if (width != 0 && height != 0) {
            g.drawImage(backgroundImage.getScaledInstance(width, height, 1), 0, 0, null);
        }

        super.paint(g);
    }

    private void createParentPanel(){
        setLayout(new BorderLayout()); // Set the layout of the parent panel
        setOpaque(false); // Set the panel to be transparent
    }

    /**
     * Create the main game panel that contains the game.
     */
    private void createGamePanel() {
        pitchPanel = new PitchPanel(); // Create a new GamePanel instance
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
        int widthSides = (int) ((frame.getWidth() - (pitchPanel.getMaximumSize().getWidth())) / 2);

        // calculate height of top and bottom border panels
        int heightNorthSouth = (int) ((frame.getHeight() - (pitchPanel.getMaximumSize().getHeight())) / 2);

        // set the size of the borders
        int borderSize = Utility.px(90);

        // get the images of the border panels from the current level of the game
        Image[] borderImages = BomberMan.getInstance().getCurrentLevel().getPitch();

        // create left panel and set the dimensions and the image
        leftPanel = createLeftPanel(
                widthSides,
                (int) pitchPanel.getMaximumSize().getHeight(),
                borderImages[0],
                borderSize
        );

        // create top panel and set the dimensions, the image, and the width of the left panel
        topPanel = createTopPanel(
                (int) pitchPanel.getMaximumSize().getWidth(),
                heightNorthSouth,
                borderImages[3],
                borderSize,
                (int) leftPanel.getPreferredSize().getWidth()
        );

        // create bottom panel and set the dimensions, the image, and the width of the left panel
        bottomPanel = createBottomPanel(
                (int) pitchPanel.getMaximumSize().getWidth(),
                heightNorthSouth,
                borderImages[1],
                borderSize,
                (int) leftPanel.getPreferredSize().getWidth()
        );

        // create right panel and set the dimensions and the image
        rightPanel = createRightPanel(
                widthSides,
                (int) pitchPanel.getMaximumSize().getHeight(),
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
        add(leftPanel, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
        add(rightPanel, BorderLayout.EAST);
        add(pitchPanel, BorderLayout.CENTER);
    }
    /**

     Adds gamePanelWithBorders to the parent panel using BorderLayout.
     */
    private void addGamePanelWithBordersToParentPanel() {
    }
    /**

     Sets the layout of the main panel to BorderLayout and adds parentPanel to it.
     */
    private void setLayoutAndAddParentPanel() {
        add(pitchPanel, BorderLayout.CENTER);
    }

    /**
     Resizes the window to fit the components.
     */
    private void resizeWindowToFitComponents() {
        frame.pack();
    }

    /**
     Starts the game by calling the start() method of the current level with gamePanel as the argument.
     */
    private void startGame() {
        BomberMan.getInstance().getCurrentLevel().start(pitchPanel);
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
                System.out.println((int) frame.getPreferredSize().getHeight());
                g.drawImage(image.getScaledInstance(borderSize, (int) frame.getPreferredSize().getHeight(), 1), pitchPanel.getX() - borderSize, 0, null);
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
                g.drawImage(image.getScaledInstance(pitchPanel.getWidth() + borderSize * 2, borderSize / 2, 1), leftPanelWidth - borderSize, pitchPanel.getY() - borderSize/2, null);
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
                g.drawImage(image.getScaledInstance(pitchPanel.getWidth() + borderSize * 2, borderSize / 2, 1), leftPanelWidth - borderSize, 0, null);
            }
        };
        bottomPanel.setPreferredSize(new Dimension(width, height));
        return bottomPanel;
    }

    private JPanel createRightPanel(int width, int height, Image image, int borderSize) {
        System.out.println((int) frame.getPreferredSize().getHeight());
        // Create a new JPanel for the right side of the game window
        JPanel rightPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                // Draw the image scaled to the specified border size on the right side of the panel
                g.drawImage(image.getScaledInstance(borderSize, (int) frame.getPreferredSize().getHeight(), 1), 0, 0, null);
            }
        };
        // Set the preferred size of the panel to the specified width and height
        rightPanel.setPreferredSize(new Dimension(width, height));
        // Return the JPanel for the left side of the game window
        return rightPanel;
    }

    public PitchPanel getPitchPanel() {
        return pitchPanel;
    }
}
