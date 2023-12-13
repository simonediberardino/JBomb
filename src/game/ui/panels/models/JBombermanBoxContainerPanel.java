package game.ui.panels.models;

import game.Bomberman;
import game.events.RunnablePar;
import game.localization.Localization;
import game.ui.helpers.Padding;
import game.ui.pages.MainMenuPanel;
import game.ui.viewelements.bombermanbutton.RedButton;
import game.ui.viewelements.bombermanbutton.YellowButton;
import game.ui.viewelements.bombermanpanel.BombermanPanelYellow;
import game.ui.viewelements.settings.InfoElementView;
import game.ui.viewelements.settings.SettingsElementView;
import game.ui.viewelements.settings.SlideElementView;
import game.ui.viewelements.settings.TextFieldElementView;
import game.utils.Utility;
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

    protected abstract int getBoxPanelWidth();

    /**
     * Sets up the layout of the panel.
     */
    public void initializeLayout() {
        setupPanels();
        setLayout(new GridBagLayout());

        componentsPanel.setLayout(new BoxLayout(componentsPanel, BoxLayout.Y_AXIS));

        add(boxPanel);
        addSettingsElements();
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

        return new Dimension(getBoxPanelWidth(), (int) (panelHeight));
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
        parentPanel.setPreferredSize(new Dimension(getBoxPanelWidth(), (int) (insidePanelSize.getHeight())));
    }

    /**
     * Adds the stats settings elements to the componentsPanel.
     */
    private void addSettingsElements() {
        componentsPanel.add(new Padding(getWidth(), DEFAULT_Y_PADDING * 2));
        titleButton = new YellowButton(title);
        componentsPanel.add(titleButton);
        componentsPanel.add(new Padding(getWidth(), DEFAULT_Y_PADDING));

        this.addCustomElements();

        if (isBackEnabled) {
            JButton mainMenuButton = new RedButton(Localization.get(MAIN_MENU));
            mainMenuButton.addActionListener((l) -> back());

            componentsPanel.add(new Padding(getWidth(), DEFAULT_Y_PADDING));
            componentsPanel.add(mainMenuButton);
        }

        componentsPanel.add(new Padding(getWidth(), DEFAULT_Y_PADDING * 2));
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
        componentsPanel.add(elementView);

        return elementView;
    }

    public JLabel addImageLabel(String imageName, Dimension dimension) {
        Image image = Utility.loadImage(imageName);
        assert image != null;

        image = image.getScaledInstance((int) dimension.getWidth(), (int) dimension.getHeight(), 0);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setPreferredSize(dimension);
        componentsPanel.add(imageLabel);
        return imageLabel;
    }

    public TextFieldElementView addTextFieldElementView(String title, String startText, RunnablePar callback) {
        return addTextFieldElementView(title, startText, callback, -1);
    }

    public TextFieldElementView addTextFieldElementView(String title, String startText, RunnablePar callback, int charLimit) {
        TextFieldElementView elementView = new TextFieldElementView(boxPanel, title, startText, callback, charLimit);
        componentsPanel.add(elementView);

        return elementView;
    }

    public SlideElementView addSlideElementView(String title, int currValue, RunnablePar callback) {
        SlideElementView elementView = new SlideElementView(boxPanel, title, currValue, callback);
        componentsPanel.add(elementView);
        return elementView;
    }


    private void back() {
        componentsPanel.removeAll();
        Bomberman.showActivity(MainMenuPanel.class);
    }
}
