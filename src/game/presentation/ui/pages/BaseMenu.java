package game.presentation.ui.pages;

import game.JBomb;
import game.presentation.ui.frames.JBombFrame;
import game.presentation.ui.panels.game.PagePanel;
import game.presentation.ui.viewelements.bombermanbutton.RedButton;
import game.presentation.ui.viewelements.misc.Space;
import game.utils.Utility;
import game.utils.file_system.Paths;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseMenu extends PagePanel {
    protected JPanel listButtonsPanel;
    private JPanel leftPanel = null;
    private JPanel rightPanel = null;
    private JPanel topPanel = null;
    protected int maximumButtonCount = 5;

    public BaseMenu(CardLayout cardLayout, JPanel parent, JBombFrame frame, String imagePath) {
        super(cardLayout, parent, frame, imagePath);
        setupLayout();
    }

    public abstract boolean blurBackground();

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (blurBackground()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(0, 0, 0, 128)); // Black with 50% transparency
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
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

        // Create a wrapper for the top panel, side panels, and center panel
        // This is necessary so the components only take the needed space and
        // not the whole screen
        JPanel mainMenuComponentsWrapper = new JPanel(new BorderLayout());
        mainMenuComponentsWrapper.setOpaque(false);

        // Add the top panel to the north
        createTopPanel();
        if (topPanel != null) {
            mainMenuComponentsWrapper.add(topPanel, BorderLayout.NORTH);
        }

        // Add the side panels
        createLeftPanel();
        createRightPanel();

        if (leftPanel != null) {
            mainMenuComponentsWrapper.add(leftPanel, BorderLayout.WEST);
        }

        if (rightPanel != null) {
            mainMenuComponentsWrapper.add(rightPanel, BorderLayout.EAST);
        }

        // Add the center panel to the center of the wrapper
        createCenterPanel();
        if (listButtonsPanel != null) {
            mainMenuComponentsWrapper.add(listButtonsPanel.getParent(), BorderLayout.CENTER);
        }

        // Add the wrapper to the main panel
        add(mainMenuComponentsWrapper, BorderLayout.NORTH);
    }

    public boolean showAppLogo() {
        return true;
    }

    private void createCenterPanel() {
        createListButtonsPanel(); // Initializes listButtonsPanel
        addButtons();
    }


    private void createSidePanel(JPanel panel, String side) {
        if (panel == null) {
            panel = new JPanel();
        }

        // Ensure the panel has no background for transparency
        panel.setOpaque(false);

        // Create a wrapper panel with BorderLayout
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setOpaque(false); // Ensure transparency

        // Add the side panel to the top (NORTH) of the wrapper
        wrapperPanel.add(panel, BorderLayout.NORTH);

        // Add the wrapper panel to the specified side of the BaseMenu
        add(wrapperPanel, side);
    }

    private void createTopPanel() {
        topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout()); // Allows precise alignment
        topPanel.setOpaque(false); // Ensure transparency if needed

        BufferedImage logoImage = Utility.INSTANCE.loadImage(Paths.getGameLogo());

        int targetWidth = Utility.INSTANCE.px(400);
        int targetHeight = Utility.INSTANCE.px(400);

        // Get original dimensions of the logo
        int originalWidth = logoImage.getWidth();
        int originalHeight = logoImage.getHeight();

        // Calculate aspect ratio and adjust dimensions
        double aspectRatio = (double) originalWidth / originalHeight;
        if (originalWidth > originalHeight) {
            targetHeight = (int) (targetWidth / aspectRatio);
        } else {
            targetWidth = (int) (targetHeight * aspectRatio);
        }

        // Scale the logo while maintaining aspect ratio
        Image img = logoImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);

        // Create a label for the logo
        JLabel label = new JLabel();

        if (showAppLogo())
            label.setIcon(new ImageIcon(img));

        // align the logo to the bottom-center of top panel using GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // center horizontally
        gbc.gridy = 1; // place at the bottom
        gbc.weightx = 1.0; // use extra horizontal space
        gbc.weighty = 1.0; // use extra vertical space
        gbc.anchor = GridBagConstraints.SOUTH; // align to the bottom

        topPanel.add(label, gbc);

        // Set preferred size of the top panel
        int height = (int) (JBomb.JBombFrame.getPreferredSize().getHeight() / 3);
        int width = (int) (JBomb.JBombFrame.getPreferredSize().getWidth());
        topPanel.setPreferredSize(new Dimension(width, height));

        // Add topPanel to the main layout
        add(topPanel, BorderLayout.NORTH);
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
        int lastIndex = Math.min(maximumButtonCount, buttons.size());
        buttons = buttons.subList(0, lastIndex);

        int addedButtons = 0;
        for (JButton b : buttons)
            if (b != null) {
                listButtonsPanel.add(b);
                addedButtons++;
            }

        int emptySpacesToAdd = maximumButtonCount - addedButtons;

        for (int i = 0; i < emptySpacesToAdd; i++) {
            listButtonsPanel.add(new Space());
        }
    }

    /**
     * Creates and adds the listButtonsPanel to the MenuPanel.
     */
    private void createListButtonsPanel() {
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridBagLayout());

        listButtonsPanel = new JPanel();
        listButtonsPanel.setLayout(new GridLayout(0, 1)); // Vertical button alignment
        listButtonsPanel.setOpaque(false);

        listButtonsPanel.add(new Space());


        gridPanel.add(listButtonsPanel);
        gridPanel.setOpaque(false);

    }


    /**
     * The list of buttons to display. Max count is {$maximumButtonCount}
     *
     * @return
     */
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
