public class Conquest extends PApplet {

    PImage gate;

    String screen = "start";

    float btnX; //button
    float btnY;
    float btnW;
    float btnH;
    boolean instructionsShown;

    boolean failure; //failure

    float xpos; //character movement
    float xbase;
    float ypos;
    float ybase;
    float dx;
    float dy;
    boolean mouseOn;
    boolean keyPressable;
    float loop;
    float characterspeed;
    boolean wPressed;
    boolean aPressed;
    boolean sPressed;
    boolean dPressed;

    boolean blocking; //blocking (don't take damage)

    int health; //health
    float barwidth;
    float healthDebounceBlock;
    float healthDebounceNorm;
    float regenDebounce;

    float[] circleX = new float[50]; //attacks
    float[] targetX = new float[50];
    float[] circleY = new float[50];
    float[] targetY = new float[50];
    int i;

    float lvlabundance; //level
    float lvlrepetition;
    float baserepetition;
    float regenTime;
    int level;

    boolean keyTakenA1 = false; //keys
    boolean keyTakenA2 = false;
    boolean keyTakenB2 = false;
    boolean keyTakenA3 = false;
    boolean keyTakenB3 = false;
    boolean keyTakenC3 = false;
    float countdown = 0;


    void setup() {
        size(1000, 700);

        //failure = false;      //tests
        //failure = true;

        gate = loadImage("key.png");

        xbase = 50; //character position
        ybase = 50;

        mouseOn = false; //character movement
        loop=0;
        keyPressable = true;
        characterspeed = 3;

        btnX = (width)/2 - 50; //reset button
        btnY = (height)/2 - 20;
        btnW = 100;
        btnH = 40;

        health = 100; //health
        healthDebounceBlock = 15;
        healthDebounceNorm = 15;
        regenDebounce = 50;

        lvlrepetition = 225; //levels
        level = 1;

    }

    void draw() {
        if(screen == "start") {
            startScreen();
        } else if(screen == "ingame") {
            gameScreen();
        } else if(screen == "gameOver") {
            gameOverScreen();
        } else if(screen == "pause") {
            pauseScreen();
        }
    }

    boolean isMouseOverResetButton(){
        return mouseX > btnX && mouseX < btnX + btnW &&
                mouseY > btnY && mouseY < btnY + btnH;
    }

    boolean isCharacterOverDepot(){
        return xpos > 5 && xpos < 155 && ypos > 5 && ypos < 105;
    }

    boolean isMouseOverResumeButton(){
        return mouseX > 200 && mouseX < 800 && mouseY > 300 && mouseY < 400;
    }

    boolean isMouseOverHelpButton(){
        return mouseX > 200 && mouseX < 800 && mouseY > 425 && mouseY < 525;
    }

    boolean isMouseOverRestartButton(){
        return mouseX > 200 && mouseX < 800 && mouseY > 550 && mouseY < 650;
    }

    boolean isMouseOverCloseButton(){
        return mouseX > 695 && mouseX < 725 && mouseY > 125 && mouseY < 155;
    }


    void mousePressed() {
        if(screen == "start") {
            if(!isMouseOverHelpButton() && !isMouseOverCloseButton()) {
                screen = "ingame";
                instructionsShown = false;
            } else if(isMouseOverHelpButton()) {
                instructionsShown = true;
            } else if(isMouseOverCloseButton()) {
                instructionsShown = false;
            }
        } else if(screen == "pause") {
            if(isMouseOverResumeButton()) {
                screen = "ingame";
                instructionsShown = false;
            } else if(isMouseOverHelpButton()) {
                instructionsShown = true;
            } else if(isMouseOverCloseButton()) {
                instructionsShown = false;
            } else if(isMouseOverRestartButton()) {
                instructionsShown = false;
                resetGame();
            }
        }
        else if(screen == "gameOver" && isMouseOverResetButton()) { //mouse reset button
            resetGame();
        }
    }


    void keyPressed() {
        if(screen == "start"){ //beginning keyPressed
            screen = "ingame";
            instructionsShown = false;
        }
        else if(screen == "ingame"){ //character movement ingame
            if(mouseOn==false||keyPressable==true){
                noCursor();
                if(keyCode == LEFT || key == 'a' || key == 'A'){
                    aPressed = true;
                }
                if(keyCode == RIGHT || key == 'd' || key == 'D'){
                    dPressed = true;
                }
                if(keyCode == UP || key == 'w' || key == 'W'){
                    wPressed = true;
                }
                if(keyCode == DOWN || key == 's' || key == 'S'){
                    sPressed = true;
                }
                if(keyCode == ENTER){
                    screen = "pause";
                }
            }
            if(keyCode == SHIFT){ //blocking
                blocking = true;
            }
        } else if(screen == "pause"){
            if(keyCode == ENTER){
                screen = "ingame";
                instructionsShown = false;
            }
        }
    }


