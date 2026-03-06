import processing.core.*;

public class App extends PApplet {

    float goodX;
    float goodY;

    float badX;
    float badY;

    float ballsize = 40;
    float speed = 4;

    float paddleX = 150;
    float paddleY = 700;
    float paddleWidth = 100;
    float paddleHeight = 15;

    int score = 0;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {
        goodX = random(width);
        badX = random(width);
    }

    public void settings() {
        size(800, 800);
    }

    public void draw() {
        background(135, 200, 235);

        fill(255);
        ellipse(80, 80, 60, 40);
        ellipse(110, 80, 60, 40);
        ellipse(95, 60, 60, 40);

        ellipse(350, 150, 60, 40);
        ellipse(380, 150, 60, 40);
        ellipse(365, 130, 60, 40);

        ellipse(650, 150, 60, 40);
        ellipse(680, 150, 60, 40);
        ellipse(665, 130, 60, 40);

        fill(34, 139, 34);
        rect(0, 750, width, 50);

        fill(50, 50, 50);
        rect(paddleX, paddleY, paddleWidth, paddleHeight);

        fill(0, 150, 255);
        ellipse(goodX, goodY, ballsize, ballsize);
        goodY += speed;

        fill(255, 0, 0);
        ellipse(badX, badY, ballsize, ballsize);
        badY += speed;

        if (badY + ballsize / 2 >= paddleY &&
                badX >= paddleX &&
                badX <= paddleX + paddleWidth) {

            score -= 10;
            badY = 0;
            badX = random(width);
        }

        if (goodY + ballsize / 2 >= paddleY &&
                goodX >= paddleX &&
                goodX <= paddleX + paddleWidth) {

            score += 10;
            goodY = 0;
            goodX = random(width);
        }

        if (goodY > height) {
            goodY = 0;
            goodX = random(width);

        }
        if (badY > height) {
            badY = 0;
            badX = random(width);

        }

        fill(255);
        textSize(20);
        text("Score: " + score, 10, 25);
    }

    public void keyPressed() {
        if (keyCode == LEFT) {
            paddleX -= 20;
        }
        if (keyCode == RIGHT) {
            paddleX += 20;
        }
    }
}
