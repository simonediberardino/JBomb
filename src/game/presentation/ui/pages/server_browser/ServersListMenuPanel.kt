package game.presentation.ui.pages.server_browser

import game.JBomb
import game.domain.events.models.RunnablePar
import game.domain.level.levels.lobby.WaitingRoomLevel
import game.domain.match.JBombMatch
import game.localization.Localization
import game.network.gamehandler.ClientGameHandler
import game.network.usecases.FetchAllServersFromMasterServerUseCase
import game.presentation.ui.frames.JBombFrame
import game.presentation.ui.pages.multiplayer.MultiplayerPanel
import game.presentation.ui.pages.server_browser.ServerBrowserPanel.Companion.createScrollableServerBrowser
import game.presentation.ui.panels.models.BoxMenuPanel
import game.presentation.ui.viewelements.bombermanbutton.RedButton
import game.presentation.ui.viewelements.bombermanbutton.YellowButton
import game.presentation.ui.viewelements.settings.JBombTextFieldTagged
import game.properties.RuntimeProperties
import game.usecases.ConnectToServerUseCase
import game.utils.Utility
import game.utils.file_system.Paths
import game.values.BomberColors
import game.values.Dimensions
import kotlinx.coroutines.*
import java.awt.CardLayout
import java.awt.Color
import java.awt.Dimension
import javax.swing.*

