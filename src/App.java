import processing.core.*;

public class App extends PApplet {

    float goodX; 
    float goodY;

    float badX;
    float badY;

    float bad2X;
    float bad2Y;           // here are all my variables

    float bad3X;
    float bad3Y;
 
    float ballsize = 40; //size of balls
    float speed = 8; // falling speed

    float paddleX = 150;
    float paddleY = 700;
    float paddleWidth = 100;
    float paddleHeight = 15;

    boolean moveLeft = false; // controls movements
    boolean moveRight = false;

    int score = 0;
    int startTime;
    int elapsedTime;
    int endTime = 30; //game is 30 seconds

    String gamescene = "start"; // controls the screen

    int resetButtonX = 350;
    int resetButtonY = 450;
    int resetButtonWidth = 160;
    int resetButtonHeight = 60;

    int startButtonX = 320;
    int startButtonY = 450;
    int startButtonWidth = 160;
    int startButtonHeight = 60;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {
        goodX = random(width); //random start positions
        badX = random(width);

        bad2X = random(width);
        bad2Y = 0;

        bad3X = random(width);
        bad3Y = 0;

        bad2Y = -200;
        bad3Y = -400;
        startTime = millis(); // start timer

    }

    public void settings() {
        size(800, 800); // window size
    }

    public void draw() {

        background(135, 200, 235);

        if (gamescene.equals("start")) { // Start screen
            background(135, 200, 235);
            fill(255);
            text("Catch the blue balls", 300, 220);

            textSize(22);
            text("Move paddle with left and right arrows", 220, 280); // chat helped me find thes numbers because it was
                                                                      // hard to do trial and error
            text("Catch blue balls for 10 points", 260, 320);
            text("Avoid red balls or -10 points", 280, 360);
            text("You have 30 seconds to play!", 270, 400);
            fill(0, 200, 0); // play button
            rect(startButtonX, startButtonY, startButtonWidth, startButtonHeight);
            fill(255);
            textSize(26);
            text("play", 380, 490);
        }

        if (gamescene.equals("play")) { // game screen
            background(135, 200, 235);
            fill(GRAY);

            fill(255);
            textSize(20);
            text("time: " + (30 - elapsedTime), 650, 30);
            elapsedTime = (millis() - startTime) / 1000;
            speed += 0.003; // slowly increase

            if (elapsedTime >= endTime) {
                gamescene = "end"; // end of screen

            }
            drawClouds(); // chat gpt helped me with the clouds

            fill(34, 139, 34);
            rect(0, 750, width, 50);
            paddleMove(); // move the paddle

            fill(50, 50, 50);
            rect(paddleX, paddleY, paddleWidth, paddleHeight);

            ballsMoving(); // draw + move balls

            if (collide(badX, badY)) {
                score -= 10;
                badY = 0;                   // collision with bad balls
                badX = random(width);
            }
            if (collide(bad2X, bad2Y) == true) {
                score -= 10;
                bad2Y = 0;
                bad2X = random(width);
            }

            if (collide(bad3X, bad3Y)) {
                score -= 10;
                bad3Y = 0;
                bad3X = random(width);

            }

            if (goodY + ballsize / 2 >= paddleY &&
                    goodX >= paddleX &&
                    goodX <= paddleX + paddleWidth) {

                score += 10;
                goodY = 0;                      //collisions with good balls
                goodX = random(width);
            }

            ballsPastScreenBottom();
            fill(255);
            textSize(20);
            text("Score: " + score, 10, 25);
        }

        else if (gamescene.equals("end")) {

            background(135, 200, 235);
            fill(255);
            textSize(40);
            text("times up", 460, 400);
            text("final score: " +  score, 200, 400);

            fill(0, 200, 0);
            rect(resetButtonX, resetButtonY, resetButtonWidth, resetButtonHeight);

            fill(255);
            textSize(24);
            text("Play Again", 375, 490);
        }

    }

    public void keyPressed() {// chat helped me get an idea on how to do this
        if (keyCode == LEFT) {
            moveLeft = true;
        }
        if (keyCode == RIGHT) {
            moveRight = true;
        }
    }

    public void mousePressed() {
        
         if (gamescene.equals("start")) {
            if (mouseX >= startButtonX && mouseX <= startButtonX + startButtonWidth && // chat helped me with getting an idea on how to do this
                    mouseY >= startButtonY && mouseY <= startButtonY + startButtonHeight); {

                startTime = millis();
                gamescene = "play";
                    }
            
                    }
        if (gamescene.equals("end")) {

            if (mouseX >= resetButtonX && mouseX <= resetButtonX + resetButtonWidth &&
                    mouseY >= resetButtonY && mouseY <= resetButtonY + resetButtonHeight) {

                restartgame();
            }
        }
    }

    public boolean collide(float x, float y) { // this method is for when the ball hits
        if (y + ballsize / 2 >= paddleY &&
                x >= paddleX &&
                x <= paddleX + paddleWidth) {
            return true;

        }
        return false;

    }

    public void ballsMoving() { // this is the method of the balls and what they look like with their speed
        fill(0, 150, 255);
        ellipse(goodX, goodY, ballsize, ballsize); // blue balls
        goodY += speed;

        fill(255, 0, 0);
        ellipse(badX, badY, ballsize, ballsize); // red balls
        badY += speed;
        fill(255, 0, 0);
        ellipse(bad2X, bad2Y, ballsize, ballsize);
        bad2Y += speed;

        ellipse(bad3X, bad3Y, ballsize, ballsize);
        bad3Y += speed;
    }

    public void restartgame() { // this method is so everytime a ball passes through, it resets to the top with a certain speed.
        score = 0;

        goodX = random(width);
        goodY = 0;

        badX = random(width);
        badY = 0;

        bad2X = random(width);
        bad2Y = -200;

        bad3X = random(width);
        bad3Y = -400;

        startTime = millis();

        gamescene = "play";
        speed = 8;
    }

    public void keyReleased() {
        if (keyCode == LEFT) {
            moveLeft = false;       //make the paddle move smoothly for left and right
        }
        if (keyCode == RIGHT) {
            moveRight = false;      
        }
    }

    public void drawClouds() { // this method is for my clouds because before this it was messy in my draw section
        fill(255);
        ellipse(80, 80, 60, ballsize);
        ellipse(110, 80, 60, ballsize);
        ellipse(95, 60, 60, ballsize);

        ellipse(350, 150, 60, ballsize);
        ellipse(380, 150, 60, ballsize);
        ellipse(365, 130, 60, ballsize);

        ellipse(650, 150, 60, ballsize);
        ellipse(680, 150, 60, ballsize);
        ellipse(665, 130, 60, ballsize);
    }

    public void paddleMove() {

        if (moveLeft) {
            paddleX -= 10;
        }                       // speed of paddles moving

        if (moveRight) {
            paddleX += 10;
        }
    }

    public void ballsPastScreenBottom() {  //this is so the ball reset to random hieght
        if (goodY > height) {
            goodY = 0;
            goodX = random(width);

        }
        if (badY > height) {
            badY = 0;
            badX = random(width);
        }
        if (bad3Y > height) {
            bad3Y = 0;
            bad3X = random(width);
        }

    }
}
