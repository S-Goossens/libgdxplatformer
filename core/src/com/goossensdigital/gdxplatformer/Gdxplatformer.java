package com.goossensdigital.gdxplatformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.goossensdigital.gdxplatformer.world.GameMap;
import com.goossensdigital.gdxplatformer.world.TileType;
import com.goossensdigital.gdxplatformer.world.TiledGameMap;

public class Gdxplatformer extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera camera;

	GameMap gameMap;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();

		gameMap = new TiledGameMap();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		gameMap.update(Gdx.graphics.getDeltaTime());
		gameMap.render(camera, batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
