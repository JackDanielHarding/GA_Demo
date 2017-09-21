package entities;
import actions.Action;
import actions.ActionHandler;
import chromesomes.PriorityChromesome;
import chromesomes.ReactionChromesome;
import map.TileMap;
import map.TileType;

public class Entity {
	
	private Vector2i position;
	private PriorityChromesome pChromesome;
	private ReactionChromesome rChromesome;
	private int fitness = 0;
	private int life = 10;
	private boolean dead = false;
	
	public Entity(Vector2i position){
		this.position = position;
		pChromesome = new PriorityChromesome();
		rChromesome = new ReactionChromesome();
	}
	
	public Entity(Entity parent1, Entity parent2){
		
	}
	
	public Vector2i getMovement(TileMap map){
		int reactX = 0;
		int reactY = 0;
		TileType reactTile = null;
		TileType currentTile;
		
		for(int viewY = position.getY() - 1; viewY <= position.getY() + 1; viewY++){
			for(int viewX = position.getX() - 1; viewX <= position.getX() + 1; viewX++){
				if(viewX != position.getX() && viewY != position.getY()){
					currentTile = map.getTile(position.getX(), position.getY());
					if((reactTile == null) || (pChromesome.getPriority(reactTile) < pChromesome.getPriority(currentTile))){
						reactTile = currentTile;
						reactX = position.getX();
						reactY = position.getY();
					} 
				}
			}
		}
		
		Action action = rChromesome.getReaction(reactTile);
		
		int directionX = reactX - position.getX();
		int directionY = reactY - position.getY();
		
		Vector2i movementVector = ActionHandler.useAction(action, new Vector2i(directionX, directionY));
		Vector2i movementPosition = new Vector2i(position.getX() + movementVector.getX(), position.getY() + movementVector.getY());
		
		TileType moveTile = map.getTile(movementPosition.getX(), movementPosition.getY());
		
		life--;
		fitness++;
		
		if(life <= 0){
			dead = true;
		}
		
		if(moveTile == TileType.EMPTY || moveTile == TileType.FOOD){
			position.setX(movementPosition.getX());
			position.setY(movementPosition.getY());
			return movementPosition;
		} else {
			return position; 
		}
	}
	
	public void setPosition(Vector2i position){
		this.position = position;
	}
	
	public Vector2i getPosition(){
		return position;
	}
	
	public int getFitness(){
		return fitness;
	}
	
	public boolean isDead(){
		return dead;
	}
}
