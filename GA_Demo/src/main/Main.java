package main;

import java.util.Random;

import display.Window;

import entities.Entity;
import entities.Vector2i;
import map.TileMap;
import map.TileType;

public class Main {

	private static final int NUM_ENTITIES = 10;
	private static final int MAP_SIZE = 10;
	private static final int NUM_FOOD = 5;

	private Entity[] entities;
	private int generation = 1;
	private Entity bestEntity;
	
	TileMap map;
	
	private static final int INIT_WIDTH = 600;
	private static final int INIT_HEIGHT = 600;
	
	Window w;

	public void run() {
		setup();
		while(!w.shouldClose()) loop();
	}

	public void setup() {
		
		w = new Window(INIT_WIDTH,INIT_HEIGHT);

		map = new TileMap(MAP_SIZE);

		entities = new Entity[NUM_ENTITIES];

		Random rand = new Random();
		int x;
		int y;

		for (int i = 0; i < entities.length; i++) {
			do {
				x = rand.nextInt(MAP_SIZE);
				y = rand.nextInt(MAP_SIZE);
			} while (!map.getTile(x, y).equals(TileType.EMPTY));
			entities[i] = new Entity(new Vector2i(x, y));
			map.setTile(x, y, TileType.ENTITY);
		}

		for (int i = 0; i < NUM_FOOD; i++) {
			do {
				x = rand.nextInt(MAP_SIZE);
				y = rand.nextInt(MAP_SIZE);
			} while (!map.getTile(x, y).equals(TileType.EMPTY));
			map.setTile(x, y, TileType.FOOD);
		}
		
		for (Entity entity : entities) {
			Vector2i entityPosition = entity.getPosition();
			map.setTile(entityPosition.getX(), entityPosition.getY(), TileType.EMPTY);
			if (!entity.isDead()) {
				Vector2i entityMovement = entity.getMovement(map);
				map.setTile(entityMovement.getX(), entityMovement.getY(), TileType.ENTITY);
			}
			if (bestEntity == null || entity.getFitness() > bestEntity.getFitness()) {
				bestEntity = entity;
			}
		}
	}

	public void loop() {
		
		w.refresh();
		map.render(w);

	}

	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}
}
