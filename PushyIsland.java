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
                drawLevel(generatelevel(1));
                break;
            case 2:
                clearScreen();
                drawLevel(generatelevel(2));
                break;
            case 3:
                clearScreen();
                drawLevel(generatelevel(3));
                break;
            default:
                endGame();
                break;
        }
    }

    void endGame(){ // Spiel 'beenden'
        System.out.println("[PushyIsland] Spiel beendet." + System.lineSeparator());
        Clerk.clear();
    }

    void showTitleScreen(){ // Titelbildschirm
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

    void clearScreen(){ // Bildschirm löschen
        Clerk.clear();
        Clerk.markdown("PushyIsland - remake");
    }

    level generatelevel(int currentLevel){ // Einzelne Level generieren
        level l = new level(currentLevel);
        System.out.println("[PushyIsland] Level " + l.level + " wird geladen." + System.lineSeparator());
        switch (currentLevel) {
            case 1:
                l.loadWorld(l.level1);
                break;
            case 2:
                l.loadWorld(l.level2);
                break;
            case 3:
                l.loadWorld(l.level3);
                //levelthree();
                break;
            default:
                currentLevel = 0;
                clearScreen();
                break;
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return l;
    }
    
    String drawLevel(level l){ //Level anzeigen
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
        t.left(180).textSize = 45;
        tx = 50;
        ty = 50;
        for (int i = 0; i < l.world.length; i++) {
            for (int j = 0; j < l.world[i].length; j++) {
                switch (l.world[i][j]) {
                    case 0:
                        t.color(0,0,205).moveTo(tx * j + 25, ty * i + 40).text("🟦");  // oder: ♒︎
                        break;
                    case 1:
                        t.color(0,0,205).moveTo(tx * j + 25, ty * i + 40).text("🟨");
                        break;
                    case 2:
                        t.color(0,0,205).moveTo(tx * j + 25, ty * i + 37.5).text("🗻");
                        break;
                    case 3:
                        t.color(0,0,205).moveTo(tx * j + 25, ty * i + 37.5).text("🌴");
                        break;
                    case 4:
                        t.color(0,0,205).moveTo(tx * j + 25, ty * i + 37.5).text("📦");
                        break;
                    case 5:
                        t.color(0,0,205).moveTo(tx * j + 25, ty * i + 37.5).text("😋");
                        break;
                    case 6:
                        t.color(0,0,205).moveTo(tx * j + 25, ty * i + 37.5).text("🏠");
                        break;
                    default:
                        t.moveTo(tx * j + 25, ty * i + 37.5).text("❌");
                        break;
                }
            }
        }
        return "[PushyIsland] Level " + l.level;
    }

    void showWinScreen(){ 
        System.out.println("[PushyIsland] Level geschafft." + System.lineSeparator());
        currentLevel++;
        run();
    }
}

class level{
    int[][] world;
    int level;

    level(int level){
        this.level = level;
        world = new int[14][22];
    }

    int[][] loadWorld(int[][] world){
        this.world = world;
        return world;
    }

    int[][] level1 = {
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 6, 1, 1, 0 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 }, 
        { 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 }, 
        { 0, 0, 0, 0, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 2, 3, 1, 1, 1, 0 }, 
        { 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 1, 1, 3, 1, 1, 1, 1, 0 }, 
        { 0, 0, 0, 1, 1, 1, 1, 1, 3, 2, 2, 3, 1, 3, 1, 1, 1, 1, 1, 1, 0, 0 }, 
        { 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0 }, 
        { 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, 
        { 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }};
        
    int[][] level2 = {
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 3, 1, 1, 0, 0, 0 },
        { 0, 0, 0, 0, 3, 6, 3, 0, 0, 0, 0, 0, 0, 0, 1, 3, 1, 3, 1, 0, 0, 0 },
        { 0, 0, 0, 3, 1, 1, 1, 3, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0 },
        { 0, 0, 3, 1, 1, 1, 1, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 1, 2, 1, 1, 1, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 2, 1, 2, 1, 1, 1, 4, 1, 2, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 1, 1, 1, 1, 4, 1, 1, 1, 5, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 1, 3, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }};

    int[][] level3 = {
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 1, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 5, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }};
}

PushyIsland p = new PushyIsland();