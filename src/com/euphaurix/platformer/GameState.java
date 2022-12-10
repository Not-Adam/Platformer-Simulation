package com.euphaurix.platformer;

public abstract class GameState {
    protected final Game game;

    public GameState(Game game) {
        this.game = game;
    }

    public abstract void render();

    public abstract void complete();

    // Inner classes:
    public static class Reset extends GameState {
        public Reset(Game game) {
            super(game);
        }

        @Override
        public void render() {
            game.getSketch().background(0);
            game.getSketch().fill(255);
            game.getSketch().textSize(50);
            game.getSketch().text("Resetting", 100, 100);
            game.initialize();
        }

        @Override
        public void complete() {
            game.setState(new GameState.Menu(game));
        }
    }

    public static class Menu extends GameState {
        public Menu(Game game) {
            super(game);
        }

        @Override
        public void render() {
            game.getSketch().background(0);
            game.getSketch().fill(255);
            game.getSketch().textSize(50);
            game.getSketch().text("Menu", 100, 100);
        }

        @Override
        public void complete() {
            game.setState(new GameState.Playing(game));
        }
    }

    public static class Playing extends GameState {
        public Playing(Game game) {
            super(game);
        }

        @Override
        public void render() {
            game.getSketch().background(0);
            game.render();
        }

        @Override
        public void complete() {
            game.setState(new GameState.Reset(game));
        }
    }
}
