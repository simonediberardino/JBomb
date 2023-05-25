package game.ui.panels.menus;

import game.Bomberman;
import game.data.DataInputOutput;
import game.localization.Localization;
import game.ui.helpers.Padding;
import game.ui.panels.BombermanFrame;
import game.ui.panels.PagePanel;
import game.utils.Paths;
import game.utils.Utility;
import game.values.Dimensions;
import game.viewelements.bombermanbutton.RedButton;
import game.viewelements.bombermanbutton.YellowButton;
import game.viewelements.bombermanpanel.BombermanPanelYellow;
import game.viewelements.misc.Space;
import game.viewelements.settings.SettingsElementView;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static game.localization.Localization.*;
import static game.values.Dimensions.DEFAULT_PADDING;

public class ProfilePanel extends PagePanel {
    private static final int WIDTH = Utility.px(800); // The width of the panel

    // Panels
    private final JPanel boxPanel = new BombermanPanelYellow(); // Parent yellow box, containing title and stats;
    private final JPanel componentsPanel = new JPanel(); // Panel containing stats and title;

    /**
     Constructs a ProfilePanel object.
     @param cardLayout the CardLayout of the parent container
     @param parent the parent container of this panel
     @param frame the BombermanFrame object */
    public ProfilePanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame) {
        super(cardLayout, parent, frame, Paths.getBackgroundImage());
        initializeLayout();
    }

    /**

     Sets up the layout of the panel. */
    private void initializeLayout() {
        setupPanels();
        setLayout(new GridBagLayout());

        add(boxPanel);
        addSettingsElements();
        setPanelSizes(componentsPanel, boxPanel);
    }

    /**
     Sets up the layouts of the boxPanel and the componentsPanel.
     */
    private void setupPanels() {
        boxPanel.setLayout(new GridBagLayout());
        boxPanel.add(componentsPanel);
        componentsPanel.setOpaque(false);
    }

    /**

     Calculates the preferred size of the insidePanel based on its components.
     @param insidePanel the panel to calculate the preferred size for
     @return a Dimension object representing the preferred size of the insidePanel */
    private Dimension calculateInsidePanelSize(JPanel insidePanel) {
        double panelHeight = Arrays.stream(
                insidePanel.getComponents()
        ).map(e -> e.getPreferredSize().getHeight() + Dimensions.DEFAULT_PADDING).mapToInt(Double::intValue).sum();

        return new Dimension(WIDTH, (int) (panelHeight));
    }

    /**
     Sets the preferred sizes for the componentsPanel and the parent boxPanel.
     @param componentsPanel the components panel
     @param parentPanel the parent panel */
    private void setPanelSizes(JPanel componentsPanel, JPanel parentPanel) {
        Dimension insidePanelSize = calculateInsidePanelSize(componentsPanel);
        componentsPanel.setPreferredSize(insidePanelSize);
        parentPanel.setPreferredSize(new Dimension(WIDTH, (int) (insidePanelSize.getHeight())));
    }

    /**
     Adds the stats settings elements to the componentsPanel.
     */
    private void addSettingsElements() {
        componentsPanel.add(new Padding(getWidth(), DEFAULT_PADDING*2));
        componentsPanel.add(new YellowButton(Localization.get(PLAYER_STATS)));
        componentsPanel.add(new Padding(getWidth(), DEFAULT_PADDING));
        SettingsElementView killsElement = addSettingsElement(Localization.get(Localization.KILLS), String.valueOf(DataInputOutput.getPlayerDataObject().getKills()));
        SettingsElementView deathsElement = addSettingsElement(Localization.get(Localization.DEATHS), String.valueOf(DataInputOutput.getPlayerDataObject().getDeaths()));
        SettingsElementView roundsElement = addSettingsElement(Localization.get(Localization.ROUNDS), String.valueOf(DataInputOutput.getPlayerDataObject().getRounds()));
        SettingsElementView lostGamesElement = addSettingsElement(Localization.get(Localization.LOST_GAMES), String.valueOf(DataInputOutput.getPlayerDataObject().getLostGames()));
        SettingsElementView pointsElement = addSettingsElement(Localization.get(Localization.POINTS), String.valueOf(DataInputOutput.getPlayerDataObject().getPoints()));
        SettingsElementView livesElement = addSettingsElement(Localization.get(Localization.LIVES), String.valueOf(DataInputOutput.getPlayerDataObject().getLives()));


        JButton mainMenuButton = new RedButton(Localization.get(MAIN_MENU));
        mainMenuButton.addActionListener((l) -> back());
        componentsPanel.add(new Padding(getWidth(), DEFAULT_PADDING));
        componentsPanel.add(mainMenuButton);
    }

    /**

     Adds a single stats settings element to the componentsPanel.
     @param title the title of the element
     @param val the value of the element
     @return the SettingsElementView object that was added to the componentsPanel
     */
    private SettingsElementView addSettingsElement(String title, String val) {
        SettingsElementView elementView = new SettingsElementView(boxPanel, title, val);
        componentsPanel.add(elementView);

        return elementView;
    }

    /**

     Implementation of the onShowCallback() method in the PagePanel superclass.
     Clears the componentsPanel and adds the stats settings elements again. */
    @Override
    public void onShowCallback() {
        componentsPanel.removeAll();
        addSettingsElements();
    }

    private void back() {
        componentsPanel.removeAll();
        Bomberman.show(MainMenuPanel.class);
    }
}