    void keyReleased() {
        if(keyCode == SHIFT){ //stop blocking
            blocking = false;
        }
        if(keyCode == LEFT || key == 'a' || key == 'A'){
            aPressed = false;
        }
        if(keyCode == RIGHT || key == 'd' || key == 'D'){
            dPressed = false;
        }
        if(keyCode == UP || key == 'w' || key == 'W'){
            wPressed = false;
        }
        if(keyCode == DOWN || key == 's' || key == 'S'){
            sPressed = false;
        }
    }

    void startScreen() {
        background(200, 200, 255);
        fill(0);
        textAlign(CENTER, CENTER);
        textSize(48);
        text("Conquest", width/2, (height-100)/2);
        textSize(24);
        text("Press any key or click mouse to begin!", width/2, height/2);
        if(isMouseOverHelpButton()){ //help button
            fill(150);
        } else {
            fill(200);
        }
        rect(200, 425, 600, 100, 20);

        fill(0); //button texts
        textAlign(CENTER, CENTER);
        textSize(36);
        text("Game Instructions", 500, 475);

        if(instructionsShown) { //close button button
            fill(100);
            rect(250, 100, 500, 500, 35);
            if(isMouseOverCloseButton()){
                fill(150);
            } else {
                fill(200);
            }
            rect(695, 125, 30, 30, 10);
            fill(0);
            textAlign(CENTER, CENTER);
            textSize(20);
            text("X", 710, 140);

            fill(255); //game instructions
            textSize(32);
            text("Game:", width/2, 130);
            text("Controls:", width/2, 320);
            text("Have Fun!", width/2, 540);
            textSize(16);
            text("Avoid Projectiles; Getting Hits Costs 25 Health Points", width/2, 160); //game
            text("Enter Green Pools and Gain Keys", width/2, 200);
            text("Keys Appear Next to Character;", width/2, 220);
            text("Drop Them Off at the Key Drop Off to Complete Levels", width/2, 240);
            text("Complete Level 3 to Win the Game!", width/2, 280);
            text("Press B to Toggle Between Keyboard and Mouse Control", width/2, 350);
            text("Use WASD or Arrow Keys in Keyboard Mode", width/2, 370);
            text("Press Shift to Deploy Shield;", width/2, 410);
            text("Damage While Blocking is Decreased by 25%", width/2, 430);
            text("Press ENTER to Pause", width/2, 470);
        }
    }

