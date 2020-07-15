package com.goossensdigital.gdxplatformer.world;/*
 * @created 15/07/2020 - 14:24
 * @project libgdxplatformer
 * @author sidney
 */

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.goossensdigital.gdxplatformer.entities.Entity;
import com.goossensdigital.gdxplatformer.entities.Player;

import java.util.ArrayList;

public abstract class GameMap {

    protected ArrayList<Entity> entities;

    protected OrthographicCamera camera;

    public GameMap () {
        entities = new ArrayList<Entity>();
        entities.add(new Player(40, 600, this));
    }

    public  void render (OrthographicCamera camera, SpriteBatch batch) {
        this.camera = camera;
        for (Entity entity : entities) {
            entity.render(batch);
        }
    }
    public  void update (float delta) {
        for(Entity entity : entities) {
            entity.update(delta, -9.8f);
        }
        if(camera != null) {
            camera.position.set(new Vector3(entities.get(0).getPos().x, entities.get(0).getPos().y, 0));
            camera.update();
        }
    }
    public abstract void dispose ();

    /**
     * Gets a tile by pixel position within the game world at a specified layer.
     * @param layer
     * @param x
     * @param y
     * @return
     */
    public TileType getTileTypeByLocation(int layer, float x, float y) {
        return this.getTileTypeByCoordinate(layer, (int) (x / TileType.TILE_SIZE), (int)(y / TileType.TILE_SIZE));
    }

    /**
     * Gets a tile at its coordinate within the map at a specified layer.
     * @param layer
     * @param col
     * @param row
     * @return
     */
    public abstract TileType getTileTypeByCoordinate(int layer, int col, int row);

    public boolean doesRectCollideWithMap(float x, float y, int width, int height) {
        if(x < 0 || y < 0 || x + width > getPixelWidth() || y + height > getPixelHeight())
            return true;

        for (int row = (int) (y / TileType.TILE_SIZE); row < Math.ceil((y + height) / TileType.TILE_SIZE); row++) {
            for (int col = (int) (x / TileType.TILE_SIZE); col < Math.ceil((x + width) / TileType.TILE_SIZE); col++) {
                for (int layer = 0; layer < getLayers(); layer++) {
                    TileType type = getTileTypeByCoordinate(layer, col, row);
                    if(type != null && type.isCollidable()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getLayers();

    public int getPixelWidth() {
        return this.getWidth() * TileType.TILE_SIZE;
    }

    public int getPixelHeight() {
        return this.getHeight() * TileType.TILE_SIZE;
    }
}
