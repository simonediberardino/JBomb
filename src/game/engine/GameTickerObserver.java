package game.engine;

import java.util.Observable;
import java.util.Observer;

public abstract class GameTickerObserver implements Observer {
    private static float DEFAULT_DELAY_OBSERVER = 40;
    protected long lastUpdate = 0L;

    @Override
    public void update(Observable o, Object arg) {
        lastUpdate = System.currentTimeMillis();
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    protected float getDelayObserverUpdate(){
        return DEFAULT_DELAY_OBSERVER;
    }
}
