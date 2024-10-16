package game.presentation.ui.pages.server_browser;

import game.JBomb;
import game.domain.events.models.RunnablePar;
import game.domain.level.levels.lobby.WaitingRoomLevel;
import game.domain.match.JBombMatch;
import game.localization.Localization;
import game.network.gamehandler.ClientGameHandler;
import game.presentation.ui.frames.JBombFrame;
import game.presentation.ui.pages.multiplayer.MultiplayerPanel;
import game.presentation.ui.panels.models.BoxMenuPanel;
import game.presentation.ui.viewelements.bombermanbutton.RedButton;
import game.presentation.ui.viewelements.bombermanbutton.YellowButton;
import game.presentation.ui.viewelements.settings.JBombTextFieldTagged;
import game.properties.RuntimeProperties;
import game.utils.Utility;
import game.utils.file_system.Paths;
import game.values.Dimensions;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static game.localization.Localization.*;
import static game.presentation.ui.pages.server_browser.ServerBrowserPanel.createScrollableServerBrowser;
import static game.values.Dimensions.DEFAULT_X_PADDING;
import static game.values.Dimensions.FONT_SIZE_MID;

public class ServersListMenuPanel extends BoxMenuPanel {
    // Stores the entered IP address from the input field
    private String enteredIpAddress = "";

    // TextField component for entering the IP address
    private JBombTextFieldTagged input = null;

    /**
     * Constructor for ServersListMenuPanel.
     * Initializes the panel with layout, parent container, frame, and basic settings.
     *
     * @param cardLayout Layout for switching between different panels
     * @param parent     Parent JPanel containing this panel
     * @param frame      Main application frame
     */
    public ServersListMenuPanel(CardLayout cardLayout, JPanel parent, JBombFrame frame) {
        super(cardLayout, parent, frame,
                Localization.get(Localization.SERVERS_LIST_TITLE),  // Fetches the title from localization
                Paths.getMainMenuWallpaper(),                      // Loads wallpaper background
                false);                                            // Indicates that background scrolling is disabled
    }

    /**
     * Creates the "Back" button that navigates the user to the MultiplayerPanel.
     * The button is styled with a red color and appropriate font size.
     */
    private void createBackButton() {
        JButton backButton = new RedButton(get(BACK), FONT_SIZE_MID); // Creates a red button for the 'Back' action
        backButton.addActionListener(event -> JBomb.showActivity(MultiplayerPanel.class)); // Sets action listener to switch panels
        boxComponentsPanel.addComponent(backButton); // Adds button to the UI panel
    }

    /**
     * Connects the client to the server using the entered IP address.
     * Stores the last connected IP in runtime properties and starts a new game session.
     */
    private void connect(String ipAddress) {
        // Saves the entered IP address as the last connected IP
        RuntimeProperties.INSTANCE.setLastConnectedIp(ipAddress);

        String[] toks = ipAddress.split(":");
        String ipv4 = toks[0];
        int port;

        try {
            port = Integer.parseInt(toks[1]);
        } catch (Exception exception) {
            port = JBombMatch.Companion.getPort();
        }

        // Initiates the client-side connection to the server and starts the waiting room level
        JBomb.startLevel(
                new WaitingRoomLevel(), // Initiates a waiting room while the server connects
                new ClientGameHandler(ipv4, port) // Sets up the client game handler
        );
    }

    /**
     * Defines the width of the box panel containing the elements in pixels.
     * This ensures that the panel's width remains consistent.
     *
     * @return Width of the box panel in pixels.
     */
    @Override
    protected int getBoxPanelWidth() {
        return Utility.INSTANCE.px(1000); // Converts the logical width value into pixel size
    }

    /**
     * Adds a text field for entering the IP address. It uses a default IP address if available,
     * otherwise, shows a placeholder indicating the user should insert an IP.
     * <p>
     * It updates `enteredIpAddress` every time the text in the field changes.
     */
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

    /**
     * Creates a "Connect" button that triggers the connection process when clicked.
     * The button is styled in yellow and uses a mid-sized font.
     */
    private void createConnectButton() {
        JButton connectButton = new YellowButton(get(CONNECT), FONT_SIZE_MID); // Creates a yellow 'Connect' button
        connectButton.addActionListener(event -> connect(enteredIpAddress)); // Sets action listener to initiate the connection
        boxComponentsPanel.addComponent(connectButton); // Adds the button to the UI panel
    }

    /**
     * Creates a scrollable panel that lists available servers for the player to choose from.
     * The server browser displays multiple predefined server options.
     *
     * @return A scrollable panel with the server list.
     */
    private JScrollPane createServerBrowser() {
        RunnablePar connectRunnable = new RunnablePar() {
            @Nullable
            @Override
            public <T> Object execute(T par) {
                String ip = (String) par;
                connect(ip);
                return null;
            }
        };

        // List of available servers with predefined names, IPs, ports, and player limits
        List<ServerInfo> serverList = Arrays.asList(
                new ServerInfo("Multiplayer: Castello di Dracula", "localhost", 30960, 10, 20),
                new ServerInfo("Arena: Arena mondo 1", "localhost", 30960, 0, 20),
                new ServerInfo("Arena: Arena mondo 2", "localhost", 30960, 0, 20),
                new ServerInfo("Arena: Arena mondo 3", "localhost", 30960, 0, 20)
        );

        // Creates a scrollable view for the server list with a defined width and height
        return createScrollableServerBrowser(
                getBoxPanelWidth() - DEFAULT_X_PADDING, // Dynamic width based on the panel width
                Utility.INSTANCE.px(300),               // Fixed height for the server browser
                serverList,                             // Pass the list of servers to be displayed
                connectRunnable
        );
    }

    /**
     * Adds custom UI elements to the panel including buttons and the server browser.
     * It also adds spacing and handles layout adjustments.
     */
    @Override
    protected void addCustomElements() {
        // Adds a button for opening the server browser
        boxComponentsPanel.addComponent(
                new YellowButton(get(Localization.SERVERS_BROWSER), Dimensions.FONT_SIZE_MID)
        );

        // Adds the scrollable server browser to the panel
        boxComponentsPanel.addComponent(createServerBrowser());

        // Adds spacing between elements
        addPadding();

        // Adds a button for directly connecting to a server via IP address
        boxComponentsPanel.addComponent(
                new YellowButton(get(Localization.CONNECT_DIRECTLY), Dimensions.FONT_SIZE_MID)
        );

        addPadding(); // Adds spacing

        // Adds the IP address input field
        addIpTextField();

        addPadding(); // Adds spacing

        // Adds the "Connect" button to initiate the connection
        createConnectButton();

        addPadding(); // Adds spacing

        // Adds the "Back" button to navigate back to the Multiplayer menu
        createBackButton();
    }

    /**
     * Utility method to add padding between components.
     * This enhances the layout by providing spacing between UI elements.
     */
    private void addPadding() {
        padding(); // Assuming 'padding()' is defined elsewhere to add consistent spacing between components
    }

    /**
     * Callback method that is executed when the panel is shown.
     * This method can be used for updating or refreshing the UI when the panel is displayed.
     */
    @Override
    public void onShowCallback() {
        super.onShowCallback(); // Calls the parent class' callback to ensure the default behavior is retained
    }
}
