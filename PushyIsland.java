import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class PushyIsland{
    boolean gameRunning;
    int currentLevel, moveCount, maxMoves = 250;
    int[] moveStatics = new int[10];
    Turtle t = new Turtle(1100,700);
    Level l = new Level();
    String playerDirection = "";
    
    public PushyIsland(){ // 5;
        gameRunning = false;
        currentLevel = 0;
        run();
    }
    
    void run() { // Runtime // 3;
        if (gameRunning) {
            l = loadLevel(currentLevel);
            if (l != null) {
                drawLevel(l);
            }
        } else {
            showTitleScreen();
        }
    }
    
    void startGame(){ // Spiel starten // 4;
        if (!gameRunning) {
            gameRunning = true;
            currentLevel = 1;
            run();    
        } else System.out.println("[PushyIsland] Das Spiel ist bereits im gange. \n");
    }
    
    void endGame(){ // Spiel 'beenden' // 4;
    gameRunning = false;
    currentLevel = 0;
    System.out.println("[PushyIsland] Spiel beendet. \n");
    Clerk.clear();
    }

    void showTitleScreen(){ // Titelbildschirm // 18;
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

    Level loadLevel(int currentLevel) { // Einzelne Level generieren // 24;
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
                newLevel.loadWorld(newLevel.level7); // Level 7 laden
                break;
            case 8:
                newLevel.loadWorld(newLevel.level8); // Level 8 laden
                break;
            case 9:
                newLevel.loadWorld(newLevel.level9); // Level 9 laden
                break;
            case 10:
                newLevel.loadWorld(newLevel.level10); // Level 10 laden
                break;
            default:
                gameWon();
        }
        return newLevel;
    }
    
    void gameWon(){ // Spiel gewonnen // 1;
        IntStream.of(moveStatics).forEach(System.out::println); // Scoreboard ausgeben
        System.out.println("[PushyIsland] Es gibt keine weiteren Level, <3-lichen Glueckwunsch. \n");
        System.out.println("[PushyIsland] Moechten Sie weitere Level spielen?");
        System.out.println("[PushyIsland] Verwenden Sie p.randomLevel() oder erstellen Sie ein eigenes.");
        endGame();
    }

    void drawLevel(Level l) { // Das Level anzeigen // 20;
        int tx = 50, ty = 50;
        t.reset().left(90).textSize = 50;
        IntStream.range(0, l.world.length).forEach(i -> { // Level durchlaufen und bestimmte -
            IntStream.range(0, l.world[i].length).forEach(j -> { // Zeichen in der View aufmalen.
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
                case 8 -> { y++ ; yield "🏠"; }   // Haus
                default -> "❌";                  // Fehler
            };
            t.color(0, 0, 205).moveTo(x, y).text(emoji);
            });
        });
        levelStats();
        maxMovesUsed();
    }

    void levelStats(){ // Level Statistiken // 3;
        t.textSize = 30;
        t.moveTo(70, 40).color(0, 0, 0).text("Level: " + currentLevel); // Aktuelles Level anzeigen
        t.moveTo(80, 80).text("Moves: " + moveCount); // Anzahl Moves anzeigen
    }

    void maxMovesUsed(){ // Maximale Moves erreicht // 2;
        if (moveCount > maxMoves) {
            System.out.println("[PushyIsland] Du hast zu viele Zuege gemacht. \n");
            resetLevel();
        }
    }
    
    void resetLevel() { // Methode um das derzeitiges Level zurücksetzen // 5;
        if (gameRunning) {
            System.out.println("[PushyIsland] Kleinen Moment, das Level wird zurueckgesetzt. \n");
            moveCount = 0;                         // Move Counter zurücksetzen
            l = loadLevel(currentLevel);           // Aktualisieren der vorhandenen Level-Instanz
            drawLevel(l);                          // Level anzeigen
        } else System.out.println("[PushyIsland] Das Spiel wurde noch nicht gestartet. \n");
    }

    void levelPassed(){ // Methode welche nach Levelabschluss ausgeführt wird // 6;
        if (gameRunning) {
            System.out.println("[PushyIsland] Level wurde mit " + moveCount +  " Moves geschafft. \n");
            if (currentLevel <= 10){
            moveStatics[currentLevel - 1] = moveCount; // Move Counter speichern
            moveCount = 0;                             // Move Counter zurücksetzen
            currentLevel++;                            // Derzeitiges Level erhöhen
        }
            run();
        } else System.out.println("[PushyIsland] Das Spiel wurde noch nicht gestartet. \n");
    }

    public String findPlayer(Level l) { // Methode um den Spieler auf dem Spielfeld zu finden // 2;
        for (int i = 0; i < l.world.length; i++) {
            for (int j = 0; j < l.world[i].length; j++) {
                if (l.world[i][j] == 7) return i + "," + j;
            }
        }
        return "Player not found.";
    }

    public int countShell(){ // Methode um die Anzahl der Muscheln zu zählen // 3;
        int shellCount = 0;
        for (int i = 0; i < l.world.length; i++) {
            for (int j = 0; j < l.world[i].length; j++) {
                if (l.world[i][j] == 6) shellCount++;
            }
        }
        return shellCount;
    }

    public int getObjectFromPosition(Level l, String pos){ // Methode um das Objekt an einer bestimmten Position zu finden // 4;
        int devider = pos.lastIndexOf(",");
        int y = Integer.parseInt(pos.substring(0, devider));
        int x = Integer.parseInt(pos.substring(devider + 1, pos.length()));
        return l.world[y][x];
    }

    boolean rules() { // Die Grundregeln des Spiels // 23;
        String player_pos = findPlayer(l);
        int devider = player_pos.lastIndexOf(",");
        int player_posY = Integer.parseInt(player_pos.substring(0, devider));
        int player_posX = Integer.parseInt(player_pos.substring(devider + 1, player_pos.length()));
    
        int[] objectPos = getPossitionFromObject(player_posY, player_posX);
        int object_posY = objectPos[0];
        int object_posX = objectPos[1];
        int object = objectPos[2];
    
        switch (object) {
            case 0:
                System.out.println("[PushyIsland] Spieler kann nicht ins Wasser. \n");
                return false;
            case 1:
                movePlayer(player_posY, player_posX, object_posY, object_posX);
                break;
            case 2,3:
                return false;
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
            } else System.out.println("[PushyIsland] Halt! Es wurden noch nicht alle Muscheln eingesammelt. \n");
                break;
            default:
                break;
        }
        drawLevel(l); // Verändertes Spielfeld neuladen
        return true;
    }
    
    int[] getPossitionFromObject(int player_posY, int player_posX) { // Position des Objekts finden // 19;
        int object_posY = 0, object_posX = 0, object = 0;
    
        switch (playerDirection) {
            case "up":
                object = getObjectFromPosition(l, ((player_posY - 1) + "," + player_posX));
                object_posY = player_posY - 1;
                object_posX = player_posX;
                break;
            case "down":
                object = getObjectFromPosition(l, ((player_posY + 1) + "," + player_posX));
                object_posY = player_posY + 1;
                object_posX = player_posX;
                break;
            case "left":
                object = getObjectFromPosition(l, (player_posY + "," + (player_posX - 1)));
                object_posY = player_posY;
                object_posX = player_posX - 1;
                break;
            case "right":
                object = getObjectFromPosition(l, (player_posY + "," + (player_posX + 1)));
                object_posY = player_posY;
                object_posX = player_posX + 1;
                break;
            default:
                break;
        }
        return new int[]{object_posY, object_posX, object}; // Rückgabe der Position des Objekts
    }
    
    void movePlayer(int player_posY, int player_posX, int object_posY, int object_posX) { // Spieler bewegen // 3;
        l.world[player_posY][player_posX] = 1;
        l.world[object_posY][object_posX] = 7;
        moveCount++;
    }
    
    boolean moveBox(int player_posY, int player_posX, int object_posY, int object_posX) { // Box bewegen // 19;
        int newBoxPosY = object_posY, newBoxPosX = object_posX;
    
        switch (playerDirection) {
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
        
    public void move(Move m) { // Move richtung anpassen.  // 10;
        if (gameRunning) {
            switch (m) {
                case UP:
                    playerDirection = "up";    // Bewegung nach oben
                    break;
                case DOWN:
                    playerDirection = "down";  // Bewegung nach unten
                    break;
                case LEFT:
                    playerDirection = "left";  // Bewegung nach links
                    break;
                case RIGHT:
                    playerDirection = "right"; // Bewegung nach rechts
                    break;
                default:
                    break;
            }
            rules();
        }
    }

    void randomLevel(){ // Random Level generieren und anzeigen. // 2;
        drawLevel(l.generate());
        currentLevel++;
    }

    void playOwnLevel(int[][] ownLevel){ // Eigenes Level spielen // 3;
        gameRunning = true;
        l.world = ownLevel;
        drawLevel(l);
    }

    void clearScreen(){ // Den Bildschirm leeren // 1;
        Clerk.clear();
    }
}

enum Move { // Enum für die Bewegungsrichtungen
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
    
    Level generate(){ // 16;
        Random rand = new Random();
        Level l = new Level();

        // Spieler Random positionieren und Land herum
        int playerX = rand.nextInt(20) + 1;
        int playerY = rand.nextInt(12) + 1;
        for (int i = playerY - 1; i < playerY + 2; i++) {
            for (int j = playerX - 1; j < playerX + 2; j++) {
                l.world[i][j] = 1;
            }
        } l.world[playerY][playerX] = 7;
        
        // Ein Haus platzieren
        int houseX = rand.nextInt(20) + 1;
        int houseY = rand.nextInt(12) + 1;
        for (int i = houseY - 1; i < houseY + 2; i++) {
            for (int j = houseX - 1; j < houseX + 2; j++) {
                if (l.world[i][j] != 7) {
                    l.world[i][j] = 1;
                }
            }
        } l.world[houseY][houseX] = 8;
        
        int shellCount = rand.nextInt(5) + 1; // Random Muscheln platzieren und Land drumherum
        for (int i = 0; i < shellCount; i++) {
            int shellX = rand.nextInt(20) + 1;
            int shellY = rand.nextInt(12) + 1;
            if (l.world[shellY][shellX] == 0) {
                for (int j = shellY - 1; j < shellY + 2; j++) {
                    for (int k = shellX - 1; k < shellX + 2; k++) {
                        if (l.world[j][k] != 7) {
                            l.world[j][k] = 1;
                        }
                    }
                } l.world[shellY][shellX] = 6;
            }
        }
        return l;
    }

    int[][] loadWorld(int[][] world){ // Worldloader // 2;
        this.world = world;

        try {
            Thread.sleep(1500); // "Lade" Zeit
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return world; // Level zurückgeben
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
        { 0, 0, 0, 0, 0, 0, 1, 1, 1, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 1, 1, 1, 3, 2, 1, 1, 1, 0, 1, 1, 2, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 3, 1, 1, 1, 5, 5, 5, 3, 1, 1, 0, 0, 1, 8, 2, 0, 0, 0, 0 },
        { 0, 0, 0, 1, 3, 2, 1, 1, 5, 5, 5, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 1, 1, 3, 5, 3, 1, 1, 0, 0, 1, 1, 3, 0, 0, 0 },
        { 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 3, 1, 0, 0, 1, 6, 0, 0, 0 },
        { 0, 0, 0, 1, 4, 4, 1, 0, 0, 1, 2, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0 },
        { 0, 0, 0, 2, 1, 1, 1, 1, 0, 0, 1, 1, 4, 1, 6, 3, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 7, 2, 1, 1, 6, 0, 0, 1, 1, 3, 2, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 1, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
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
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 1, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }};

    int[][] level10 = {
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 6, 1, 1, 1, 1, 2, 0, 0, 0, 0, 3, 1, 1, 1, 1, 1, 3, 0, 0 },
        { 0, 0, 0, 1, 4, 1, 1, 4, 0, 0, 0, 0, 0, 1, 1, 3, 5, 5, 6, 1, 3, 0 },
        { 0, 0, 0, 2, 1, 4, 1, 0, 0, 0, 0, 0, 0, 1, 5, 5, 5, 5, 1, 1, 0, 0 },
        { 0, 0, 0, 0, 1, 1, 4, 1, 0, 0, 0, 0, 0, 1, 1, 5, 5, 1, 1, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 1, 4, 1, 0, 0, 0, 0, 0, 3, 1, 1, 4, 1, 2, 0, 0, 0 },
        { 0, 0, 0, 0, 3, 7, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 1, 1, 1, 1, 2, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 6, 1, 8, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }};
}

// Code by Leon Sahl