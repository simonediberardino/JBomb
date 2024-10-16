package game.presentation.ui.panels.models;

import game.presentation.ui.frames.JBombFrame;
import game.presentation.ui.helpers.Padding;
import game.presentation.ui.panels.game.PagePanel;
import game.presentation.ui.viewelements.bombermanpanel.BombermanPanel;
import game.presentation.ui.viewelements.bombermanpanel.BombermanPanelYellow;
import game.utils.file_system.Paths;

import javax.swing.*;
import java.awt.*;

import static game.values.Dimensions.DEFAULT_Y_PADDING;

public abstract class BoxMenuPanel extends PagePanel {
    protected JBombermanBoxContainerPanel boxComponentsPanel;
    protected BombermanPanel bombermanPanel;
    protected final String title;
    protected boolean isBackEnabled;

    public BoxMenuPanel(
            CardLayout cardLayout,
            JPanel parent,
            JBombFrame frame,
            String title
    ) {
        this(cardLayout, parent, frame, title, Paths.getMainMenuWallpaper(), true, new BombermanPanelYellow());
    }


    public BoxMenuPanel(
            CardLayout cardLayout,
            JPanel parent,
            JBombFrame frame,
            String title,
            String background,
            boolean isBackEnabled,
            BombermanPanel containerPanel
    ) {
        super(cardLayout, parent, frame, background);
        this.title = title;
        this.isBackEnabled = isBackEnabled;
        this.bombermanPanel = containerPanel;
        initializeLayout();
    }

    public BoxMenuPanel(
            CardLayout cardLayout,
            JPanel parent,
            JBombFrame frame,
            String title,
            String background
    ) {
        this(cardLayout, parent, frame, title, background, true);
    }

    public BoxMenuPanel(
            CardLayout cardLayout,
            JPanel parent,
            JBombFrame frame,
            String title,
            String background,
            boolean isBackEnabled
    ) {
        super(cardLayout, parent, frame, background);
        this.title = title;
        this.isBackEnabled = isBackEnabled;
        initializeLayout();
    }

    protected abstract int getBoxPanelWidth();

    private void initializeLayout() {
        setLayout(new GridBagLayout());

        this.boxComponentsPanel = new JBombermanBoxContainerPanel(title, isBackEnabled, bombermanPanel) {
            @Override
            protected int getDefaultBoxPanelWidth() {
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

    protected void padding() {
        boxComponentsPanel.addComponent(new Padding(0, DEFAULT_Y_PADDING));
    }

    @Override
    public void onShowCallback() {
        boxComponentsPanel.refresh();
    }
}
