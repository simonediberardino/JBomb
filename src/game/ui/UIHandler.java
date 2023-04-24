package game.ui;

import game.BomberMan;
import game.entity.*;
import game.models.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class UIHandler implements KeyListener {
    public static final int GRID_SIZE = 5;
    private static final int[] GAME_PANEL_SIZE = {GRID_SIZE*9, GRID_SIZE*9};
    public static JButton[][] positions = new JButton[GAME_PANEL_SIZE[0]][GAME_PANEL_SIZE[1]];
    private JFrame jFrame;
    private JPanel gridJPanel;
    private JPanel mainJPanel;

    public UIHandler(){
        createUI();
    }

    public void createUI(){
        jFrame = new JFrame();

        jFrame.addKeyListener(BomberMan.getInstance().getKeyEventObservable());
        jFrame.setFocusable(true);
        jFrame.requestFocus();

        jFrame.setSize(1920, 1080);
        mainJPanel = new JPanel();
        gridJPanel = new JPanel();
        gridJPanel.setLayout(new GridLayout(GAME_PANEL_SIZE[0],GAME_PANEL_SIZE[0]));

        Dimension dim = new Dimension(670, 800);
        gridJPanel.setMaximumSize(dim);
        gridJPanel.setMinimumSize(dim);
        gridJPanel.setMinimumSize(dim);
        gridJPanel.setPreferredSize(dim);
        gridJPanel.setMaximumSize(dim);
        gridJPanel.setSize(dim);
        gridJPanel.revalidate();

        for(int i = 0; i < GAME_PANEL_SIZE[0]; i++){
            for(int j = 0; j < GAME_PANEL_SIZE[0]; j++){
                positions[i][j] = new JButton();
                positions[i][j].setBackground(BomberMan.getInstance().getCurrentLevel().getBackgroundColor());
                positions[i][j].setBorderPainted(false);
                gridJPanel.add(positions[i][j]);
            }
        }

        mainJPanel.add(gridJPanel);
        jFrame.add(mainJPanel);
        jFrame.setVisible(true);


        BomberMan.getInstance().getCurrentLevel().generatePitch(positions);
                /*
        BomberMan.getInstance().setPlayer(new Player(new Coordinates(20,10)));
        BomberMan.getInstance().getPlayer().spawn();

        (new Enemy(new Coordinates(10,13))).spawn();
        BomberMan.getInstance().getPlayer().placeBomb();*/

        jFrame.repaint();
    }


    public static void setIcon(JButton jComponent, Icon icon){
        jComponent.setIcon(new ImageIcon(getBufferedImageFromIcon(icon).getScaledInstance(jComponent.getWidth(), jComponent.getHeight(),1)));
    }

    public static void setIcon(JButton jComponent, Icon icon, int sizeX, int sizeY){
        jComponent.setIcon(new ImageIcon(getBufferedImageFromIcon(icon).getScaledInstance(sizeY, sizeX,1)));
    }

    public static BufferedImage getBufferedImageFromIcon(Icon icon) {
        BufferedImage buffer = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = buffer.getGraphics();
        icon.paintIcon(new JLabel(), g, 0, 0);
        g.dispose();
        return buffer;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("keyTyped: " + e.getKeyCode());

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("keyPressed: " + e.getKeyCode());

    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("keyReleased: " + e.getKeyCode());

    }
}
