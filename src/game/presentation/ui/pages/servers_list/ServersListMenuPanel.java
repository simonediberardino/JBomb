package game.presentation.ui.pages.servers_list;

import game.Bomberman;
import game.domain.events.models.RunnablePar;
import game.domain.level.levels.lobby.WaitingRoomLevel;
import game.localization.Localization;
import game.network.gamehandler.ClientGameHandler;
import game.presentation.ui.frames.BombermanFrame;
import game.presentation.ui.pages.play.PlayMenuPanel;
import game.presentation.ui.panels.models.BoxMenuPanel;
import game.presentation.ui.viewelements.bombermanbutton.RedButton;
import game.presentation.ui.viewelements.bombermanbutton.YellowButton;
import game.utils.Utility;
import game.utils.file_system.Paths;

import javax.swing.*;
import java.awt.*;

import static game.localization.Localization.*;

public class ServersListMenuPanel extends BoxMenuPanel {
    protected String enteredIpAddress = "";

    public ServersListMenuPanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame) {
        super(cardLayout, parent, frame, Localization.get(Localization.SERVERS_LIST_TITLE), Paths.getMainMenuWallpaper(), false);
    }

    private void createBackButton() {
        JButton b = new RedButton(get(BACK));
        b.addActionListener(l -> Bomberman.showActivity(PlayMenuPanel.class));
        boxComponentsPanel.addComponent(b);
    }

    private void addIpTextField() {
        boxComponentsPanel.addTextFieldElementView(Localization.get(SERVERS_LIST_INPUT), Localization.get(INSERT), new RunnablePar() {
            @Override
            public <T> Object execute(T par) {
                if (par.toString().isEmpty()) return null;
                enteredIpAddress = par.toString();
                return null;
            }
        });
    }

    private void createConnectButton() {
        JButton b = new YellowButton(get(CONNECT));
        b.addActionListener(l -> connect());
        boxComponentsPanel.addComponent(b);
    }

    private void connect() {
        Bomberman.startLevel(new WaitingRoomLevel(), new ClientGameHandler(enteredIpAddress, 28960));
    }

    @Override
    protected int getBoxPanelWidth() {
        return Utility.INSTANCE.px(1000);
    }

    @Override
    protected void addCustomElements() {
        padding();
        addIpTextField();
        padding();
        padding();
        createConnectButton();
        createBackButton();
    }

    @Override
    public void onShowCallback() {
        super.onShowCallback();
        enteredIpAddress = "";
    }
}
