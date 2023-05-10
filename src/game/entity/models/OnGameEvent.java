package game.entity.models;

import game.events.GameEvent;

public interface OnGameEvent {
    void onGameEvent(GameEvent gameEvent, Object arg);
}
