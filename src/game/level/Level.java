package game.level;

import game.BomberMan;
import game.entity.*;
import game.models.Coordinates;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static game.ui.GameFrame.GRID_SIZE;


public abstract class Level {
    public abstract String getStone();
    public abstract String getGrass();
    public abstract String getDestroyable();

    public Image getStoneBlock(){
        try {
            return ImageIO.read(new File(getStone()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Image getDestroyableBlock(){
        try {
            return ImageIO.read(new File(getDestroyable()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Image getGrassBlock(){
        try {
            return ImageIO.read(new File(getGrass()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public abstract Icon getPitch();

    public void generateGrass(JPanel jPanel) {/*
        for (int i = 0; i < positions.length / GRID_SIZE; i++)
            for (int j = 0; j < positions[i].length / GRID_SIZE; j++)
                new Grass(new Coordinates(i * GRID_SIZE, j * GRID_SIZE)).spawn();*/
    }

    public abstract void generateStone(JPanel jPanel);


    public void start(JPanel jPanel){
        generateGrass(jPanel);
        generateStone(jPanel);
        new Player().spawn();
    }
}
