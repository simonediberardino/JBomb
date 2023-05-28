package game.entity.enemies.boss.ghost;

import game.Bomberman;
import game.entity.enemies.GhostEnemy;
import game.entity.enemies.boss.Boss;
import game.entity.models.Coordinates;
import game.entity.models.Direction;
import game.entity.models.Entity;
import game.events.RunnablePar;
import game.ui.panels.game.PitchPanel;
import game.utils.GradientCallbackHandler;
import game.utils.Paths;
import game.utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GhostBoss extends Boss {
    private static final String SKIN_PATH_TEMPLATE = "%s/ghost_boss/ghost_with_axe_%s_%s.png";
    private static final int ACTION_CHANCE = 1;
    private static final int INVISIBLE_DURATION = 3500;
    private static final int ATTACK_RESET_DELAY = 1000;
    private static final int INVISIBLE_DELAY = 10000;
    private static final int MAX_GHOST_ENEMY_SPAWNED = 5;
    private static final int GHOST_SPAWN_TIME_DELAY = 10000;
    private static final int LIGHTS_EVENT_DELAY = 20000;
    private static long lastLightsEvent = System.currentTimeMillis();
    private static final int MAX_GHOSTS_ALIVE = 10;
    private final static int BOSS_ATTACK_VERTICAL_RANGE = 2;
    private final static int BOSS_ATTACK_HORIZONTAL_RANGE = 1;
    private boolean isInvisibleTaskRunning = false;
    private long lastInvisibleTime = 0;
    private long lastGhostSpawnTime;

    public GhostBoss() {
        super();
        this.hitboxSizetoWidthRatio= 0.648f;
        this.hitboxSizeToHeightRatio = 0.70f;
        paddingTopFunction = new RunnablePar() {
            @Override
            public <T> Object execute(T par) {
                setPaddingTop(0);
                return 0;
            }
        };
    }

    @Override
    public void attack(Entity e){
        attackAnimation();
        super.attack(e);
    }
    @Override
    protected Map<Integer, Integer> healthStatusMap() {
        return new HashMap<>();
    }

    @Override
    protected String getImageFromRageStatus() {
        return String.format(SKIN_PATH_TEMPLATE, Paths.getEnemiesFolder(), imageDirection.toString().toLowerCase(), currRageStatus);
    }

    @Override
    public String[] getCharacterOrientedImages() {
        return new String[] { getImageFromRageStatus() };
    }

    @Override
    protected java.util.List<Direction> getImageDirections() {
        return Arrays.asList(Direction.RIGHT, Direction.LEFT);
    }

    private void attackAnimation() {
        if(currRageStatus == 1) return;

        updateRageStatus(1);
        Timer t = new Timer(ATTACK_RESET_DELAY, (l) -> updateRageStatus(0));
        t.setRepeats(false);
        t.start();
    }

    /**
     * This method makes the Ghost disappear and then reappear by changing its alpha value gradually.
     */
    private synchronized void disappearAndReappear() {
        if (isInvisibleTaskRunning) {
            // If an invisible task is already running, exit the method.
            return;
        }

        // Check if enough time has passed since the last invisibility to start the task.
        if (Utility.timePassed(lastInvisibleTime) < INVISIBLE_DELAY) {
            return;
        }

        // Record the current time as the last invisibility time.
        lastInvisibleTime = System.currentTimeMillis();
        // Set the flag to indicate that an invisible task is running.
        isInvisibleTaskRunning = true;

        // Define the start and end alpha values, and the step for alpha increment/decrement.
        float startAlpha = 1f, endAlpha = 0f, step = 0.01f;

        // Create a callback handler for the task of gradually showing the object.
        GradientCallbackHandler showTask = new GradientCallbackHandler(new RunnablePar() {
            @Override
            public <T> Object execute(T par) {
                // Set the alpha value of the object to the given parameter value.
                setAlpha((Float) par);

                if ((Float) par >= startAlpha) {
                    // If the alpha value has reached or exceeded the start alpha, mark the invisible task as finished.
                    isInvisibleTaskRunning = false;
                }
                return null;
            }
        }, endAlpha, startAlpha, -step);

        // Create a callback handler for the task of gradually hiding the object.
        GradientCallbackHandler hideTask = new GradientCallbackHandler(new RunnablePar() {
            @Override
            public <T> Object execute(T par) {
                // Set the alpha value of the object to the given parameter value.
                setAlpha((Float) par);

                if (!((Float) par <= endAlpha)) {
                    // If the alpha value is not yet less than or equal to the end alpha, exit the method.
                    return null;
                }

                // Create a timer to delay the execution of the show task after the object is hidden.
                Timer t = new Timer(INVISIBLE_DURATION, (l) -> showTask.execute());
                t.setRepeats(false);
                t.start();

                return null;
            }
        }, startAlpha, endAlpha, step);

        // Start the task of gradually hiding the object.
        hideTask.execute();
    }

    public static void turnOffLights() {

        PitchPanel pitchPanel = Bomberman.getBombermanFrame().getPitchPanel();

        pitchPanel.addGraphicsCallback(
                GhostBoss.class.getSimpleName(), new RunnablePar() {
                    @Override
                    public <T> Object execute(T par) {
                        Graphics2D g2d = Bomberman.getBombermanFrame().getPitchPanel().g2d;
                        g2d.setColor(new Color(0,0,0,0.9f));
                        g2d.fillRect(0,0,Bomberman.getBombermanFrame().getHeight(),Bomberman.getBombermanFrame().getWidth());
                        return null;
                    }
                }
        );
    }

    public static void turnOnLights() {
        PitchPanel pitchPanel = Bomberman.getBombermanFrame().getPitchPanel();
        pitchPanel.removeGraphicsCallback(GhostBoss.class.getSimpleName());
    }

    private void spawnGhosts(int n) {
        if(Utility.timePassed(lastGhostSpawnTime) < GHOST_SPAWN_TIME_DELAY) return;

        lastGhostSpawnTime = System.currentTimeMillis();

        for(int i = 0; i < n; i++){
            if(Bomberman.getMatch().getEnemiesAlive() >= MAX_GHOSTS_ALIVE) return;
            new GhostEnemy().spawnAtRandomCoordinates();
        }
    }
    public synchronized static void performLightsAnimation(){
        new Thread(() -> {
            try {
                turnOffLights();
                Thread.sleep(10000);
                turnOnLights();
                Thread.sleep(100);
                turnOffLights();
                Thread.sleep(400);
                turnOnLights();
                Thread.sleep(100);
                turnOffLights();
                Thread.sleep(400);
                turnOnLights();
                Thread.sleep(100);
                turnOffLights();
                Thread.sleep(400);
                turnOnLights();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    private void attack() {

        ArrayList<Coordinates> coordsOfUnderneathEntityBlocks = Coordinates.getAllBlocksInAreaFromDirection(this,Direction.DOWN,BOSS_ATTACK_VERTICAL_RANGE);
        System.out.println(coordsOfUnderneathEntityBlocks);
        ArrayList<Coordinates>  coordsOfEntitysImageDirectionBlocks = Coordinates.getAllBlocksInAreaFromDirection(this,imageDirection, BOSS_ATTACK_HORIZONTAL_RANGE);
        coordsOfUnderneathEntityBlocks.addAll(coordsOfEntitysImageDirectionBlocks);

        //merge the 2 lists into one another
        coordsOfUnderneathEntityBlocks.stream().forEach(c->Coordinates.getEntitiesOnBlock(c).stream().forEach(e->interact(e)));
        attackAnimation();
    }

    @Override
    public void doUpdate(boolean gamestate) {
        Utility.runPercentage(ACTION_CHANCE, this::attack);
        Utility.runPercentage(ACTION_CHANCE, this::disappearAndReappear);
        Utility.runPercentage(ACTION_CHANCE, new Runnable() {
            @Override
            public void run() {
                if(Utility.timePassed(lastLightsEvent)>LIGHTS_EVENT_DELAY){
                    performLightsAnimation();
                    lastLightsEvent = System.currentTimeMillis();

                }
            }
        });
        Utility.runPercentage(ACTION_CHANCE, () -> {
            int enemyCount = (int) (Math.random() * MAX_GHOST_ENEMY_SPAWNED);     //spawn ghost enemies
            spawnGhosts(enemyCount);
        });

        super.doUpdate(gamestate);
    }
}
