package game.presentation.ui.pages.server_browser

import game.domain.events.models.RunnablePar
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.JScrollBar
import javax.swing.JScrollPane

class ServerBrowserPanel(servers: List<ServerInfo>, connectRunnable: RunnablePar) : JPanel() {
    private val servers: MutableList<ServerInfo>
    init {
        this.servers = servers.toMutableList() // Initialize the server list
        setLayout(GridLayout(0, 1, padding, padding)) // Dynamic row count with vertical spacing
        setOpaque(false) // Make the panel transparent
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding)) // Padding

        // Add a ServerButton for each server
        for (server in servers) {
            addServerButton(server, connectRunnable) // Use the new method
        }
    }

    private fun addServerButton(server: ServerInfo, connectRunnable: RunnablePar) {
        add(ServerButton(width, server, connectRunnable)) // Create and add the button
    }

    companion object {
        val padding = 10

        fun createScrollableServerBrowser(
            width: Int,
            height: Int,
            servers: List<ServerInfo>,
            connectRunnable: RunnablePar
        ): JScrollPane {
            val scrollPane = getjScrollPane(width, servers, connectRunnable)

            // Keep scroll functionality while hiding the scrollbars
            scrollPane.verticalScrollBar.setUnitIncrement(16) // Set scroll speed

            // Remove the background of the scroll pane and viewport for transparency
            scrollPane.setOpaque(false)
            scrollPane.viewport.setOpaque(false) // Ensure viewport is transparent
            scrollPane.setBorder(null) // Remove the border for a cleaner look
            scrollPane.preferredSize = Dimension(width, height)
            return scrollPane
        }

        private fun getjScrollPane(width: Int, servers: List<ServerInfo>, connectRunnable: RunnablePar): JScrollPane {
            val serverBrowserPanel = ServerBrowserPanel(servers, connectRunnable)

            // Wrap the ServerBrowserPanel in a JScrollPane to make it scrollable
            return object : JScrollPane(serverBrowserPanel) {
                override fun setVerticalScrollBar(verticalScrollBar: JScrollBar) {
                    verticalScrollBar.preferredSize = Dimension(0, 0) // Hide vertical scrollbar
                    super.setVerticalScrollBar(verticalScrollBar)
                }

                override fun setHorizontalScrollBar(horizontalScrollBar: JScrollBar) {
                    horizontalScrollBar.preferredSize = Dimension(0, 0) // Hide horizontal scrollbar
                    super.setHorizontalScrollBar(horizontalScrollBar)
                }
            }
        }
    }
}
