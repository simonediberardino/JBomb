package game.ui;

import game.BomberMan;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * The main game frame that holds the game panel and UI.
 */
public class GameFrame extends JFrame {
    private GamePanel gamePanel;

    /**
     * Initializes the game frame.
     */
    public GameFrame(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
        BomberMan.getInstance().gameFrame = this;
        initGamePanel();
        createUI();
    }

    private void initGamePanel() {
        gamePanel = new GamePanel();
        setLayout(new BorderLayout());

        int widthSides = (int) ((getWidth() - (gamePanel.getMaximumSize().getWidth())) / 2);
        int heightNorthSouth = (int) ((getHeight() - (gamePanel.getMaximumSize().getHeight())) / 2);

        int borderSize = Utility.px(90);

        Image[] images = BomberMan.getInstance().getCurrentLevel().getPitch();

        JPanel leftPanel = createLeftPanel(
                widthSides,
                (int) gamePanel.getMaximumSize().getHeight(),
                images[0],
                borderSize
        );

        add(leftPanel, BorderLayout.WEST);

        JPanel topPanel = createTopPanel(
                (int) gamePanel.getMaximumSize().getWidth(),
                heightNorthSouth,
                images[3],
                borderSize,
                (int) leftPanel.getPreferredSize().getWidth()
        );

        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = createBottomPanel(
                (int) gamePanel.getMaximumSize().getWidth(),
                heightNorthSouth,
                images[1],
                borderSize,
                (int) leftPanel.getPreferredSize().getWidth()
        );
        add(bottomPanel, BorderLayout.SOUTH);

        JPanel rightPanel = createRightPanel(
                widthSides,
                (int) gamePanel.getMaximumSize().getHeight(),
                images[2],
                borderSize
        );

        add(rightPanel, BorderLayout.EAST);
        add(gamePanel, BorderLayout.CENTER);

        pack();
        BomberMan.getInstance().getCurrentLevel().start(gamePanel);
    }

    private JPanel createLeftPanel(int width, int height, Image image, int borderSize) {
        JPanel leftPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(image.getScaledInstance(borderSize, getHeight(), 1), (int) (getPreferredSize().getWidth() - borderSize), 0, null);
            }
        };
        leftPanel.setPreferredSize(new Dimension(width, height));
        return leftPanel;
    }

    private JPanel createTopPanel(int width, int height, Image image, int borderSize, int leftPanelWidth) {
        JPanel topPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(image.getScaledInstance(gamePanel.getWidth() + borderSize * 2, borderSize / 2, 1), leftPanelWidth - borderSize, getHeight() - borderSize / 2, null);
            }
        };
        topPanel.setPreferredSize(new Dimension(width, height));
        return topPanel;
    }

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
        JPanel rightPanel = createImagePanel(image, borderSize, getHeight(), 0, 0);
        rightPanel.setPreferredSize(new Dimension(width, height));
        return rightPanel;
    }

    /**
     * Create a panel with the given image and size, positioned at the given x and y coordinates.
     *
     * @param image The image to use for the panel
     * @param width The width of the panel
     * @param height The height of the panel
     * @param x The x coordinate of the panel
     * @param y The y coordinate of the panel
     * @return The created panel
     */
    private JPanel createImagePanel(Image image, int width, int height, int x, int y) {
        return new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(image.getScaledInstance(width, height, 1), x, y, null);
            }
        };
    }
    /**
     * Initializes the game UI and starts the current level.
     */
    public void createUI(){
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