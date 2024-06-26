package game.presentation.ui.pages;

import game.JBomb;
import game.presentation.ui.frames.JBombFrame;
import game.presentation.ui.panels.game.PagePanel;
import game.presentation.ui.viewelements.misc.Space;
import game.utils.dev.Log;
import game.utils.file_system.Paths;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseMenu extends PagePanel {
    protected JPanel listButtonsPanel;
    private JPanel leftPanel = null;
    private JPanel rightPanel = null;

    public BaseMenu(CardLayout cardLayout, JPanel parent, JBombFrame frame, String imagePath) {
        super(cardLayout, parent, frame, imagePath);
        setupLayout();
    }

    public BaseMenu(CardLayout cardLayout, JPanel parent, JBombFrame frame) {
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
        int height = (int) (JBomb.JBombFrame.getPreferredSize().getHeight());
        int width = (int) (JBomb.JBombFrame.getPreferredSize().getWidth() - centerPanelWidth) / 2;

        panel.setPreferredSize(new Dimension(width, height));
        panel.setOpaque(false);

        add(panel, side);
    }

    private void createRightPanel() {
        rightPanel = getRightPanel();
        createSidePanel(rightPanel, BorderLayout.EAST);
    }

    private void createLeftPanel() {
        leftPanel = getLeftPanel();
        createSidePanel(leftPanel, BorderLayout.WEST);
    }

    private void addButtons() {
        List<JButton> buttons = getButtons();

        for (JButton b : buttons) if (b != null) listButtonsPanel.add(b);
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

    public void refreshButtons() {
        List<JButton> alreadyAddedButtons = new ArrayList<>();
        Component[] components = listButtonsPanel.getComponents();

        for (Component component : components) {
            if (component instanceof JButton) {
                alreadyAddedButtons.add((JButton) component);
            }
        }

        for (JButton toAddButton : getButtons()) {
            if (toAddButton == null)
                continue;

            boolean isButtonAlreadyPresent = alreadyAddedButtons
                    .stream()
                    .anyMatch(e -> e.getText().trim().equalsIgnoreCase(toAddButton.getText().trim()));

            if (!isButtonAlreadyPresent) {
                Log.INSTANCE.e("adding " + toAddButton);
                listButtonsPanel.add(toAddButton);
            }
        }

        listButtonsPanel.repaint();
        repaint();
    }

    private void refreshPanels() {
        leftPanel.repaint();
        rightPanel.repaint();
    }

    @Override
    public void onShowCallback() {
        refreshButtons();
        refreshPanels();
        revalidate();
        repaint();
    }
}
