Clerk.clear();


class pushyisland{
    boolean gameRunning;
    int currentLevel;

    pushyisland(){
        showTitleScreen();
    }

    void startGame(){
        gameRunning = true;
        currentLevel = 1;
        run();
    }

    void run(){
        switch (currentLevel) {
            case 1:

                break;
            case 2:

                break;
            default:
                break;
        }
    }

    void endGame(){
        Clerk.clear();
    }

    void showTitleScreen(){
        Turtle t = new Turtle(1000,1000);        

        t.moveTo(500,340);
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

        t.left(90);
        t.textSize = 85;
        t.color(0,255,200).moveTo(500, 100);
        t.text("PushyIsland - remake");
        
        t.textSize = 20;
        t.moveTo(750, 130).color(0,255,40);
        t.text("by Leon Sahl");
        
        t.textSize = 50;
        t.color(150,100,255).moveTo(500, 700);

        t.text("Spiel Starten mit p.startGame");
        t.moveTo(500, 800);
        t.text("Spiel Verlassen mit p.endGame");
    }

    void clearScreen(){
        Clerk.clear();
        Clerk.markdown("PushyIsland - remake");
    }

    void generatelevelone(){

    }

    void generateleveltwo(){

    }
    
    String showLevelOne(){

        return "[PushyIsland] Level 1";
    }
    
    String showLevelTwo(){
    
        return "[PushyIsland] Level 2";
    }

    String showWinScreen(){

        return "[PushyIsland] Level geschafft.";
    }
}

class level{
    int[][] world;

    level(){
        world= new int[12][20];
        generateWorld();
    }

    void generateWorld(){
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world.length; j++) {
                world[i][j] = 0;
            }
        }
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < world.length; i++) {
            s.append("/n");
            for (int j = 0; j < world.length; j++) {
                s.append(world[i][j]);
            }
        }
        return "" + s;
    }
}


pushyisland p = new pushyisland();
level l = new level();