    void gameScreen() {
        background(255);

        fill(0, 0, 100);
        rect(5, 5, 150, 100, 10); //key depot
        fill(255);
        textAlign(CENTER, CENTER);
        textSize(24);
        text("Key Drop Off", 80, 55);

        //text ("shield?"+blocking, 400, 400);           //debugging
        //text ("possible?"+keyPressable, 400, 400);

        if(wPressed){                 //WASD
            ypos-=characterspeed;
        }
        if(aPressed){
            xpos-=characterspeed;
        }
        if(sPressed){
            ypos+=characterspeed;
        }
        if(dPressed){
            xpos+=characterspeed;
        }

        //fill(0, 200, 100, 150);
        if(level == 1){           //level 1
            lvlabundance = 15;
            baserepetition = 225;
            regenTime = 50;
            if(circleCircleCollisionCheck(xpos, ypos, 30, width-75, height/2, 100) && !keyTakenA1){ //circle A1
                fill(0, 150, 50);
            } else if(!keyTakenA1){
                fill(0, 200, 100, 150);
            } else {
                fill(150);
            }
            circle(width-75, height/2, 100);
            if(circleCircleCollisionCheck(xpos, ypos, 30, width-75, height/2, 100) && !keyTakenA1) {
                fill(150);
                rect(xpos-40, ypos-20, 80, 10, 5);
                fill(200);
                rect(xpos-40, ypos-20, 80+countdown, 10, 5);
                fill(0, 150, 50);
                if(countdown > -80) {
                    countdown-=0.5;
                } else if (countdown == -80) {
                    keyTakenA1 = true;
                    countdown = 0;
                }
            }
            if(keyTakenA1 && isCharacterOverDepot()){
                level = 2;
            } else if(keyTakenA1) {
                image(gate, xpos+10, ypos, 20, 20);
            }

        } else if(level == 2) { //level 2
            lvlabundance = 25;
            baserepetition = 150;
            regenTime = 100;
            if(circleCircleCollisionCheck(xpos, ypos, 30, width-75, 2*height/3, 100) && !keyTakenA2) { //Circle A2
                fill(0, 150, 50);
            } else if(!keyTakenA2) {
                fill(0, 200, 100, 150);
            } else {
                fill(150);
            }
            circle(width-75, 2*height/3, 100);
            if(circleCircleCollisionCheck(xpos, ypos, 30, width-75, 2*height/3, 100) && !keyTakenA2) {
                fill(150);
                rect(xpos-40, ypos-20, 80, 10, 5);
                fill(200);
                rect(xpos-40, ypos-20, 80+countdown, 10, 5);
                fill(0, 150, 50);
                if(countdown > -80) {
                    countdown-=0.5;
                } else if (countdown == -80) {
                    keyTakenA2 = true;
                    countdown = 0;
                }
            }
            if(circleCircleCollisionCheck(xpos, ypos, 30, width-75, height/3, 100) && !keyTakenB2) { //Circle B2
                fill(0, 150, 50);
            } else if(!keyTakenB2) {
                fill(0, 200, 100, 150);
            } else {
                fill(150);
            }
            circle(width-75, height/3, 100);
            if(circleCircleCollisionCheck(xpos, ypos, 30, width-75, height/3, 100) && !keyTakenB2) {
                fill(150);
                rect(xpos-40, ypos-20, 80, 10, 5);
                fill(200);
                rect(xpos-40, ypos-20, 80+countdown, 10, 5);
                fill(0, 150, 50);
                if(countdown > -80) {
                    countdown-=0.5;
                } else if (countdown == -80) {
                    keyTakenB2 = true;
                    countdown = 0;
                }
            }
            if(keyTakenA2 && keyTakenB2 && isCharacterOverDepot()) {
                level = 3;
            }
            if(keyTakenA2) {
                image(gate, xpos+10, ypos, 20, 20);
            }
            if(keyTakenB2) {
                image(gate, xpos-35, ypos, 20, 20);
            }

        } else if(level == 3){ //level 3
            lvlabundance = 35;
            baserepetition = 100;
            regenTime = 200;
            if(circleCircleCollisionCheck(xpos, ypos, 30, width-75, 5*height/6, 100) && !keyTakenA3) { //Circle A3
                fill(0, 150, 50);
            } else if(!keyTakenA3){
                fill(0, 200, 100, 150);
            } else {
                fill(150);
            }
            circle(width-75, 5*height/6, 100);
            if(circleCircleCollisionCheck(xpos, ypos, 30, width-75, 5*height/6, 100) && !keyTakenA3) {
                fill(150);
                rect(xpos-40, ypos-20, 80, 10, 5);
                fill(200);
                rect(xpos-40, ypos-20, 80+countdown, 10, 5);
                fill(0, 150, 50);
                if(countdown > -80) {
                    countdown-=0.5;
                } else if (countdown == -80) {
                    keyTakenA3 = true;
                    countdown = 0;
                }
            }
            if(circleCircleCollisionCheck(xpos, ypos, 30, width-75, height/2, 100) && !keyTakenB3) {
                fill(0, 150, 50);
            } else if(!keyTakenB3){
                fill(0, 200, 100, 150);
            } else {
                fill(150);
            }
            circle(width-75, height/2, 100);
            if(circleCircleCollisionCheck(xpos, ypos, 30, width-75, height/2, 100) && !keyTakenB3) {
                fill(150);
                rect(xpos-40, ypos-20, 80, 10, 5);
                fill(200);
                rect(xpos-40, ypos-20, 80+countdown, 10, 5);
                fill(0, 150, 50);
                if(countdown > -80){
                    countdown-=0.5;
                } else if (countdown == -80){
                    keyTakenB3 = true;
                    countdown = 0;
                }
            }
            if(circleCircleCollisionCheck(xpos, ypos, 30, width-75, height/6, 100) && !keyTakenC3) {
                fill(0, 150, 50);
            } else if(!keyTakenC3) {
                fill(0, 200, 100, 150);
            } else {
                fill(150);
            }
            circle(width-75, height/6, 100);
        }
        if(circleCircleCollisionCheck(xpos, ypos, 30, width-75, height/6, 100) && !keyTakenC3) {
            fill(150);
            rect(xpos-40, ypos-20, 80, 10, 5);
            fill(200);
            rect(xpos-40, ypos-20, 80+countdown, 10, 5);
            fill(0, 150, 50);
            if(countdown > -80) {
                countdown-=0.5;
            } else if (countdown == -80) {
                keyTakenC3 = true;
                countdown = 0;
            }
        }
        if(keyTakenA3 && keyTakenB3 && keyTakenC3 && isCharacterOverDepot()) {
            failure = false;
            screen = "gameOver";
        }
        if(keyTakenA3) {
            image(gate, xpos+10, ypos, 20, 20);
        }
        if(keyTakenB3) {
            image(gate, xpos-35, ypos, 20, 20);
        }
        if(keyTakenC3) {
            image(gate, xpos-10, ypos+15, 20, 20);
        }

        barwidth = health*2; //health and healthbar
        fill(255, 0, 0);
        rect(width-215, 15, 200, 30);
        fill(0, 200, 0);
        rect(width-215, 15, barwidth, 30);
        if(health <= 0) {
            failure = true;
            screen = "gameOver";
        } else {
            fill(255);
            textAlign(CENTER, CENTER);
            textSize(16);
            text("Health: " + health, width-115, 30);
        }
        if (regenDebounce == regenTime && health < 100) {
            health++;
            regenDebounce = 0;
        } else if(regenDebounce < regenTime){
            regenDebounce++;
        }

        if(lvlrepetition<baserepetition){ //minefield
            lvlrepetition++;
        } else {
            for(i = 0; i<lvlabundance; i++) {
                circleX[i] = width-200; //positions - subject to change
                circleY[i] = height/2;
                targetX[i] = random(width);
                targetY[i] = random(height);
            }
            lvlrepetition = 0;
        }
        for(i = 0; i<lvlabundance; i++) {
            float speed = random(0.03, 0.08);
            if(blocking == true) {
                fill(0, 0, 255, 150);
            } else {
                fill(255, 0, 0, 150);
            }
            circle(circleX[i], circleY[i], 15);
            float tgtdx = targetX[i] - circleX[i];
            float tgtdy = targetY[i] - circleY[i];
            circleX[i] += tgtdx * speed;
            circleY[i] += tgtdy * speed;
            if (circleCircleCollisionCheck(xpos, ypos, 30, circleX[i], circleY[i], 15)) {
                if (blocking == true) {
                    if (healthDebounceBlock == 15) {
                        health -= 5;
                        healthDebounceBlock = 0;
                    } else {
                        healthDebounceBlock++;
                    }
                } else {
                    if (healthDebounceNorm == 15) {
                        health -= 25;
                        healthDebounceNorm = 0;
                    } else {
                        healthDebounceNorm++;
                    }
                }
            }

            if(keyPressed) { //character movement
                if((key == 'b' || key == 'B') && keyPressable == true){
                    mouseOn = !mouseOn;
                    xbase = xpos;
                    ybase = ypos;
                    keyPressable = false;
                }
            }
            if(keyPressable == false) {
                loop++;
                if(loop >= 300) {
                    loop=0;
                    keyPressable = true;
                }
            }
            if(mouseOn == true) {
                cursor();
                fill(0);
                text("Mode: Mouse", width-100, height-30);
                dx = mouseX-xpos;
                dy = mouseY-ypos;
                xpos = xbase += dx/350 * characterspeed/1.5;
                ypos = ybase += dy/350 * characterspeed/1.5;
            } else {
                fill(0);
                text("Mode:Keyboard", width-100, height-30);
            }

            fill(0);
            circle(xpos, ypos, 30); //character
            if(blocking == true) {
                characterspeed = 1;
                fill(0, 0, 255);
                rect(xpos+17, ypos-15, 2, 30); //shield rect
            } else {
                characterspeed = 4;
            }
        }
    }


