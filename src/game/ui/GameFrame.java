package game.ui;

import game.BomberMan;

import javax.swing.*;
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

        initGamePanel();
        createUI();
    }

    /**
     * Initializes the main game panel.
     */
    private void initGamePanel(){
        gamePanel = new GamePanel();
        setLayout(new BorderLayout());

        // Calculate the width and height of the border rigid areas
        int widthSides = (int) ((getWidth() - (gamePanel.getMaximumSize().getWidth())) / 2);
        int heightNorthSouth = (int) ((getHeight() - (gamePanel.getMaximumSize().getHeight())) / 2);

        // Add border rigid areas
        add((Box.createRigidArea(new Dimension(widthSides, (int) gamePanel.getMaximumSize().getHeight()))), BorderLayout.EAST);
        add((Box.createRigidArea(new Dimension(widthSides, (int) gamePanel.getMaximumSize().getHeight()))), BorderLayout.WEST);
        add((Box.createRigidArea(new Dimension((int) gamePanel.getMaximumSize().getWidth(), heightNorthSouth))), BorderLayout.NORTH);
        add((Box.createRigidArea(new Dimension((int) gamePanel.getMaximumSize().getWidth(), heightNorthSouth))), BorderLayout.SOUTH);

        // Set background color and add main game panel
        gamePanel.setBackground(Color.GREEN);

        add(gamePanel, BorderLayout.CENTER);
        pack();
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

        // Start the current level
        BomberMan.getInstance().getCurrentLevel().start(gamePanel);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}