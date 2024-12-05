Clerk.clear();

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;


public class PushyIsland{
    boolean gameRunning;
    int currentLevel;
    Turtle t = new Turtle(1100,700);
    
    
    public PushyIsland(){
        gameRunning = false;
        currentLevel = 0;
        run();
    }
    
    void run(){ // Runtime
        if (gameRunning) {
            drawLevel(generatelevel(currentLevel));
        } else showTitleScreen();
    }
    
    void startGame(){ // Spiel starten
        gameRunning = true;
        currentLevel = 1;
        run();
    }
    
    void endGame(){ // Spiel 'beenden'
    System.out.println("[PushyIsland] Spiel beendet." + System.lineSeparator());
    Clerk.clear();
    }

    void showTitleScreen(){ // Titelbildschirm
        t.moveTo(550,190).color(0,100,100);
        for (int i = 0; i < 360; i++) {
            t.forward(2).right(1);
        }
        t.moveTo(600,300);
        for (int i = 0; i < 360; i++) {
            t.forward(0.5).right(1);
        }
        t.moveTo(510,290);
        for (int i = 0; i < 360; i++) {
            t.forward(0.75).right(1);
        }
        t.moveTo(525,340);
        for (int i = 0; i < 360; i++) {
            t.forward(0.1).right(1);
        }
        t.moveTo(600,330);
        for (int i = 0; i < 360; i++) {
            t.forward(0.1).right(1);
        }

        t.left(90).color(0,200,200);
        t.textSize = 85;
        t.moveTo(550, 100).text("PushyIsland - remake");
        t.textSize = 20;
        t.moveTo(750, 130).color(0,255,200).text("by Leon Sahl");
        t.textSize = 50;
        t.color(150,100,255).moveTo(550, 500);
        t.text("Spiel Starten mit p.startGame").moveTo(550, 600).text("Spiel Verlassen mit p.endGame");
    }

    Level generatelevel(int currentLevel){ // Einzelne Level generieren
        Level l = new Level(currentLevel);
        System.out.println("[PushyIsland] Level " + l.level + " wird geladen." + System.lineSeparator());
        switch (currentLevel) {
            case 1:
            l.loadWorld(l.level1); // Level 1 laden
            break;
            case 2:
            l.loadWorld(l.level2); // Level 2 laden
            break;
            case 3:
            l.loadWorld(l.level3); // Level 3 laden 
            break;
            case 4:
            l.loadWorld(l.level4); // Level 4 laden
            break;
            case 5:
            l.loadWorld(l.level5); // Level 5 laden
            break;
            case 6:
            l.loadWorld(l.level5); // Level 6 laden
            break;
            default: // Wenn kein Level mehr gefunden wird, wird das Spiel beendet
            currentLevel = 0;
            clearScreen();
            break;
        }
        try {
            Thread.sleep(500); //"Lade" Zeit
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return l;
    }

    void clearScreen(){ // Bildschirm löschen
        Clerk.clear();
        Clerk.markdown("PushyIsland - remake");
    }

    void drawLevel(Level l) { // Level anzeigen
        int tx = 50, ty = 50;
        t.reset().left(90).textSize = 50;
        for (int i = 0; i < l.world.length; i++) {
            for (int j = 0; j < l.world[i].length; j++) {
                int x = tx * j + 25, y = ty * i + 42;
                String emoji = switch (l.world[i][j]) {
                    case 0 -> "🟦"; // Wasser
                    case 1 -> "🟨"; // Boden
                    case 2 -> "🗻"; // Berg
                    case 3 -> switch (new Random().nextInt(3)) {
                        case 0 -> "🌳"; // Baum
                        case 1 -> "🌴"; // Palme
                        default -> "🍄"; // Pilz
                    };
                    case 4 -> "📦"; // Box
                    case 5 -> "🟩"; // Erhöhung
                    case 6 -> "🐚"; // Muschel
                    case 7 -> "😋"; // Spieler
                    case 8 -> { y += 1; yield "🏠"; } // Haus
                    default -> "❌"; // Fehler
                };
                t.color(0, 0, 205).moveTo(x, y).text(emoji);
            }
        }
    }

        void levelPassed(){
            System.out.println("[PushyIsland] Level geschafft." + System.lineSeparator());
            currentLevel++;
            run();
        }

        //String findPlayer(){ // Neuer Ansatz!
        //    for (int i = 0; i < l.world.length; i++) {
        //        for (int j = 0; j < l.world.length; j++) {
        //            if (l.world[i][j] == 5) return i + " , " + j;
        //        }
        //    }
        //    return "Player not found.";
        //}

        boolean rules(){



            return false;
        }
        
        Move move(Move m){
            if (gameRunning) {
                switch (m) {
                    case UP:
                    
                    break;
                    case DOWN:
                    
                    break;
                    case LEFT:
                    
                    break;
                    case RIGHT:
                    
                    break;
                default:
                break;
            }
        }
        return m;
    }
}

enum Move{
    UP,
    DOWN,
    LEFT,
    RIGHT;
}

class Level{
    int[][] world;
    int level;