    void pauseScreen() {
        background(50);
        cursor();

        if(isMouseOverResumeButton()){ //resume button
            fill(150);
        } else {
            fill(200);
        }
        rect(200, 300, 600, 100, 20);
        if(isMouseOverHelpButton()){ //help button
            fill(150);
        } else {
            fill(200);
        }
        rect(200, 425, 600, 100, 20);
        if(isMouseOverRestartButton()){ //Restart button
            fill(150);
        } else {
            fill(200);
        }
        rect(200, 550, 600, 100, 20);

        fill(0); //button texts
        textAlign(CENTER, CENTER);
        textSize(36);
        text("Click or Press ENTER to Resume", 500, 350);
        text("How to Play?", 500, 475);
        text("Restart", 500, 600);

        fill(255); //game paused text
        textSize(96);
        text("Game Paused!", width/2, height/2 - 150);

        if(instructionsShown) { //help button
            fill(100);
            rect(250, 100, 500, 500, 35);
            if(isMouseOverCloseButton()){
                fill(150);
            } else {
                fill(200);
            }
            rect(695, 125, 30, 30, 10);
            fill(0);
            textAlign(CENTER, CENTER);
            textSize(20);
            text("X", 710, 140);

            fill(255);
            textSize(32);
            text("Game:", width/2, 130);
            text("Controls:", width/2, 320);
            text("Have Fun!", width/2, 540);
            textSize(16);
            text("Avoid Projectiles; Getting Hits Costs 25 Health Points", width/2, 160); //game
            text("Enter Green Pools and Gain Keys", width/2, 200);
            text("Keys Appear Next to Character;", width/2, 220);
            text("Drop Them Off at the Key Drop Off to Complete Levels", width/2, 240);
            text("Complete Level 3 to Win the Game!", width/2, 280);
            text("Press B to Toggle Between Keyboard and Mouse Control", width/2, 350);
            text("Use WASD or Arrow Keys in Keyboard Mode", width/2, 370);
            text("Press Shift to Deploy Shield;", width/2, 410);
            text("Damage While Blocking is Decreased by 25%", width/2, 430);
            text("Press ENTER to Pause", width/2, 470);
        }
    }


