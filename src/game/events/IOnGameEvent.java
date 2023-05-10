package game.events;

public interface IOnGameEvent {
    void onEvent(GameEvent gameEvent, Object ... arg);
}
