package game.presentation.ui.pages;

import game.Bomberman;
import game.presentation.ui.frames.BombermanFrame;
import game.presentation.ui.panels.game.PagePanel;
import game.presentation.ui.viewelements.misc.Space;
import game.utils.file_system.Paths;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class BaseMenu extends PagePanel {
    protected JPanel listButtonsPanel;

    public BaseMenu(CardLayout cardLayout, JPanel parent, BombermanFrame frame, String imagePath) {
        super(cardLayout, parent, frame, imagePath);
        setupLayout();
    }

    public BaseMenu(CardLayout cardLayout, JPanel parent, BombermanFrame frame) {
        super(cardLayout, parent, frame, Paths.getMainMenuWallpaper());
        setupLayout();
    }

    /**
     * Sets up the layout of the MenuPanel.
     */
    private void setupLayout() {
        setLayout(new BorderLayout());

        createCenterPanel();
        createRightPanel();
        createLeftPanel();
    }

    private void createCenterPanel() {
        createListButtonsPanel();
        addButtons();
    }

    private void createSidePanel(JPanel panel, String side) {
        if (panel == null)
            panel = new JPanel();

        int centerPanelWidth = (int) listButtonsPanel.getPreferredSize().getWidth();
        int height = (int) (Bomberman.bombermanFrame.getPreferredSize().getHeight());
        int width = (int) (Bomberman.bombermanFrame.getPreferredSize().getWidth() - centerPanelWidth) / 2;

        panel.setPreferredSize(new Dimension(width, height));
        panel.setOpaque(false);

        add(panel, side);
    }

    private void createRightPanel() {
        JPanel p = getRightPanel();
        createSidePanel(p, BorderLayout.EAST);
    }

    private void createLeftPanel() {
        JPanel p = getLeftPanel();
        createSidePanel(p, BorderLayout.WEST);
    }

    private void addButtons() {
        List<JButton> buttons = getButtons();

        for (JButton b : buttons) listButtonsPanel.add(b);
    }

    /**
     * Creates and adds the listButtonsPanel to the MenuPanel.
     */
    private void createListButtonsPanel() {
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridBagLayout());

        listButtonsPanel = new JPanel();
        listButtonsPanel.setLayout(new GridLayout(0, 1));
        listButtonsPanel.setOpaque(false);

        for (int i = 0; i < getButtonsPadding(); i++) {
            listButtonsPanel.add(new Space());
        }

        gridPanel.add(listButtonsPanel);
        gridPanel.setOpaque(false);
        add(gridPanel, BorderLayout.CENTER);
    }

    protected abstract int getButtonsPadding();

    protected abstract List<JButton> getButtons();

    protected abstract JPanel getRightPanel();
    protected abstract JPanel getLeftPanel();

    @Override
    public void onShowCallback() {

    }
}
