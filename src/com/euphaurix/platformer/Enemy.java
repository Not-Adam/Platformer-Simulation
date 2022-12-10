package com.euphaurix.platformer;

import processing.core.PApplet;

import static com.euphaurix.platformer.Platformer.drawPixel;

@SuppressWarnings("unused")
public class Enemy {

    private final int startX;
    private int x;
    private int vx;
    private float y;
    private final int[] color;
    private final Game game;
    private final PApplet sketch;

    private final int range;
    private long cooldown;

    public Enemy(int x, int y, int[] color, int range, Game game) {
        this.startX = x;
        this.x = x;
        this.vx = 1;
        this.y = y;
        this.color = color;
        this.range = range;
        this.game = game;
        this.sketch = game.getSketch();
    }

    public int getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public int[] getColor() {
        return color;
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void render() {
        this.update();
        drawPixel(x - game.getCameraX(), (int) y, color);
    }

    public void update() {
        long millis = this.sketch.millis();
        long dMs = millis % 400;
        if ((dMs >= 375 || dMs <= 25) && millis - cooldown > 100) {
            cooldown = millis;
            this.x += this.vx;
            if ((this.startX - this.x) % (this.range - 1) == 0) {
                this.vx *= -1;
            }
        }
    }
}
