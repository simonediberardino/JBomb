package game.ui.panels.menu;

import game.ui.frames.BombermanFrame;
import game.ui.panels.game.PagePanel;
import game.utils.Paths;

import javax.swing.*;
import java.awt.*;

public abstract class BoxMenuPanel extends PagePanel {
    // Panels
    protected JBombermanBoxContainerPanel boxComponentsPanel;
    protected final String title;

    public BoxMenuPanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame, String title) {
        super(cardLayout, parent, frame, Paths.getBackgroundImage());
        this.title = title;
        initializeLayout();
    }

    abstract int getBoxPanelWidth();

    /**
     * Sets up the layout of the panel.
     */
    private void initializeLayout() {
        setLayout(new GridBagLayout());

        this.boxComponentsPanel = new JBombermanBoxContainerPanel(title, true) {
            @Override
            int getBoxPanelWidth() {
                return BoxMenuPanel.this.getBoxPanelWidth();
            }

            @Override
            protected void addCustomElements() {
                BoxMenuPanel.this.addCustomElements();
            }
        };
        boxComponentsPanel.initializeLayout();
        add(boxComponentsPanel);
    }


    protected abstract void addCustomElements();

    /**
     * Implementation of the onShowCallback() method in the PagePanel superclass.
     * Clears the componentsPanel and adds the stats settings elements again.
     */
    @Override
    public void onShowCallback() {
        boxComponentsPanel.refresh();
    }
}
