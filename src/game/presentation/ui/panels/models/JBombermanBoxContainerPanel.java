package game.presentation.ui.panels.models;

import game.JBomb;
import game.domain.events.models.RunnablePar;
import game.localization.Localization;
import game.presentation.ui.helpers.Padding;
import game.presentation.ui.pages.main_menu.MainMenuPanel;
import game.presentation.ui.viewelements.bombermanbutton.RedButton;
import game.presentation.ui.viewelements.bombermanbutton.YellowButton;
import game.presentation.ui.viewelements.bombermanpanel.BombermanPanelYellow;
import game.presentation.ui.viewelements.settings.InfoElementView;
import game.presentation.ui.viewelements.settings.SettingsElementView;
import game.presentation.ui.viewelements.settings.SlideElementView;
import game.presentation.ui.viewelements.settings.JBombTextFieldTagged;
import game.utils.Utility;
import game.utils.ui.Utils2D;
import game.values.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static game.localization.Localization.MAIN_MENU;
import static game.values.Dimensions.DEFAULT_Y_PADDING;

public abstract class JBombermanBoxContainerPanel extends JPanel {
    protected YellowButton titleButton;
    protected final JPanel boxPanel = new BombermanPanelYellow(); // Parent yellow box, containing title and stats;
    protected final JPanel componentsPanel = new CenteredPanel(); // Panel containing stats and title;
    private final boolean isBackEnabled;
    private final String title;

    public JBombermanBoxContainerPanel(String title, boolean isBackEnabled) {
        this.title = title;
        this.isBackEnabled = isBackEnabled;
    }

    protected abstract int getDefaultBoxPanelWidth();

    public int calculateContainerWidth() {
        return Math.max(getDefaultBoxPanelWidth(), Utils2D.INSTANCE.calculateMaxWidth(componentsPanel));
    }

    /**
     * Sets up the layout of the panel.
     */
    public void initializeLayout() {
        setupPanels();
        setLayout(new GridBagLayout());

        componentsPanel.setLayout(new BoxLayout(componentsPanel, BoxLayout.Y_AXIS));

        add(boxPanel);
        addSettingsElements();
    }

    private void refreshPanelSize() {
        setPanelSizes(componentsPanel, boxPanel);
    }
    
    /**
     * Sets up the layouts of the boxPanel and the componentsPanel.
     */
    private void setupPanels() {
        boxPanel.setLayout(new GridBagLayout());
        boxPanel.add(componentsPanel);
        componentsPanel.setOpaque(false);
    }

    /**
     * Calculates the preferred size of the insidePanel based on its components.
     *
     * @param insidePanel the panel to calculate the preferred size for
     * @return a Dimension object representing the preferred size of the insidePanel
     */
    private Dimension calculateInsidePanelSize(JPanel insidePanel) {
        double panelHeight = Arrays.stream(
                insidePanel.getComponents()
        ).map(e -> e.getPreferredSize().getHeight() + Dimensions.DEFAULT_Y_PADDING).mapToInt(Double::intValue).sum();

        return new Dimension(calculateContainerWidth(), (int) (panelHeight));
    }

    /**
     * Sets the preferred sizes for the componentsPanel and the parent boxPanel.
     *
     * @param componentsPanel the components panel
     * @param parentPanel     the parent panel
     */
    private void setPanelSizes(JPanel componentsPanel, JPanel parentPanel) {
        Dimension insidePanelSize = calculateInsidePanelSize(componentsPanel);
        componentsPanel.setPreferredSize(insidePanelSize);
        parentPanel.setPreferredSize(new Dimension(calculateContainerWidth(), (int) (insidePanelSize.getHeight())));
    }

    /**
     * Adds the stats settings elements to the componentsPanel.
     */
    private void addSettingsElements() {
        addComponent(new Padding(getWidth(), DEFAULT_Y_PADDING * 2));
        titleButton = new YellowButton(title);
        addComponent(titleButton);
        addComponent(new Padding(getWidth(), DEFAULT_Y_PADDING));

        this.addCustomElements();

        if (isBackEnabled) {
            JButton mainMenuButton = new RedButton(Localization.get(MAIN_MENU));
            mainMenuButton.addActionListener((l) -> back());

            addComponent(new Padding(getWidth(), DEFAULT_Y_PADDING));
            addComponent(mainMenuButton);
        }

        addComponent(new Padding(getWidth(), DEFAULT_Y_PADDING * 2));
    }

    protected void refresh() {
        componentsPanel.removeAll();
        addSettingsElements();
    }

    protected abstract void addCustomElements();

    /**
     * Adds a single stats settings element to the componentsPanel.
     *
     * @param title the title of the element
     * @param val   the value of the element
     * @return the SettingsElementView object that was added to the componentsPanel
     */
    public SettingsElementView addInfoElement(String title, String val) {
        InfoElementView elementView = new InfoElementView(boxPanel, title, val);
        addComponent(elementView);

        return elementView;
    }

    public void addComponent(Component component) {
        componentsPanel.add(component);
        refreshPanelSize();
    }
    
    public JLabel addImageLabel(String imageName, Dimension dimension) {
        Image image = Utility.INSTANCE.loadImage(imageName);
        assert image != null;

        image = image.getScaledInstance((int) dimension.getWidth(), (int) dimension.getHeight(), 0);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setPreferredSize(dimension);
        addComponent(imageLabel);
        return imageLabel;
    }

    public JBombTextFieldTagged addTextFieldElementView(String title, String startText, RunnablePar callback) {
        return addTextFieldElementView(title, startText, callback, -1);
    }

    public JBombTextFieldTagged addTextFieldElementView(String title, String startText, RunnablePar callback, int charLimit) {
        JBombTextFieldTagged elementView = new JBombTextFieldTagged(boxPanel, title, startText, callback, charLimit);
        addComponent(elementView);

        return elementView;
    }

    public SlideElementView addSlideElementView(String title, int currValue, RunnablePar callback) {
        SlideElementView elementView = new SlideElementView(boxPanel, title, currValue, callback);
        addComponent(elementView);
        return elementView;
    }


    private void back() {
        componentsPanel.removeAll();
        JBomb.showActivity(MainMenuPanel.class);
    }
}
