import game.BomberMan;
import game.engine.GarbageCollectorTask;
import game.level.Level1;
import game.level.Level2;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args){
        new GarbageCollectorTask().start();
        new BomberMan(new Level2());
    }
}
