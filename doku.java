/open start.java

Clerk.markdown(
    Text.fillOut(
"""
    
# Dokumentation

## Was ist PushyIsland?
**PushyIsland ist ein Spiel, welches ich im Rahmen des Moduls "Programmierung" erstellt habe. <br>
Das Spiel ist ein einfaches Puzzle-Spiel, bei dem der Spieler den Charakter "Pushy" steuert. <br>
Das Ziel des Spielst ist es, mithilfe von Boxen das Ziel zu erreichen und vorher alle Muscheln zu sammeln. <br>
Das Hauptspiel besteht aus 10 selbst erstellten Leveln, die es zu lösen gilt. <br>
Danach gibt es die Möglichkeit eigene Level zu erstellen und zu spielen, oder sich welche generieren zu lassen.** <br>
**Dazu weiter unten mehr**

## Wie kann sich der Spieler bewegen? (Szenario 1)
**Der Spieler bewegt sich innerhalb der einzelnen Kästchen fort, er kann sich nur auf dem Sand bewegen. <br>
Die grünen Kästchen repräsentieren eine Anhöhung, auf die der Spieler nicht kommen kann. <br>
Anhöhung sowie Berge und Bäume dienen als Hindernisse auf der Insel. <br>
Oben links befindet sich ein Score, welcher die Anzahl der Züge des Spielers zählt. <br>
Bitte beachten Sie, dass sie **nicht** diagonal laufen können, versuchen Sie dies zu vermeiden. <br>
Man hat pro Level eine eingestellte Maximale Anzahl an Moves, diese ist 250.**
<br>
**Bewegt wird der Spieler über die Jshell mithilfe der Befehle p.move(Move.UP/DOWN/LEFT/RIGHT). <br>
Da dies eine erschwärliche Fortbewegungsmöglichkeit ist, habe ich die Möglichkeit der Tastaturtasteneingabe hinzugefügt. <br>
Mithilfe von W/A/S/D kann man den Spieler im Browser direkt steuern.**

```java
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
                System.out.println("[PushyIsland] Spieler kann nicht ins Wasser.");
                return false;
            case 1:
                movePlayer(player_posY, player_posX, object_posY, object_posX);
                break;

void movePlayer(int player_posY, int player_posX, int object_posY, int object_posX) {
        l.world[player_posY][player_posX] = 1;
        l.world[object_posY][object_posX] = 7;
        moveCount++;
    }
```

## Boxen verschieben? (Szenario 2/3)
**Der Spieler ist so stark, dass er die auf der Insel rumliegenden Boxen verschieben kann. <br>
Das Verschieben ermöglicht ihm das vorranschreiten im Level und letzendlich das abschließen. <br>
Bei der Programmierung habe ich darauf geachtet von welcher Richtung die Box verschoben wird,  
ebenfalls musste ich darauf achten, dass sich vor der Box kein Hinderniss befindet,  
dies erfrage ich mithilfe der getObjectPossition Methode.<br>
Eine weitere Mechanik ist es, die Boxen ins Wasser zu schieben um dann sich eine Art "Brücke" zu bauen**

```java 
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
```

## Muscheln sammeln/retten? (Szenario 4)
**Die Muscheln sind die Bewohner der Insel, sie sind in den Leveln verteilt und müssen gesammelt werden. <br>
Sollte der Spieler alle Muscheln gesammelt haben, dann darf er zum Ziel.**

## Das Level neustarten
**Manchmal kann es passieren dass man sich Softlocked oder einfach nicht weiter weiß, 
dafür gitb es die Möglichkeit des Level neuzustarten. <br>
Mithilfe des Befehls p.restartLevel() oder der Taste "R" wird das aktuelle Level neugestartet. <br>
Das funktioniert, da ich das Level in einem Array abgespeichert habe und dieses Array bei einem Neustart neulade.**

```java 
void resetLevel() { // Methode um das derzeitiges Level zurücksetzen // 5;
        if (gameRunning) {
            System.out.println("[PushyIsland] Kleinen Moment, das Level wird zurückgesetzt. \n");
            moveCount = 0;                         // Move Counter zurücksetzen
            l = loadLevel(currentLevel);           // Aktualisieren der vorhandenen Level-Instanz
            drawLevel(l);                          // Level anzeigen
        } else System.out.println("[PushyIsland] Das Spiel wurde noch nicht gestartet. \n");
    }
```

## Eigene Level erstellen (Szenario 5.1)
**Jeder kann kinderleicht ein eigenes Level erstellen, ich selbst habe 10 level erstellt, das hat wirklich Spaß gemacht. <br>
Dafür habe ich eine Methode erstellt, die ein Level aus einem Array erstellt. <br>
Wenn du ein eigenes Level erstellen möchtest, dann kannst du folgendes Array verwenden:**

```java
int[][] ownLevel = {
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
```
**Kopiere das Array in einen Texteditor und ändere die Zahlen, um dein eigenes Level zu erstellen.**
## Folgende Zahlen repräsentieren die Objekte:
```java 
     0 -> "🟦";      // Wasser
     1 -> "🟨";      // Boden
     2 -> "🗻";      // Berg
     3 -> "🌴";      // Palme
     4 -> "📦";      // Box
     5 -> "🟩";      // Erhöhung
     6 -> "🐚";      // Muschel
     7 -> "😋";      // Spieler
     8 -> "🏠";      // Haus - Ziel
others -> "❌";      // Fehler (Falsche Zahl)
```
## Möchtest du dein eigenes Level testen/spielen?
1. **Füge das erstellte Array in die Jshell ein.**
2. **Führe nun den Befehl p.playOwnLevel(ownLevel) aus.**
3. **Spaß haben und das Level lösen.**

# Scoreboard
| Name | Moves ges.|
| ----------- | ----------- |
| Leon | 558 |
| Andrea | xxx |
| Martin | 564 |
| | |
| | |

## Der Random Level Generator (Szenario 5.2)
**Ein Random Level kann mithilfe des Befehls p.randomLevel() generiert werden. <br>
Dieser Befehl generiert ein zufälliges Level, welches du dann spielen kannst. <br>
Sollte dir das Level nicht gefallen, oder es ist unmöglich, kannst du mit R oder p.resetLevel() ein neues generieren lassen. <br>
Gefällt dir ein Level besonders gut, dann kannst du dir mit p.l.world anzeigen lassen und abspeichern. <br>
Viel Spaß beim Spielen!**

```java 
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
    
    Level generatePlayer(Level l){ // Spieler / Land
        Random rand = new Random();
        int playerX = rand.nextInt(19) + 1;
        int playerY = rand.nextInt(11) + 1;
        for (int i = playerY - 1; i < playerY + 2; i++) {
            for (int j = playerX - 1; j < playerX + 2; j++) {
                l.world[i][j] = 1;
            }
        }
        l.world[playerY][playerX] = 7;
        return l;
    }
```
"""));