package game.presentation.ui.pages.server_browser;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ServerBrowserPanel extends JPanel {
    private final List<ServerInfo> serverList;

    public ServerBrowserPanel(int width, List<ServerInfo> servers) {
        this.serverList = servers;
        setLayout(new GridLayout(0, 1, 10, 10)); // Dynamic row count with vertical spacing
        setOpaque(false); // Make the panel transparent
        // Add padding around the panel by setting an empty border (acts as padding)
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add a ServerButton for each server
        for (ServerInfo server : servers) {
            add(new ServerButton(width, server));
        }
    }

    public static JScrollPane createScrollableServerBrowser(int width, int height, List<ServerInfo> servers) {
        ServerBrowserPanel serverBrowserPanel = new ServerBrowserPanel(width, servers);

        // Wrap the ServerBrowserPanel in a JScrollPane to make it scrollable
        JScrollPane scrollPane = new JScrollPane(serverBrowserPanel) {
            @Override
            public void setVerticalScrollBar(JScrollBar verticalScrollBar) {
                verticalScrollBar.setPreferredSize(new Dimension(0, 0)); // Hide vertical scrollbar
                super.setVerticalScrollBar(verticalScrollBar);
            }

            @Override
            public void setHorizontalScrollBar(JScrollBar horizontalScrollBar) {
                horizontalScrollBar.setPreferredSize(new Dimension(0, 0)); // Hide horizontal scrollbar
                super.setHorizontalScrollBar(horizontalScrollBar);
            }
        };

        // Keep scroll functionality while hiding the scrollbars
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Set scroll speed

        // Remove the background of the scroll pane and viewport for transparency
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false); // Ensure viewport is transparent
        scrollPane.setBorder(null); // Remove the border for a cleaner look
        scrollPane.setPreferredSize(new Dimension(width, height));

        return scrollPane;
    }
}
