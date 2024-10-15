package game.presentation.ui.pages.servers_list;

import game.JBomb;
import game.domain.events.models.RunnablePar;
import game.domain.level.levels.lobby.WaitingRoomLevel;
import game.domain.match.JBombMatch;
import game.localization.Localization;
import game.network.gamehandler.ClientGameHandler;
import game.presentation.ui.frames.JBombFrame;
import game.presentation.ui.pages.play.PlayMenuPanel;
import game.presentation.ui.pages.server_browser.ServerBrowserPanel;
import game.presentation.ui.pages.server_browser.ServerInfo;
import game.presentation.ui.panels.models.BoxMenuPanel;
import game.presentation.ui.viewelements.bombermanbutton.RedButton;
import game.presentation.ui.viewelements.bombermanbutton.YellowButton;
import game.presentation.ui.viewelements.settings.JBombTextFieldTagged;
import game.properties.RuntimeProperties;
import game.utils.Utility;
import game.utils.file_system.Paths;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static game.localization.Localization.*;
import static game.presentation.ui.pages.server_browser.ServerBrowserPanel.createScrollableServerBrowser;
import static game.values.Dimensions.DEFAULT_X_PADDING;

public class ServersListMenuPanel extends BoxMenuPanel {
    protected String enteredIpAddress = "";
    private JBombTextFieldTagged input = null;

    public ServersListMenuPanel(CardLayout cardLayout, JPanel parent, JBombFrame frame) {
        super(cardLayout, parent, frame, Localization.get(Localization.SERVERS_LIST_TITLE), Paths.getMainMenuWallpaper(), false);
    }

    private void createBackButton() {
        JButton b = new RedButton(get(BACK));
        b.addActionListener(l -> JBomb.showActivity(PlayMenuPanel.class));
        boxComponentsPanel.addComponent(b);
    }

    private void addIpTextField() {
        String lastConnectedIp = RuntimeProperties.INSTANCE.getLastConnectedIp();
        String ipAddressInputText = lastConnectedIp.isEmpty() ? Localization.get(INSERT) : lastConnectedIp;

        enteredIpAddress = lastConnectedIp;

        RunnablePar textChangedCallback = new RunnablePar() {
            @Override
            public <T> Object execute(T par) {
                if (par.toString().isEmpty()) return null;
                enteredIpAddress = par.toString();
                return null;
            }
        };

        RunnablePar onClickCallback = new RunnablePar() {
            @Override
            public <T> Object execute(T par) {
                if (enteredIpAddress.isEmpty()) {
                    JTextField source = (JTextField) (par);
                    source.setText("");
                }
                return null;
            }
        };

        input = boxComponentsPanel.addTextFieldElementView(
                Localization.get(SERVERS_LIST_INPUT),
                ipAddressInputText,
                textChangedCallback,
                onClickCallback
        );
    }

    private void createConnectButton() {
        JButton b = new YellowButton(get(CONNECT));
        b.addActionListener(l -> connect());
        boxComponentsPanel.addComponent(b);
    }

    private void connect() {
        RuntimeProperties.INSTANCE.setLastConnectedIp(enteredIpAddress);
        JBomb.startLevel(new WaitingRoomLevel(), new ClientGameHandler(enteredIpAddress, JBombMatch.Companion.getDefaultPort()));
    }

    @Override
    protected int getBoxPanelWidth() {
        return Utility.INSTANCE.px(1000);
    }

    @Override
    protected void addCustomElements() {
        padding();
        /*addIpTextField();
        padding();
        padding();
        createConnectButton();
        createBackButton();*/

        JScrollPane serverBrowser = createScrollableServerBrowser(
                getBoxPanelWidth() - DEFAULT_X_PADDING,
                Arrays.asList(
                        new ServerInfo("Multiplayer: Castello di Dracula", 10, 20),
                        new ServerInfo("Arena: Arena mondo 1", 0, 20),
                        new ServerInfo("Arena: Arena mondo 1", 0, 20),

                        new ServerInfo("Arena: Arena mondo 2", 0, 20)
                )
        );

        boxComponentsPanel.addComponent(serverBrowser);
    }

    @Override
    public void onShowCallback() {
        super.onShowCallback();
    }
}
