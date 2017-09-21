package entities;
import actions.Action;
import chromesomes.PriorityChromesome;
import chromesomes.ReactionChromesome;
import map.TileMap;
import map.TileType;

public class Entity {
	
	private int x;
	private int y;
	private PriorityChromesome pChromesome;
	private ReactionChromesome rChromesome;
	private int fitness;
	
	public Entity(int x, int y){
		this.x = x;
		this.y = y;
		pChromesome = new PriorityChromesome();
		rChromesome = new ReactionChromesome();
	}
	
	public Entity(Entity parent1, Entity parent2){
		
	}
	
	public Position getMovement(TileMap map){
		int reactX = 0;
		int reactY = 0;
		TileType reactTile = null;
		TileType currentTile;
		
		for(int viewY = y - 1; viewY <= y + 1; viewY++){
			for(int viewX = x - 1; viewX <= x + 1; viewX++){
				if(viewX != x && viewY != y){
					currentTile = map.getTile(x, y);
					if((reactTile == null) || (pChromesome.getPriority(reactTile) < pChromesome.getPriority(currentTile))){
						reactTile = currentTile;
						reactX = x;
						reactY = y;
					} 
				}
			}
		}
		
		Action action = rChromesome.getReaction(reactTile);
		
		int moveX;
		int moveY;
		
		return new Position(moveX, moveY);
	}
	
	public int getFitness(){
		return fitness;
	}
}
