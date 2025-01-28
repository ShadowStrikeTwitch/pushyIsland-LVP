import javax.swing.*;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PushyIsland{
    boolean gameRunning, titleScreen, canMove;
    int currentLevel, moveCount, maxMoves = 250;
    int[] moveStatics = new int[10];
    Turtle t = new Turtle(1100,700);
    Level l = new Level();
    String playerDirection = "";
    
    public PushyIsland(){ // 5;
        gameRunning = false;
        currentLevel = 0;
        System.out.println("\n[PushyIsland] Willkommen zu PushyIsland.");
        run();
    }
    
    // ------------------------- SPIELINSTANZ LOGIK -------------------------

    void startGame() {
        if (gameRunning) {
            System.out.println("Das Spiel ist bereits im Gange.");
            return;
        }
        gameRunning = true;
        canMove = true;
        currentLevel = 1;
        run();
    }

    void run() { // Runtime // 3;
        if (gameRunning) {
            if (currentLevel > 10) {
                gameWon();
                return;
            }
            l = loadLevel(currentLevel);
            drawLevel(l);
        } else {
            showTitleScreen();
        }
    }
    
    void showTitleScreen(){ // Titelbildschirm // 18;
        if (titleScreen) {
            System.out.println("Der Titelbildschirm wurde bereits angezeigt.");
            return;
        }
        titleScreen = true;
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
    
    void gameWon(){ // Spiel gewonnen // 6;
        if (currentLevel == 50) {
            moveCount = 0;
            System.out.println("[PushyIsland] Du hast das Bonuslevel geschafft. \n");
            return;
        }
        int sum = IntStream.of(moveStatics).sum(); // Scoreboard Summe
        System.out.println("\n\n\n\n\n[PushyIsland] Du hast das Ende der Kampagne erreicht, <3-lichen Glueckwunsch.\n");
        System.out.println("[PushyIsland] Trage gerne " + sum + " ins Scoreboard ein.");
        System.out.println("[PushyIsland] Moechten Sie weitere Level spielen?");
        System.out.println("[PushyIsland] Verwenden Sie p.randomLevel() oder erstellen Sie ein eigenes.");
        System.out.println("[PushyIsland] Oder ESC / p.endGame um das Spiel zu beenden.");
        t.reset();
    }
    
    void endGame() { // Spiel beenden // 3;
        resetGameVariables();
        t.reset();
        System.out.println("[PushyIsland] Spiel beendet. Neustart mit p.showTitleScreen() \n");
    }
    
    void resetGameVariables() { // Spielvariablen zurücksetzen // 4;
        gameRunning = false;
        titleScreen = false;
        moveCount = 0;
        moveStatics = new int[10];
    }

    // ------------------------- LEVEL LOGIK -------------------------

    Level loadLevel(int currentLevel) {
        System.out.println("[PushyIsland] Level " + currentLevel + " wird geladen... \n");
        Level newLevel = new Level();
        Map<Integer, int[][]> levels = Map.of(
            1, l.level1,
            2, l.level2,
            3, l.level3,
            4, l.level4,
            5, l.level5,
            6, l.level6,
            7, l.level7,
            8, l.level8,
            9, l.level9,
            10, l.level10
        );
        newLevel.loadWorld(levels.get(currentLevel));
        return newLevel;
    }
    
    void drawLevel(Level l) { // Das Level anzeigen // 9;
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
            t.moveTo(x, y).text(emoji);
            });
        });
        levelStats();
        maxMovesUsed();
        canMove = true;
    }

    void levelStats(){ // Level Statistiken // 5;
        t.textSize = 30;
        if (currentLevel < 20) {
            t.moveTo(70, 40).color(0, 0, 0).text("Level: " + currentLevel); // ANZEIGE: Aktuelles Level
        } else if (currentLevel == 50){
            t.moveTo(110, 40).color(0, 0, 0).text("Level: Random");          // ANZEIGE: Bonus Level
        } else t.moveTo(110, 40).color(0, 0, 0).text("Level: Eigenes");     // ANZEIGE: Eigenes Level
        t.moveTo(80, 80).text("Moves: " + moveCount);                       // ANZEIGE: Anzahl Moves
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
            if (currentLevel < 11) {
                l = loadLevel(currentLevel);       // Aktualisieren der vorhandenen Level-Instanz
                drawLevel(l);                      // Level anzeigen
            } else if (currentLevel == 50) {
                l = l.generate();                  // Random Level generieren
                drawLevel(l);                      // Level anzeigen
            } 
        } else System.out.println("[PushyIsland] Das Spiel wurde noch nicht gestartet. \n");
    }

    void levelPassed(){ // Methode welche nach Levelabschluss ausgeführt wird // 6;
        if (gameRunning) {
            if (currentLevel < 10){
                System.out.println("[PushyIsland] Level " + currentLevel + " wurde mit " + moveCount +  " Moves geschafft. \n");
                moveStatics[currentLevel - 1] = moveCount; // Move Counter speichern
                moveCount = 0;                             // Move Counter zurücksetzen
                currentLevel++;                            // Derzeitiges Level erhöhen
            } else if (currentLevel == 10){
                currentLevel++;
                gameWon();
            } else if (currentLevel == 50){
                l = l.generate();                          // Random Level generieren
                drawLevel(l);                   // Random Level generieren
            }
            run();
        } else System.out.println("[PushyIsland] Das Spiel wurde noch nicht gestartet. \n");
    }

    // ------------------------- SPIELER LOGIK -------------------------

    public String findPlayer(Level l) { // Methode um den Spieler auf dem Spielfeld zu finden // 2;
        for (int i = 0; i < l.world.length; i++) {
            for (int j = 0; j < l.world[i].length; j++) {
                if (l.world[i][j] == 7) return i + "," + j;
            }
        }
        return "Spieler nicht gefunden!";
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
    
        canMove = true;
        switch (object) {
            case 0:
                System.out.println("[PushyIsland] Spieler kann nicht ins Wasser. \n");
                return false;
            case 1:
                movePlayer(player_posY, player_posX, object_posY, object_posX);
                break;
            case 2,3,5:
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
                System.out.println("[PushyIsland] Error. \n");
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
        if (gameRunning && canMove) {
            canMove = false;
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
        currentLevel = 50;
        gameRunning = true;
        l = l.generate();
        drawLevel(l);
    }

    void playOwnLevel(int[][] ownLevel){ // Eigenes Level spielen // 3;
        gameRunning = true;
        l.world = ownLevel;
        currentLevel = 100;
        drawLevel(l);
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

    // ------------------------- LEVEL GENERATOR -------------------------

    Level generate(){ // 16;
        Level l = new Level();
        generatePlayer(l); // Spieler platzieren
        generateBoxes(l); // Boxen platzieren
        generateHouse(l); // Haus platzieren
        generateShells(l); // Muscheln platzieren
        generateSand(l); // Sand verteilen
        generateWater(l); // Wasser um das Spielfeld
        generateObstacles(l); // Hindernisse generieren
        return l;
    }

    Level generatePlayer(Level l) { // Spieler / Land
        Random rand = new Random();
        int playerX = rand.nextInt(19) + 1;
        int playerY = rand.nextInt(11) + 1;
        IntStream.range(playerY - 1, playerY + 2).forEach(i ->
            IntStream.range(playerX - 1, playerX + 2).forEach(j -> l.world[i][j] = 1)
        );
        l.world[playerY][playerX] = 7;
        return l;
    }
    
    Level generateBoxes(Level l) {
        Random rand = new Random();
        int boxCount = rand.nextInt(5) + 2;
        IntStream.range(0, boxCount).forEach(i -> {
            int boxX = rand.nextInt(19) + 1;
            int boxY = rand.nextInt(11) + 1;
            if (l.world[boxY][boxX] == 0) {
                IntStream.range(boxY - 1, boxY + 2).forEach(j ->
                    IntStream.range(boxX - 1, boxX + 2).forEach(k -> l.world[j][k] = 1)
                );
                l.world[boxY][boxX] = 4;
            }
        });
        return l;
    }
    
    Level generateHouse(Level l) { // Haus / Land
        Random rand = new Random();
        int houseX = rand.nextInt(19) + 1;
        int houseY = rand.nextInt(11) + 1;
        IntStream.range(houseY - 1, houseY + 2).forEach(i ->
            IntStream.range(houseX - 1, houseX + 2).forEach(j -> {
                if (l.world[i][j] != 7) {
                    l.world[i][j] = 1;
                }
            })
        );
        l.world[houseY][houseX] = 8;
        return l;
    }
    
    Level generateShells(Level l) { // Muscheln / Land
        Random rand = new Random();
        int shellCount = rand.nextInt(5) + 1;
        IntStream.range(0, shellCount).forEach(i -> {
            int shellX = rand.nextInt(19) + 1;
            int shellY = rand.nextInt(11) + 1;
            if (l.world[shellY][shellX] == 0) {
                IntStream.range(shellY - 1, shellY + 2).forEach(j ->
                    IntStream.range(shellX - 1, shellX + 2).forEach(k -> {
                        if (l.world[j][k] != 7) {
                            l.world[j][k] = 1;
                        }
                    })
                );
                l.world[shellY][shellX] = 6;
            }
        });
        return l;
    }
    
    Level generateSand(Level l) { // Bisschen Land verteilen
        Random rand = new Random();
        IntStream.range(1, 13).forEach(i ->
            IntStream.range(1, 21).forEach(j -> {
                if (l.world[i][j] == 0) {
                    l.world[i][j] = rand.nextInt(2);
                }
            })
        );
        return l;
    }
    
    Level generateWater(Level l) { // Wasser um das Spielfeld
        IntStream.range(0, 14).forEach(i ->
            IntStream.range(0, 22).forEach(j -> {
                if (i == 0 || i == 13 || j == 0 || j == 21) {
                    l.world[i][j] = 0;
                }
            })
        );
        return l;
    }
    
    Level generateObstacles(Level l) { // Hindernisse generieren
        Random rand = new Random();
        int obstacleCount = rand.nextInt(5) + 1;
        IntStream.range(0, obstacleCount).forEach(i -> {
            int obstacleX = rand.nextInt(19) + 1;
            int obstacleY = rand.nextInt(11) + 1;
            if (l.world[obstacleY][obstacleX] == 0) {
                int sprite = rand.nextInt(2);
                l.world[obstacleY][obstacleX] = (sprite == 0) ? 2 : 3;
            }
        });
        return l;
    }

    // ------------------------- LEVEL LOADER UND KAMPAGNE -------------------------

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
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 1, 1, 0, 0, 0 },
        { 0, 0, 0, 0, 3, 8, 3, 0, 0, 0, 0, 0, 0, 0, 1, 3, 1, 2, 1, 0, 0, 0 },
        { 0, 0, 0, 3, 1, 1, 1, 3, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0 },
        { 0, 0, 3, 1, 5, 5, 1, 1, 3, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 5, 2, 5, 1, 1, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 2, 5, 2, 1, 1, 1, 4, 1, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 1, 5, 5, 1, 1, 4, 1, 1, 1, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 1, 1, 1, 1, 4, 1, 1, 1, 7, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 1, 3, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }};

    int[][] level3 = {
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 3, 2, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 1, 1, 1, 6, 1, 5, 5, 5, 5, 5, 5, 8, 1, 0, 0, 0, 0 },
        { 0, 0, 0, 1, 2, 1, 3, 1, 1, 5, 5, 5, 3, 2, 2, 5, 3, 1, 0, 0, 0, 0 },
        { 0, 0, 0, 3, 1, 3, 1, 3, 5, 5, 3, 3, 5, 5, 3, 5, 1, 1, 0, 0, 0, 0 },
        { 0, 0, 0, 2, 1, 1, 1, 1, 5, 3, 5, 2, 5, 5, 5, 1, 6, 1, 0, 0, 0, 0 },
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
        { 0, 0, 1, 5, 5, 5, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 3, 3, 2, 0, 0 },
        { 0, 0, 1, 1, 1, 1, 1, 4, 1, 1, 1, 0, 0, 0, 1, 1, 3, 1, 8, 3, 0, 0 },
        { 0, 0, 3, 2, 1, 1, 1, 1, 1, 4, 1, 0, 0, 0, 1, 6, 1, 1, 1, 3, 0, 0 },
        { 0, 0, 0, 0, 0, 1, 1, 1, 4, 1, 1, 0, 0, 0, 1, 1, 3, 1, 3, 3, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
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
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 3, 1, 0, 0, 4, 1, 1, 1, 3, 0, 0, 0 },
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
        { 0, 0, 0, 6, 1, 1, 4, 2, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0 },
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
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3, 3, 0, 0, 0, 1, 1, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 4, 1, 1, 2, 3, 6, 1, 3, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 1, 6, 1, 1, 3, 2, 1, 1, 1, 1, 4, 0, 1, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 3, 1, 1, 1, 5, 5, 5, 3, 1, 1, 3, 5, 5, 1, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 6, 3, 2, 4, 1, 5, 5, 5, 1, 1, 1, 2, 5, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 1, 1, 1, 1, 1, 0, 3, 5, 3, 1, 1, 3, 0, 0, 3, 2, 0, 0, 0 },
        { 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 0, 0, 0, 1, 8, 1, 0, 0 },
        { 0, 0, 0, 0, 0, 1, 3, 2, 1, 5, 5, 2, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0 },
        { 0, 0, 0, 0, 0, 7, 1, 3, 1, 1, 4, 1, 1, 1, 6, 3, 0, 0, 1, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 3, 2, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
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

    int[][] thanksForPlaying = {
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        { 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1 },
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        { 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0 },
        { 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1 },
        { 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0 },
        { 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1 },
        { 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0 },
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        { 1, 6, 1, 6, 1, 6, 1, 6, 1, 6, 1, 1, 6, 1, 6, 1, 6, 1, 6, 1, 6, 1 },
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        { 1, 7, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 8, 1 },
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }};
}
// Code by Leon Sahl