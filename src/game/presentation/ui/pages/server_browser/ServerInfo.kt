package game.presentation.ui.pages.server_browser;

// ServerInfo class to hold server details
public class ServerInfo {
    private final String name;
    private final int players;
    private final int ping;
    private final int port;
    private final String ip;

    public ServerInfo(String name, String ip, int port, int players, int ping) {
        this.name = name;
        this.players = players;
        this.ping = ping;
        this.ip = ip;
        this.port = port;
    }

    public String getFullIp() {
        return ip + ":" + port;
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
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

