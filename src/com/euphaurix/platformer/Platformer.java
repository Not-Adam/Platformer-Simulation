package com.euphaurix.platformer;

import processing.core.PApplet;

public class Platformer extends PApplet {

    // Sketch constants
    public static PApplet sketch;
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;
    public static final int SCALE = 16;
    public static final int FPS = 60;

    public void settings() {
        size(WIDTH * SCALE, HEIGHT * SCALE);
    }

    public Game game;

    public void setup() {
        frameRate(FPS);
        sketch = this;

        game = new Game(sketch);
        game.initialize();
    }

    public void draw() {
        game.getState().render();
    }

    public void keyPressed() {
        if (game.getState() instanceof GameState.Menu) {
            game.getState().complete();
        }
        if (key == CODED) {
            if (keyCode == LEFT) {
                game.getP1().move(-1, 0);
            } else if (keyCode == RIGHT) {
                game.getP1().move(1, 0);
            } else if (keyCode == UP) {
                game.getP1().jump();
            }
        } else {
            if (key == 'a') {
                game.getP2().move(-1, 0);
            } else if (key == 'd') {
                game.getP2().move(1, 0);
            } else if (key == ' ') {
                game.getP2().jump();
            }
        }
    }

    public static void drawPixel(int x, int y, int[] rgb) {
        sketch.fill(rgb[0], rgb[1], rgb[2]);
        sketch.rect(x * SCALE, y * SCALE, SCALE, SCALE);
    }

    public static void main(String[] args) {
        PApplet.main("com.euphaurix.platformer.Platformer");
    }
}
