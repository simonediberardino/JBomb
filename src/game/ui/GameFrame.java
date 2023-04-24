package game.ui;

import game.BomberMan;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public static final int GRID_SIZE = Utility.px(70);
    private JPanel mainJPanel;

    public GameFrame(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);

        initGamePanel();
        createUI();
    }

    private void initGamePanel(){
        mainJPanel = new GamePanel();
        setLayout(new BorderLayout());

        int widthSides = (int) ((getWidth() - (mainJPanel.getMaximumSize().getWidth())) / 2);
        int heightNorthSouth = (int) ((getHeight() - (mainJPanel.getMaximumSize().getHeight())) / 2);

        add((Box.createRigidArea(new Dimension(widthSides, (int) mainJPanel.getMaximumSize().getHeight()))), BorderLayout.EAST);
        add((Box.createRigidArea(new Dimension(widthSides, (int) mainJPanel.getMaximumSize().getHeight()))), BorderLayout.WEST);
        add((Box.createRigidArea(new Dimension((int) mainJPanel.getMaximumSize().getWidth(), heightNorthSouth))), BorderLayout.NORTH);
        add((Box.createRigidArea(new Dimension((int) mainJPanel.getMaximumSize().getWidth(), heightNorthSouth))), BorderLayout.SOUTH);

        mainJPanel.setBackground(Color.GREEN);
        add(mainJPanel, BorderLayout.CENTER);
        pack();
    }

    public void createUI(){
        this.addKeyListener(BomberMan.getInstance().getKeyEventObservable());
        this.setFocusable(true);
        this.requestFocus();

        this.setVisible(true);
        this.revalidate();
        this.repaint();

        BomberMan.getInstance().getCurrentLevel().start(mainJPanel);
        revalidate();
        repaint();
    }
}
