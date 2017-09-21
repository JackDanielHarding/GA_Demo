package main;
import java.util.Random;

import entities.Entity;
import map.TileMap;
import map.TileType;

public class Main {
	
	private static final int NUM_ENTITIES = 10;
	private static final int MAP_SIZE     = 10;
	private static final int NUM_FOOD     = 5;
	
	private Entity[] entities;
	private int generation = 1;
	private Entity bestEntity;
	
	public void run(){
		TileMap map = new TileMap(MAP_SIZE);
		
		entities = new Entity[NUM_ENTITIES];
		
		Random rand = new Random();
		int x;
		int y;
		
		for(int i = 0; i < entities.length; i++){
			do {
				x = rand.nextInt(MAP_SIZE);
				y = rand.nextInt(MAP_SIZE);
			} while (!map.getTile(x, y).equals(TileType.EMPTY));
			entities[i] = new Entity(x, y);
		}
		
		for (int i = 0; i < NUM_FOOD; i++){
			do {
				x = rand.nextInt(MAP_SIZE);
				y = rand.nextInt(MAP_SIZE);
			} while (!map.getTile(x, y).equals(TileType.EMPTY));
			//TODO: add Food
		}
		
		for(Entity entity : entities){
			entity.getMovement(map);
		}
	}

	
	public static void main(String[] args){
		Main main = new Main();
		main.run();
	}
}
