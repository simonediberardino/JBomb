package game;

import game.BomberManMatch;
import game.engine.GarbageCollectorTask;
import game.level.Level;
import game.level.WorldSelectorLevel;
import game.level.world1.World1Level1;
import game.level.world2.World2Level1;
import game.level.world2.World2Level5;
import game.ui.panels.BombermanFrame;
import game.ui.panels.PagePanel;
import game.ui.panels.game.MatchPanel;

public class Bomberman {
    private static BomberManMatch bomberManMatch;
    private static BombermanFrame bombermanFrame = null;

    public static void main(String[] args){
        new GarbageCollectorTask().start();
        start();
    }

    public static void start() {
        bombermanFrame = new BombermanFrame();
        bombermanFrame.create();
    }

    public static BombermanFrame getBombermanFrame() {
        return bombermanFrame;
    }

    public static void startLevel(Level level) {
        bomberManMatch = null;
        System.gc();
        bomberManMatch = new BomberManMatch(level);
        bomberManMatch.start();
        bombermanFrame.initGamePanel();
        bomberManMatch.getCurrentLevel().start(bombermanFrame.getPitchPanel());
        show(MatchPanel.class);
    }

    public static void show(Class<? extends PagePanel> page) {
        bombermanFrame.getCardLayout().show(bombermanFrame.getParentPanel(), page.getSimpleName());
    }
}
