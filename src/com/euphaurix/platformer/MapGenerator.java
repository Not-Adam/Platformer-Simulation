package com.euphaurix.platformer;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.euphaurix.platformer.Platformer.*;

public class MapGenerator {

    // Platform Generation constants
    private final List<List<Integer>> map = new ArrayList<>();
    private static final int MAP_SIZE = 1000;

    private static final int MAX_PLATFORM_SIZE = 7;
    private static final int MIN_PLATFORM_SIZE = 5;

    private static final int MAX_JUMP = 5;
    private static final int MIN_JUMP = 3;

    private static final int MAX_FALL = 3;
    private static final int MIN_FALL = -3;

    private static final int ENEMY_SPAWN_CHANCE = 4;

    // Class variables
    private final Game game;
    private final PApplet sketch;
    private final List<Enemy> enemies;

    public MapGenerator(Game game) {
        this.game = game;
        this.sketch = game.getSketch();
        this.enemies = new ArrayList<>();
    }

    public void generate() {
        if (!(this.game.getState() instanceof GameState.Reset)) {
            return;
        }
        long start = System.currentTimeMillis();
        this.map.clear();
        this.enemies.clear();
        int startX = 0;
        int endX = (int) sketch.random(MIN_PLATFORM_SIZE, MAX_PLATFORM_SIZE);
        int platformY = HEIGHT / 2;

        while (endX <= MAP_SIZE) {
            List<Integer> platform = Arrays.asList(startX, endX, platformY);
            map.add(platform);

            startX = endX + (int) sketch.random(MIN_JUMP, MAX_JUMP);
            endX = startX + (int) sketch.random(MIN_PLATFORM_SIZE, MAX_PLATFORM_SIZE);
            platformY += (int) sketch.random(MIN_FALL, MAX_FALL);
            if ((int) sketch.random(ENEMY_SPAWN_CHANCE - 1) == 0) {
                enemies.add(new Enemy(startX, platformY - 1, new int[]{201, 155, 156}, Math.abs(startX - endX), game));
            }
        }

        System.out.println("Map generated in " + (System.currentTimeMillis() - start) + "ms!");
        game.getState().complete();
    }

    public List<List<Integer>> getCurrentMap() {
        return this.map;
    }
    public List<Enemy> getEnemies() {
        return this.enemies;
    }

    public void render() {
        for (List<Integer> platform : map) {
            int startX = platform.get(0);
            int endX = platform.get(1);
            int platformY = platform.get(2);

            for (int i = startX; i < endX; i++) {
                drawPixel(i - game.getCameraX(), platformY, new int[]{255, 0, 0});
            }
        }

        for (Enemy enemy : enemies) {
            enemy.render();
        }
    }
}
