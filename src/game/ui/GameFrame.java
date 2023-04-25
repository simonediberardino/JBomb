package game.ui;

import game.BomberMan;

import javax.swing.*;
import java.awt.*;

/**
 * The main game frame that holds the game panel and UI.
 */
public class GameFrame extends JFrame {
    public static final int GRID_SIZE = Utility.px(60);
    private JPanel mainJPanel;

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
        mainJPanel = new GamePanel();
        setLayout(new BorderLayout());

        // Calculate the width and height of the border rigid areas
        int widthSides = (int) ((getWidth() - (mainJPanel.getMaximumSize().getWidth())) / 2);
        int heightNorthSouth = (int) ((getHeight() - (mainJPanel.getMaximumSize().getHeight())) / 2);

        // Add border rigid areas
        add((Box.createRigidArea(new Dimension(widthSides, (int) mainJPanel.getMaximumSize().getHeight()))), BorderLayout.EAST);
        add((Box.createRigidArea(new Dimension(widthSides, (int) mainJPanel.getMaximumSize().getHeight()))), BorderLayout.WEST);
        add((Box.createRigidArea(new Dimension((int) mainJPanel.getMaximumSize().getWidth(), heightNorthSouth))), BorderLayout.NORTH);
        add((Box.createRigidArea(new Dimension((int) mainJPanel.getMaximumSize().getWidth(), heightNorthSouth))), BorderLayout.SOUTH);

        // Set background color and add main game panel
        mainJPanel.setBackground(Color.GREEN);
        add(mainJPanel, BorderLayout.CENTER);
        pack();
    }

    /**
     * Initializes the game UI and starts the current level.
     */
    public void createUI(){
        // Set key listener and focusable
        this.addKeyListener(BomberMan.getInstance().getKeyEventObservable());
        this.setFocusable(true);
        this.requestFocus();

        // Make the frame visible
        this.setVisible(true);
        this.revalidate();
        this.repaint();

        // Start the current level
        BomberMan.getInstance().getCurrentLevel().start(mainJPanel);
        revalidate();
        repaint();
    }
}