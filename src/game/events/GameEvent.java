package game.events;

/**
 * Fires a game event using Strategy Pattern;
 */
public interface GameEvent {
    void invoke(Object arg);
}