    Level(int level){
        this.level = level;
        world = new int[14][22]; // Leeres Level erstellen
    }

    int[][] loadWorld(int[][] world){
        this.world = world;
        return world; // Fertige Level laden
    }

    int[][] level1 = {
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 5, 5, 1, 1, 8, 1, 1, 0 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 5, 5, 1, 1, 1, 1, 1, 0 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 5, 1, 1, 0 }, 
        { 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 3, 2, 3, 5, 1, 1, 0 }, 
        { 0, 0, 0, 1, 1, 7, 1, 5, 5, 5, 5, 5, 3, 3, 5, 5, 3, 5, 5, 1, 1, 0 }, 
        { 0, 0, 0, 1, 1, 1, 1, 5, 3, 2, 2, 3, 5, 3, 5, 5, 5, 5, 1, 1, 0, 0 }, 
        { 0, 0, 0, 0, 1, 1, 1, 1, 5, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 0, 0, 0 }, 
        { 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, 
        { 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }};
        
    int[][] level2 = {
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 3, 1, 1, 0, 0, 0 },
        { 0, 0, 0, 0, 3, 8, 3, 0, 0, 0, 0, 0, 0, 0, 1, 3, 1, 3, 1, 0, 0, 0 },
        { 0, 0, 0, 3, 1, 1, 1, 3, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0 },
        { 0, 0, 3, 1, 5, 5, 1, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 5, 2, 5, 1, 1, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 2, 5, 2, 1, 1, 1, 4, 1, 2, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 5, 5, 5, 1, 1, 4, 1, 1, 1, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 1, 1, 1, 1, 4, 1, 1, 1, 7, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 1, 3, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }};

    int[][] level3 = {
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 1, 2, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 1, 2, 1, 1, 1, 5, 5, 5, 5, 5, 5, 8, 1, 0, 0, 0, 0 },
        { 0, 0, 0, 1, 2, 1, 1, 3, 1, 5, 5, 5, 3, 2, 2, 5, 1, 1, 0, 0, 0, 0 },
        { 0, 0, 0, 3, 1, 3, 3, 1, 5, 5, 3, 3, 5, 5, 3, 5, 1, 1, 0, 0, 0, 0 },
        { 0, 0, 0, 2, 3, 1, 1, 1, 5, 3, 5, 2, 5, 5, 5, 1, 6, 1, 0, 0, 0, 0 },
        { 0, 0, 0, 3, 1, 1, 1, 1, 5, 5, 5, 5, 5, 5, 1, 1, 1, 1, 0, 0, 0, 0 },
        { 0, 0, 0, 1, 7, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 1, 3, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }};

    int[][] level4 = {
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 3, 2, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 1, 7, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 5, 2, 5, 3, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 5, 5, 5, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 3, 3, 0, 0 },
        { 0, 0, 1, 1, 1, 1, 1, 4, 1, 1, 1, 0, 0, 0, 1, 1, 3, 3, 8, 3, 0, 0 },
        { 0, 0, 3, 2, 1, 1, 1, 1, 1, 4, 1, 0, 0, 0, 1, 1, 1, 1, 1, 3, 0, 0 },
        { 0, 0, 0, 0, 0, 1, 1, 1, 4, 1, 1, 0, 0, 0, 1, 1, 3, 1, 3, 3, 0, 0 },
        { 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }};

    int[][] level5 = {
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 3, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 7, 3, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 1, 0, 0, 1, 4, 1, 1, 3, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 8, 1, 0, 0, 4, 1, 1, 1, 3, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 4, 1, 1, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 3, 2, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }};
    
    int[][] level6 = {
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }};
}

PushyIsland p = new PushyIsland();

//p.drawLevel(p.generatelevel(1))
//p.drawLevel(p.generatelevel(2))
//p.drawLevel(p.generatelevel(3))
//p.drawLevel(p.generatelevel(4))
//p.drawLevel(p.generatelevel(5))

//Spieler Moves merken (HighScore (Anzahl Moves)) einbauen