// Panel displaying the list of available game servers
open class ServersListMenuPanel(
    cardLayout: CardLayout?,
    parent: JPanel?,
    frame: JBombFrame?
) : BoxMenuPanel(
    cardLayout, parent, frame,
    Localization.get(Localization.SERVERS_LIST_TITLE),
    Paths.mainMenuWallpaper,
    false
) {
    private var enteredIpAddress = ""
    private var ipAddressInputField: JBombTextFieldTagged? = null
    private lateinit var serverListContainer: JPanel
    private lateinit var scrollPane: JScrollPane
    private lateinit var loadingIndicator: JProgressBar // Component for loading indicator
    private val serversListHeight = Utility.px(300)

    // Adds the input field for the IP address of the server
    private fun addIpAddressInputField() {
        val lastConnectedIp = RuntimeProperties.lastConnectedIp
        val defaultIpText = lastConnectedIp.ifEmpty { Localization.get(Localization.INSERT) }

        enteredIpAddress = lastConnectedIp
        val textChangedCallback: RunnablePar = object : RunnablePar {
            override fun <T> execute(par: T): Any? {
                if (par.toString().isNotEmpty()) {
                    enteredIpAddress = par.toString()
                }
                return null
            }
        }

        val onClickCallback: RunnablePar = object : RunnablePar {
            override fun <T> execute(par: T): Any? {
                if (enteredIpAddress.isEmpty()) {
                    (par as JTextField).text = ""
                }

                return null
            }
        }

        ipAddressInputField = boxComponentsPanel.addTextFieldElementView(
            Localization.get(Localization.SERVERS_LIST_INPUT),
            defaultIpText,
            textChangedCallback,
            onClickCallback
        )
    }

    // Creates the "Connect" button
    private fun createConnectButton() {
        val connectButton: JButton = YellowButton(
            Localization.get(Localization.CONNECT),
            Dimensions.FONT_SIZE_MID
        )
        connectButton.addActionListener {
            ConnectToServerUseCase(enteredIpAddress).invokeBlocking()
        }
        boxComponentsPanel.addComponent(connectButton)
    }

    // Fetches the list of servers from the master server
    private fun fetchAvailableServers() {
        showLoadingIndicator() // Show loading indicator

        // Launch coroutine to fetch servers
        JBomb.scope.launch {
            val servers = FetchAllServersFromMasterServerUseCase().invoke() ?: emptyList() // Fetch servers

            // Runnable for connecting to the selected server
            val connectRunnable: RunnablePar = object : RunnablePar {
                override fun <T> execute(par: T): Any? {
                    val ip = par as String
                    ConnectToServerUseCase(ip).invokeBlocking()
                    return null
                }
            }

            // Create the scroll pane with the server list
            scrollPane = createScrollableServerBrowser(
                width = boxPanelWidth - Dimensions.DEFAULT_X_PADDING,
                height = serversListHeight,
                servers = servers,
                connectRunnable = connectRunnable
            )

            val occupiedHeight = servers.size * ServerButton.height + ServerBrowserPanel.padding * servers.size
            serverListContainer.preferredSize = Dimension(boxPanelWidth - Dimensions.DEFAULT_X_PADDING, occupiedHeight)

            // Clear existing components if needed
            serverListContainer.removeAll()
            serverListContainer.add(scrollPane) // Add scroll pane to the container
            serverListContainer.revalidate() // Refresh the layout
            serverListContainer.repaint() // Repaint the container

            removeLoadingIndicator() // Remove loading indicator
        }
    }

    private fun showLoadingIndicator() {
        loadingIndicator = JProgressBar().apply {
            isOpaque = false
            isIndeterminate = true // Set to indeterminate mode for loading
            preferredSize = Dimension(Utility.px(50), Utility.px(50)) // Set size for the loading indicator
            // Customize appearance
            background = Color(255, 255, 255, 0) // Transparent background
            foreground = BomberColors.ORANGE
            isBorderPainted = false

            val paddingX = Dimensions.DEFAULT_X_PADDING + Utility.px(30)
            val paddingY = Dimensions.DEFAULT_Y_PADDING + Utility.px(30)

            setBorder(BorderFactory.createEmptyBorder(
                paddingY,
                paddingX,
                paddingY,
                paddingX
            )) // Padding
        }


        // Center the loading indicator in the server list container
        serverListContainer.add(loadingIndicator)
        serverListContainer.revalidate() // Refresh the layout
        serverListContainer.repaint() // Repaint the container
    }

    // Removes the loading indicator
    private fun removeLoadingIndicator() {
        serverListContainer.remove(loadingIndicator)
        serverListContainer.revalidate() // Refresh the layout
        serverListContainer.repaint() // Repaint the container
    }

    // Creates a scrollable panel to list available servers
    private fun createServerBrowserPanel(): JPanel {
        serverListContainer = JPanel().apply {
            preferredSize = Dimension(boxPanelWidth, serversListHeight)
            isOpaque = false
            layout = BoxLayout(this, BoxLayout.Y_AXIS) // Use BoxLayout for vertical stacking
        }
        return serverListContainer
    }

    // Adds custom UI elements to the panel
    override fun addCustomElements() {
        addTitle()
        addServerBrowserPanel()
        addPadding()
        addDirectConnectButton()
        addPadding()
        addIpAddressInputField() // Add the IP address input field
        addPadding()
        createConnectButton() // Add the connect button
        addPadding()
        createBackButton() // Add the back button
    }

    private fun addTitle() {
        boxComponentsPanel.addComponent(
            YellowButton(Localization.get(Localization.SERVERS_BROWSER), Dimensions.FONT_SIZE_MID)
        )
    }

    private fun addServerBrowserPanel() {
        boxComponentsPanel.addComponent(createServerBrowserPanel())
    }

    private fun addDirectConnectButton() {
        boxComponentsPanel.addComponent(
            YellowButton(Localization.get(Localization.CONNECT_DIRECTLY), Dimensions.FONT_SIZE_MID)
        )
    }

    // Returns the width of the box panel
    override fun getBoxPanelWidth(): Int {
        return Utility.px(1000)
    }

    // Adds padding to the UI
    private fun addPadding() {
        padding() // Assuming 'padding()' is defined elsewhere
    }

    // Creates the back button to return to the previous menu
    private fun createBackButton() {
        val backButton: JButton = RedButton(
            Localization.get(Localization.BACK),
            Dimensions.FONT_SIZE_MID
        )
        backButton.addActionListener {
            JBomb.showActivity(MultiplayerPanel::class.java)
        }
        boxComponentsPanel.addComponent(backButton) // Adds button to the UI panel
    }

    // Called when the panel is shown to the user
    override fun onShowCallback() {
        super.onShowCallback()
        fetchAvailableServers() // Fetch servers when the panel is shown
    }
}
