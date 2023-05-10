import game.BomberManMatch;
import game.engine.GarbageCollectorTask;
import game.level.Level2;
import game.level.WorldSelectorLevel;

public class Main {
    public static void main(String[] args){
        new GarbageCollectorTask().start();
        new BomberManMatch(new Level2());
    }
}
