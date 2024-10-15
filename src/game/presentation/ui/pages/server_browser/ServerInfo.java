package game.presentation.ui.pages.server_browser;

// ServerInfo class to hold server details
public class ServerInfo {
    private final String name;
    private final int players;
    private final int ping;

    public ServerInfo(String name, int players, int ping) {
        this.name = name;
        this.players = players;
        this.ping = ping;
    }

    public String getName() {
        return name;
    }

    public int getPlayers() {
        return players;
    }

    public int getPing() {
        return ping;
    }
}

