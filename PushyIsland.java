Clerk.clear();

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PushyIsland{
    boolean gameRunning;
    int currentLevel;

    public PushyIsland(){
        gameRunning = false;
        currentLevel = 0;
        run();
    }

    void startGame(){
        gameRunning = true;
        currentLevel = 1;
        run();
    }

    void run(){
        switch (currentLevel) {
            case 0:
                clearScreen();
                showTitleScreen();
                break;
            case 1:
                clearScreen();
                showLevel(generatelevel(1));
                break;
            case 2:
                clearScreen();
                showLevel(generatelevel(2));
                break;
            case 3:
                clearScreen();
                showLevel(generatelevel(2));
                break;
            default:
                showWinScreen();
                break;
        }
    }

    void endGame(){
        Clerk.clear();
    }

    void showTitleScreen(){ //Titelbildschirm
        Turtle t = new Turtle(1000,1000);        

        t.moveTo(500,340).color(0,100,100);
        for (int i = 0; i < 360; i++) {
            t.forward(2).right(1);
        }
        t.moveTo(560,410);
        for (int i = 0; i < 360; i++) {
            t.forward(0.5).right(1);
        }
        t.moveTo(470,400);
        for (int i = 0; i < 360; i++) {
            t.forward(0.75).right(1);
        }
        t.moveTo(480,450);
        for (int i = 0; i < 360; i++) {
            t.forward(0.1).right(1);
        }
        t.moveTo(550,440);
        for (int i = 0; i < 360; i++) {
            t.forward(0.1).right(1);
        }

        t.left(90).color(0,200,200);
        t.textSize = 85;
        t.moveTo(500, 100).text("PushyIsland - remake");
        t.textSize = 20;
        t.moveTo(750, 130).color(0,255,200).text("by Leon Sahl");
        t.textSize = 50;
        t.color(150,100,255).moveTo(500, 700);
        t.text("Spiel Starten mit p.startGame").moveTo(500, 800).text("Spiel Verlassen mit p.endGame");
    }

    void clearScreen(){ //Bildschirm löschen
        Clerk.clear();
        Clerk.markdown("PushyIsland - remake");
    }

    level generatelevel(int level){ //Level 1 generieren
        level l = new level(level);
        System.out.println("[PushyIsland] Level " + l.level + " wird geladen." + System.lineSeparator());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return l;
    }
    
    void showLevel(level l){ //Level anzeigen
        Turtle t = new Turtle(1100,700);
        StringBuilder s = new StringBuilder();
        int tx = 50, ty = 50;

        for (int i = 0; i < 14; i++) { // Gitter zeichnen
            t.moveTo(0, ty * i);
            t.forward(1100);
        }
        t.right(90);
        for (int i = 0; i < 24; i++) { // Gitter zeichnen
            t.moveTo(tx * i, 0);
            t.forward(700);
        }
        
        t.left(180).textSize = 50;
        for (int i = 0; i < l.world.length; i++) {
            for (int j = 0; j < l.world[i].length; j++) {
                s.append(l.world[i][j]);
            }
        }

        tx = 50;
        ty = 50;

        for (int i = 0; i < l.world.length; i++) {
            for (int j = 0; j < l.world[i].length; j++) {
                switch (l.world[i][j]) {
                    case 0:
                        t.moveTo(tx * j + 25, ty * i + 37.5).text("o");
                        break;
                    case 1:
                        t.moveTo(tx * j, ty * i).text("G");
                        break;
                    case 2:
                        t.moveTo(tx * j, ty * i).text("S");
                        break;
                    case 3:
                        t.moveTo(tx * j, ty * i).text("P");
                        break;
                    case 4:
                        t.moveTo(tx * j, ty * i).text("H");
                        break;
                    default:
                        t.moveTo(tx * j, ty * i).text("X");
                        break;
                }
            }
        }

        System.out.println("[PushyIsland] Level " + l.level);
    }
    
    String showWinScreen(){ 
        currentLevel++;
        run();
        return "[PushyIsland] Level geschafft.";
    }
}

class level{
    int[][] world;
    int level;

    level(int level){
        this.level = level;
        world = new int[14][22];
        generateWorld(world);
    }

    void generateWorld(int[][] world){
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[i].length ; j++) {
                world[i][j] = 0;
            }
        }
    }

    //public String toString(){
    //    StringBuilder s = new StringBuilder();
    //    for (int i = 0; i < world.length; i++) {
    //        s.append(System.lineSeparator());
    //        for (int j = 0; j < world.length; j++) {
    //            s.append(world[i][j]);
    //        }
    //    }
    //    return "" + s;
    //}
}

PushyIsland p = new PushyIsland();