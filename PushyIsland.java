import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class PushyIsland{
    boolean gameRunning;
    int currentLevel, moveCount;
    int[] moveStatics = new int[6];
    Turtle t = new Turtle(1100,700);
    Level l = new Level();
    String player_dir = "";
    
    public PushyIsland(){
        gameRunning = false;
        currentLevel = 0;
        run();
    }
    
    void run() { // Runtime
        if (gameRunning) {
            l = loadLevel(currentLevel);
            if (l != null) {
                drawLevel(l);
            }
        } else {
            showTitleScreen();
        }
    }
    
    void startGame(){ // Spiel starten
        if (!gameRunning) {
            gameRunning = true;
            currentLevel = 1;
            run();    
        } else System.out.println("[PushyIsland] Das Spiel ist bereits im gange. \n");
    }
    
    void endGame(){ // Spiel 'beenden'
    gameRunning = false;
    currentLevel = 0;
    System.out.println("[PushyIsland] Spiel beendet. \n");
    Clerk.clear();
    }

    void showTitleScreen(){ // Titelbildschirm
        t.moveTo(550,190).color(33, 53, 85);
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

        t.left(90).color(62, 88, 121);
        t.textSize = 85;
        t.moveTo(550, 100).text("PushyIsland - remake");
        t.textSize = 30;
        t.moveTo(770, 130).color(33, 53, 85).text("by Leon Sahl");
        t.textSize = 35;
        t.color(62, 88, 121).moveTo(550, 540);
        t.text("Starten mit SPACEBAR oder p.startGame()").moveTo(550, 590).text("Steuern mit WASD oder p.move(Move.UP/DOWN/LEFT/RIGHT)").moveTo(550, 640).text("Du kannst ein Level neustarten mit R oder p.resetLevel()").moveTo(550, 690).text("Verlassen mit ESC oder p.endGame");
    }

    Level loadLevel(int currentLevel) { // Einzelne Level generieren
        System.out.println("[PushyIsland] Level " + currentLevel + " wird geladen. \n");
        Level newLevel = new Level();
        switch (currentLevel) {
            case 1:
                newLevel.loadWorld(newLevel.level1); // Level 1 laden
                break;
            case 2:
                newLevel.loadWorld(newLevel.level2); // Level 2 laden
                break;
            case 3:
                newLevel.loadWorld(newLevel.level3); // Level 3 laden 
                break;
            case 4:
                newLevel.loadWorld(newLevel.level4); // Level 4 laden
                break;
            case 5:
                newLevel.loadWorld(newLevel.level5); // Level 5 laden
                break;
            case 6:
                newLevel.loadWorld(newLevel.level6); // Level 6 laden
                break;
            case 7:
                newLevel.loadWorld(newLevel.level7); // Level 6 laden
                break;
            case 8:
                newLevel.loadWorld(newLevel.level8); // Level 6 laden
                break;
            case 9:
                newLevel.loadWorld(newLevel.level9); // Level 6 laden
                break;
            case 10:
                newLevel.loadWorld(newLevel.level10); // Level 6 laden
                break;
            default: // Wenn kein Level mehr gefunden wird, wird das Spiel beendet
                System.out.println("[PushyIsland] Es gibt keine weiteren Level, <3-lichen Glueckwunsch.");
                IntStream.of(moveStatics).forEach(System.out::println);
                endGame();
                return null;
        }
        try {
            Thread.sleep(500); // "Lade" Zeit
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return newLevel;
    }

    void drawLevel(Level l) { // Level anzeigen
        int tx = 50, ty = 50;
        t.reset().left(90).textSize = 50;
        IntStream.range(0, l.world.length).forEach(i -> {
            IntStream.range(0, l.world[i].length).forEach(j -> {
            int x = tx * j + 25, y = ty * i + 42;
            String emoji = switch (l.world[i][j]) {
                case 0 -> "🟦";                   // Wasser
                case 1 -> "🟨";                   // Boden
                case 2 -> "🗻";                   // Berg
                case 3 -> "🌴";                   // Palme
                case 4 -> "📦";                   // Box
                case 5 -> "🟩";                   // Erhöhung
                case 6 -> "🐚";                   // Muschel
                case 7 -> "😋";                   // Spieler
                case 8 -> { y += 1; yield "🏠"; } // Haus
                default -> "❌";                  // Fehler
            };
            t.color(0, 0, 205).moveTo(x, y).text(emoji);
            });
        });
        t.textSize = 30;
        t.moveTo(70, 40).color(0, 0, 0).text("Level: " + currentLevel);
        t.moveTo(80, 80).text("Moves: " + moveCount);
    }
    
    void clearScreen(){ // Bildschirm leeren
        Clerk.clear();
    }

    void resetLevel() { // Methode um das derzeitiges Level zurücksetzen
        if (gameRunning) {
            System.out.println("[PushyIsland] Kleinen Moment, das Level wird zurückgesetzt. \n");
            moveCount = 0;                         // Move Counter zurücksetzen
            l = loadLevel(currentLevel);           // Aktualisieren der vorhandenen Level-Instanz
            drawLevel(l);                          // Level anzeigen
        } else System.out.println("[PushyIsland] Das Spiel wurde noch nicht gestartet. \n");
    }

    void levelPassed(){
        if (gameRunning) {
            System.out.println("[PushyIsland] Level wurde mit " + moveCount +  " Moves geschafft. \n");
            moveStatics[currentLevel - 1] = moveCount; // Move Counter speichern
            moveCount = 0;                             // Move Counter zurücksetzen
            currentLevel++;                            // Derzeitiges Level erhöhen
            run();
        } else System.out.println("[PushyIsland] Das Spiel wurde noch nicht gestartet. \n");
    }

    public String findPlayer(Level l) { // Methode um den Spieler auf dem Spielfeld zu finden
        for (int i = 0; i < l.world.length; i++) {
            for (int j = 0; j < l.world[i].length; j++) {
                if (l.world[i][j] == 7) return i + "," + j;
            }
        }
        return "Player not found.";
    }

    public int countShell(){ // Methode um die Anzahl der Muscheln zu zählen
        int shellCount = 0;
        for (int i = 0; i < l.world.length; i++) {
            for (int j = 0; j < l.world[i].length; j++) {
                if (l.world[i][j] == 6) shellCount++;
            }
        }
        return shellCount;
    }

    public int possitionObjectFinder(Level l, String pos){ // Methode um das Objekt an einer bestimmten Position zu finden
        int devider = pos.lastIndexOf(",");
        int y = Integer.parseInt(pos.substring(0, devider));
        int x = Integer.parseInt(pos.substring(devider + 1, pos.length()));
        return l.world[y][x];
    }

    boolean rules() { // Regeln des Spiels
        String player_pos = findPlayer(l);
        int devider = player_pos.lastIndexOf(",");
        int player_posY = Integer.parseInt(player_pos.substring(0, devider));
        int player_posX = Integer.parseInt(player_pos.substring(devider + 1, player_pos.length()));
    
        int[] objectPos = getObjectPosition(player_posY, player_posX);
        int object_posY = objectPos[0];
        int object_posX = objectPos[1];
        int object = objectPos[2];
    
        switch (object) {
            case 0:
                System.out.println("[PushyIsland] Spieler kann nicht ins Wasser.");
                return false;
            case 1:
                movePlayer(player_posY, player_posX, object_posY, object_posX);
                break;
            case 4:
                if (!moveBox(player_posY, player_posX, object_posY, object_posX)) return false;
                break;
            case 6:
                l.world[object_posY][object_posX] = 1;
                break;
            case 8:
            if (countShell() == 0) {
                moveCount++;
                levelPassed();
            } else System.out.println("[PushyIsland] Es wurden noch nicht alle Muscheln eingesammelt.");
                break;
            default:
                break;
        }
        drawLevel(l); // Verändertes Spielfeld neuladen
        return true;
    }
    
    int[] getObjectPosition(int player_posY, int player_posX) { // Position des Objekts finden
        int object_posY = 0, object_posX = 0, object = 0;
    
        switch (player_dir) {
            case "up":
                object = possitionObjectFinder(l, ((player_posY - 1) + "," + player_posX));
                object_posY = player_posY - 1;
                object_posX = player_posX;
                break;
            case "down":
                object = possitionObjectFinder(l, ((player_posY + 1) + "," + player_posX));
                object_posY = player_posY + 1;
                object_posX = player_posX;
                break;
            case "left":
                object = possitionObjectFinder(l, (player_posY + "," + (player_posX - 1)));
                object_posY = player_posY;
                object_posX = player_posX - 1;
                break;
            case "right":
                object = possitionObjectFinder(l, (player_posY + "," + (player_posX + 1)));
                object_posY = player_posY;
                object_posX = player_posX + 1;
                break;
            default:
                break;
        }
        return new int[]{object_posY, object_posX, object}; // Rückgabe der Position des Objekts
    }
    
    void movePlayer(int player_posY, int player_posX, int object_posY, int object_posX) { // Spieler bewegen
        l.world[player_posY][player_posX] = 1;
        l.world[object_posY][object_posX] = 7;
        moveCount++;
    }
    
    boolean moveBox(int player_posY, int player_posX, int object_posY, int object_posX) { // Box bewegen
        int newBoxPosY = object_posY, newBoxPosX = object_posX;
    
        switch (player_dir) {
            case "up":
                newBoxPosY = object_posY - 1;
                break;
            case "down":
                newBoxPosY = object_posY + 1;
                break;
            case "left":
                newBoxPosX = object_posX - 1;
                break;
            case "right":
                newBoxPosX = object_posX + 1;
                break;
        }
        if (l.world[newBoxPosY][newBoxPosX] == 1) { // Box kann weiter bewegt werden
            l.world[player_posY][player_posX] = 1;
            l.world[object_posY][object_posX] = 7;
            l.world[newBoxPosY][newBoxPosX] = 4;
        } else if (l.world[newBoxPosY][newBoxPosX] == 0) { // Box kann ins Wasser geschoben werden
            l.world[player_posY][player_posX] = 1;
            l.world[object_posY][object_posX] = 7;
            l.world[newBoxPosY][newBoxPosX] = 1; // Box wird zu Boden
        } else {
            System.out.println("[PushyIsland] Box kann nicht weiter geschoben werden.");
            return false;
        }
        moveCount++;
        return true;
    }
        
    public void move(Move m) {
        if (gameRunning) {
            switch (m) {
                case UP:
                    player_dir = "up";    // Bewegung nach oben
                    break;
                case DOWN:
                    player_dir = "down";  // Bewegung nach unten
                    break;
                case LEFT:
                    player_dir = "left";  // Bewegung nach links
                    break;
                case RIGHT:
                    player_dir = "right"; // Bewegung nach rechts
                    break;
                default:
                    break;
            }
            rules();
        }
    }
}

enum Move {
    UP,
    DOWN,
    LEFT,
    RIGHT;
}

class Level{
    int[][] world;

    Level(){
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
        { 0, 0, 0, 1, 2, 3, 3, 3, 1, 5, 5, 5, 3, 2, 2, 5, 1, 1, 0, 0, 0, 0 },
        { 0, 0, 0, 3, 1, 1, 1, 1, 5, 5, 3, 3, 5, 5, 3, 5, 1, 1, 0, 0, 0, 0 },
        { 0, 0, 0, 2, 1, 3, 1, 1, 5, 3, 5, 2, 5, 5, 5, 1, 6, 1, 0, 0, 0, 0 },
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
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 6, 0, 0, 1, 4, 1, 1, 3, 0, 0, 0 },
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
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 1, 1, 8, 3, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 3, 2, 0, 0, 0, 0 },
        { 0, 0, 0, 1, 1, 1, 4, 2, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 1, 7, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 2, 1, 3, 1, 0, 0, 1, 1, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 4, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }};

    int[][] level7 = {
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 2, 5, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 8, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 1, 6, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
        { 0, 0, 3, 4, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 4, 1, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 1, 1, 3, 0, 0, 0, 0, 0, 1, 4, 1, 1, 1, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 1, 4, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 2, 1, 7, 4, 1, 0, 0, 0, 1, 6, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 3, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }};

    int[][] level8 = {
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

    int[][] level9 = {
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

    int[][] level10 = {
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

//Spieler Moves merken (HighScore (Anzahl Moves)) einbauen