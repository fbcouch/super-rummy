package com.ahsgaming.superrummy;

import com.ahsgaming.superrummy.screens.LevelScreen;
import com.ahsgaming.superrummy.screens.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.FPSLogger;

public class RummyGame extends Game {
	public static final boolean DEBUG = true;
    public static final String LOG = "RummyGame";

    public static final int VERSION = 1;

    FPSLogger fpsLogger = new FPSLogger();

    public static RummyGame instance;
    public static TextureService textureService;

    public GameController gameController;

    public RummyGame() {
        instance = this;
    }

    @Override
    public void create() {
        textureService = new TextureService("assets.atlas");

        gameController = new GameController();
        if (DEBUG) setScreen(new LevelScreen(this, gameController));
    }

    @Override
    public void render() {
        super.render();

        if (DEBUG) fpsLogger.log();
    }

    public static void main(String[] args) {

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Super Rummy (c) 2013 Jami Couch";
        cfg.useGL20 = false;
        cfg.width = 1280;
        cfg.height = 768;
        cfg.fullscreen = false;
        cfg.resizable = true;

        new LwjglApplication(new RummyGame(), cfg);
    }
}
