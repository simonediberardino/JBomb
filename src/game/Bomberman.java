package game;

import game.data.DataInputOutput;
import game.level.levels.Level;
import game.level.online.OnlineGameHandler;
import game.localization.Localization;
import game.match.BomberManMatch;
import game.sound.AudioManager;
import game.tasks.GarbageCollectorTask;
import game.ui.frames.BombermanFrame;
import game.ui.panels.game.PagePanel;
import game.ui.panels.game.CustomSoundMode;
import game.ui.panels.game.MatchPanel;
import game.ui.pages.LoadingPanel;
import game.ui.pages.MainMenuPanel;
import game.ui.viewelements.misc.ToastHandler;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

import static game.localization.Localization.WELCOME_TEXT;

public class Bomberman {
    private static BomberManMatch bomberManMatch;
    private static BombermanFrame bombermanFrame;

    /**
     * Starts the Java Application;
     */
    public static void main(String[] args) {
        retrievePlayerData();
        startGarbageCollectorTask();
        start();
    }

    private static void start() {
        bombermanFrame = new BombermanFrame();
        bombermanFrame.create();
        showActivity(MainMenuPanel.class);
        ToastHandler.getInstance().show(Localization.get(WELCOME_TEXT).replace("%user%", DataInputOutput.getInstance().getUsername()));
    }

    public static void startGarbageCollectorTask() {
        new GarbageCollectorTask().start();
    }

    public static void retrievePlayerData() {
        DataInputOutput.getInstance().retrieveData();
    }

    public static BombermanFrame getBombermanFrame() {
        return bombermanFrame;
    }

    public static BomberManMatch getMatch() {
        return bomberManMatch;
    }

    public static void quitMatch() {
        destroyLevel();
        showActivity(MainMenuPanel.class);
    }

    public static void destroyLevel() {
        if (bomberManMatch == null) {
            return;
        }
        Bomberman.getBombermanFrame().removeKeyListener(bomberManMatch.getControllerManager());
        bomberManMatch.destroy();
    }

    /**
     * Starts a new level and destroys the previous one;
     *
     */
    private static void doStartLevel(Level level, OnlineGameHandler onlineGameHandler) {
        destroyLevel();
        bomberManMatch = new BomberManMatch(level, onlineGameHandler);
        bombermanFrame.initGamePanel();
        bomberManMatch.getCurrentLevel().start(bombermanFrame.getPitchPanel());

        if (bomberManMatch.getPlayer() != null)
            bomberManMatch.assignPlayerToControllerManager();

        Bomberman.getBombermanFrame().addKeyListener(Bomberman.getMatch().getControllerManager());
        Bomberman.getBombermanFrame().getPitchPanel().addMouseListener(Bomberman.getMatch().getMouseControllerManager());
        Bomberman.getBombermanFrame().getPitchPanel().addMouseMotionListener(Bomberman.getMatch().getMouseControllerManager());
        showActivity(MatchPanel.class);
    }

    public static void startLevel(Level level, OnlineGameHandler onlineGameHandler) {
        bombermanFrame.getLoadingPanel().initialize();
        bombermanFrame.getLoadingPanel().updateText(level);
        bombermanFrame.getLoadingPanel().setCallback(() -> doStartLevel(level, onlineGameHandler));
        showActivity(LoadingPanel.class);
        Bomberman.showActivity(LoadingPanel.class);
    }

    /**
     * Shows a new page and starts its background sound;
     *
     * @param page
     */
    public static void showActivity(Class<? extends PagePanel> page) {
        bombermanFrame.getCardLayout().show(bombermanFrame.getParentPanel(), page.getSimpleName());

        // Gets the component with the passed class and fires its onShowCallback;
        Optional<Component> shownComponentOpt = Arrays.stream(
                bombermanFrame.getParentPanel().getComponents()
        ).filter(c -> c.getClass() == page).findFirst();

        Component shownComponent = shownComponentOpt.orElse(null);
        if (shownComponent instanceof PagePanel)
            ((PagePanel) (shownComponent)).onShowCallback();

        if (!(shownComponent instanceof CustomSoundMode)) {
            AudioManager.getInstance().playBackgroundSong();
        }
    }

    public static boolean isGameEnded() {
        return getMatch() == null || !getMatch().getGameState();
    }
}
