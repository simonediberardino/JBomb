package game.presentation.ui.pages.multiplayer;

import game.localization.Localization;
import game.presentation.ui.panels.models.CenteredPanel;
import game.presentation.ui.panels.models.JBombermanBoxContainerPanel;
import game.presentation.ui.viewelements.bombermanbutton.RedButton;
import game.presentation.ui.viewelements.bombermanpanel.BombermanPanelYellow;
import game.utils.Utility;
import game.values.Dimensions;

import javax.swing.*;
import java.awt.*;

import static game.localization.Localization.GAME_ENDED_MP;

public class GameEndedMultiplayerPanel extends JBombermanBoxContainerPanel {
    private final JDialog dialog;  // Store the dialog reference

    public GameEndedMultiplayerPanel(JDialog dialog) {
        super(Localization.get(GAME_ENDED_MP), false, new BombermanPanelYellow());
        this.dialog = dialog;  // Initialize the dialog reference
        this.initializeLayout();
    }

    @Override
    protected int getDefaultBoxPanelWidth() {
        return Utility.INSTANCE.px(Dimensions.DEFAULT_MAIN_MENU_BOX_SIZE);
    }

    @Override
    protected void addCustomElements() {
        JPanel panel = new CenteredPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        int buttonWidth = getDefaultBoxPanelWidth() - Dimensions.DEFAULT_X_PADDING;

        for (int i = 0; i < 15; i++) {
            PlayerScoreLabel playerScoreLabel = new PlayerScoreLabel(buttonWidth,
                    "test",
                    0,
                    i + 1);
            panel.add(playerScoreLabel);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(getDefaultBoxPanelWidth(), getDefaultBoxPanelWidth()));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        addComponent(scrollPane);

        RedButton exitButton = new RedButton(Localization.get(Localization.CONTINUE));
        exitButton.addActionListener(e -> {
            dialog.dispose();  // Close the dialog when the button is clicked
        });

        addComponent(exitButton);
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    public static void createAndShowDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame);
        dialog.setUndecorated(true);
        dialog.setSize(parentFrame.getSize());
        dialog.setLocationRelativeTo(null);

        JPanel blurBackground = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 0, 0, 150));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        blurBackground.setOpaque(false);
        blurBackground.setLayout(new BorderLayout());

        // Pass the dialog to the GameEndedMultiplayerPanel constructor
        GameEndedMultiplayerPanel panel = new GameEndedMultiplayerPanel(dialog);
        blurBackground.add(panel, BorderLayout.CENTER);
        dialog.getContentPane().add(blurBackground);
        dialog.setBackground(new Color(0, 0, 0, 0));
        dialog.setModalityType(Dialog.ModalityType.MODELESS);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }

}