    void gameOverScreen() {
        cursor();
        if (failure == true) { //failed
            background(255, 0, 0);
            if(isMouseOverResetButton()){
                fill(150);
            } else {
                fill(200);
            }
            rect(btnX, btnY, btnW, btnH, 10);
            fill(0);
            textAlign(CENTER, CENTER);
            textSize(16);
            text("Restart", btnX + btnW / 2, btnY + btnH / 2);
            textSize(32);
            text("You Failed!", width/2, (height-100)/2);
        } else { //success
            background(0, 150, 0);
            if(isMouseOverResetButton()){
                fill(150);
            } else {
                fill(200);
            }
            rect(btnX, btnY, btnW, btnH, 10);
            fill(0);
            textAlign(CENTER, CENTER);
            textSize(16);
            text("Play Again!", btnX + btnW / 2, btnY + btnH / 2);
            textSize(32);
            text("Success!", width/2, (height-100)/2);
        }
    }

    void resetGame() {

        xbase = 50; //character position
        ybase = 50;
        xpos = 50;
        ypos = 50;
        loop = 0;

        blocking = false;

        health = 100;
        healthDebounceBlock = 15;
        healthDebounceNorm = 15;
        regenDebounce = 50;

        lvlrepetition = 225; //levels
        level = 1;

        keyTakenA1 = false;
        keyTakenA2 = false;
        keyTakenB2 = false;
        keyTakenA3 = false;
        keyTakenB3 = false;
        keyTakenC3 = false;

        screen = "start";

    }

    boolean circleCircleCollisionCheck(float x1, float y1, float d1, float x2, float y2, float d2) {
        float distance = dist(x1, y1, x2, y2);
        return (distance <= d1/2 + d2/2);
    }

    boolean intcircleCircleCollisionCheck(float x1, float y1, float d1, int x2, int y2, float d2) {
        float distance = dist(x1, y1, x2, y2);
        return (distance <= d1/2 + d2/2);
    }


    boolean rectangleRectangleCollisionCheck(float x1, float y1, float w1, float h1,
                                             float x2, float y2, float w2, float h2) {
        return ((x1 <= x2 + w2) &&
                (x1 + w1 >= x2) &&
                (y1 <= y2 + h2) &&
                (y1 + h1 >= y2));
    }

    boolean rectangleCircleCollisionCheck(float rx, float ry, float rw, float rh,
                                          float cx, float cy, float cd) {
        float testX = cx;
        float testY = cy;

        if(cx < rx) testX = rx;
        else if(cx > rx + rw) testX = rx + rw;
        if(cy < ry) testY = ry;
        else if(cy > ry + rh) testY = ry + rh;
        float dX = cx - testX;
        float dY = cy - testY;
        float distance = sqrt(dX*dX + dY*dY);
        return (distance < cd/2);
    }

}