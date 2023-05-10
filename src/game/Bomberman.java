package game;

import game.data.DataInputOutput;
import game.engine.GarbageCollectorTask;
import game.level.Level;
import game.ui.panels.BombermanFrame;
import game.ui.panels.PagePanel;
import game.ui.panels.game.MatchPanel;

public class Bomberman {
    private static BomberManMatch bomberManMatch;
    private static BombermanFrame bombermanFrame;

    public static void main(String[] args){
        retrievePlayerData();
        startGarbageCollectorTask();
        start();
    }

    public static void start() {
        bombermanFrame = new BombermanFrame();
        bombermanFrame.create();
    }

    public static void startGarbageCollectorTask() {
        new GarbageCollectorTask().start();
    }

    public static void retrievePlayerData() {
        DataInputOutput.retrieveData();
    }

    public static BombermanFrame getBombermanFrame() {
        return bombermanFrame;
    }

    public static BomberManMatch getMatch() {
        return bomberManMatch;
    }

    public static void startLevel(Level level) {
        bomberManMatch = null;
        System.gc();
        bomberManMatch = new BomberManMatch(level);
        bombermanFrame.initGamePanel();
        bomberManMatch.getCurrentLevel().start(bombermanFrame.getPitchPanel());
        Bomberman.getBombermanFrame().addKeyListener(Bomberman.getMatch().getControllerManager());

        show(MatchPanel.class);
    }

    public static void show(Class<? extends PagePanel> page) {
        bombermanFrame.getCardLayout().show(bombermanFrame.getParentPanel(), page.getSimpleName());
    }
}
