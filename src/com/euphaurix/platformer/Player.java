package com.euphaurix.platformer;

import java.util.List;

import static com.euphaurix.platformer.Platformer.*;

public class Player {

    private static final int JUMP_COOLDOWN = 750; // ms

    private int x;
    private float y;
    private final int[] color;
    private final Game game;

    private float vy;
    private long cooldown;

    public Player(int x, int y, int[] color, Game game) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.game = game;

        this.vy = 0;
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
        if (!(game.getState() instanceof GameState.Playing)) return; // Game Check

        if (this.y <= 0 || this.y >= HEIGHT // Y-Position check
        || this.x - game.getCameraX() < 0 || this.x - game.getCameraX() > WIDTH) { // Checking if player is within screen
            game.getState().complete();
        }

        for (List<Integer> platform : game.getMapGenerator().getCurrentMap()) {
            int startX = platform.get(0);
            int endX = platform.get(1);
            int platformY = platform.get(2);

            if (x >= startX && x < endX
            && y + 1 >= platformY && y <= platformY) {
                if (System.currentTimeMillis() > this.cooldown) {
                    vy = 0;
                    y = platformY - 1;
                    return;
                }
                break;
            }
        }

        this.vy += 0.01;
        this.y += this.vy;
    }

    public void jump() {
        if (System.currentTimeMillis() > this.cooldown + JUMP_COOLDOWN) {
            this.vy = -0.25F;
            this.cooldown = System.currentTimeMillis() + 500;
        }
    }
}
