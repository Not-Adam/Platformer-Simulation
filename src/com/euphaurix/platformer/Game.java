package com.euphaurix.platformer;

import processing.core.PApplet;

import static com.euphaurix.platformer.Platformer.HEIGHT;
import static com.euphaurix.platformer.Platformer.WIDTH;

public class Game {

    private final PApplet sketch;
    private final MapGenerator map;

    private Player p1;
    private Player p2;
    private int cameraX;
    private GameState state = new GameState.Reset(this);

    public Game(PApplet sketch) {
        this.sketch = sketch;
        this.map = new MapGenerator(this);
    }

    public PApplet getSketch() {
        return this.sketch;
    }
    public int getCameraX() {
        return this.cameraX;
    }
    public MapGenerator getMapGenerator() {
        return this.map;
    }

    // Player getters
    public Player getP1() {
        return this.p1;
    }
    public Player getP2() {
        return this.p2;
    }

    protected void setState(GameState state) { this.state = state; }
    public GameState getState() {
        return state;
    }

    public void initialize() {
        this.map.generate();

        p1 = new Player(0, HEIGHT / 2 - 1, new int[]{0, 255, 0}, this);
        p2 = new Player(1, HEIGHT / 2 - 1, new int[]{0, 0, 255}, this);
    }

    public void render() {
        this.map.render();
        this.p1.render();
        this.p2.render();
        cameraX = (p1.getX() + p2.getX()) / 2 - WIDTH / 2;
    }
}
