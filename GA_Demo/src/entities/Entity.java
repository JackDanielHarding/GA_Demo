package entities;

import actions.Action;
import actions.ActionHandler;
import chromesomes.PriorityChromesome;
import chromesomes.ReactionChromesome;
import logging.Logger;
import logging.Logger.Category;
import map.TileMap;
import map.TileType;

public class Entity {

	private Vector2i position;
	private PriorityChromesome pChromesome;
	private ReactionChromesome rChromesome;
	private int fitness = 0;
	private int life = 10;
	private boolean dead = false;

	public Entity(Vector2i position) {
		this.position = position;
		pChromesome = new PriorityChromesome();
		rChromesome = new ReactionChromesome();
		Logger.debug(this.toString() + " position: " + position.toString(), Category.ENTITIES);
	}

	public Entity(Entity parent1, Entity parent2) {

	}

	public Vector2i move(TileMap map) {
		int reactX = 0;
		int reactY = 0;
		TileType reactTile = null;
		TileType currentTile;

		for (int viewY = position.getY() - 1; viewY <= position.getY() + 1; viewY++) {
			for (int viewX = position.getX() - 1; viewX <= position.getX() + 1; viewX++) {
				if (!(viewX == position.getX() && viewY == position.getY())) {
					currentTile = map.getTile(viewX, viewY);
					Logger.debug("Checked Tile [" + viewX + ", " + viewY + "] " + currentTile.toString(),
							Category.ENTITIES);
					if ((reactTile == null)
							|| (pChromesome.getPriority(reactTile) < pChromesome.getPriority(currentTile))) {
						reactTile = currentTile;
						reactX = viewX;
						reactY = viewY;
					}
				}
			}
		}

		Logger.debug("PriorityTile: " + reactTile.toString(), Category.ENTITIES);

		Action action = rChromesome.getReaction(reactTile);
		Logger.debug("Reaction: " + action.toString(), Category.ENTITIES);

		Vector2i tileDirection = new Vector2i(reactX - position.getX(), reactY - position.getY());
		Logger.debug("Tile Direction: x: " + tileDirection.getX() + ", y: " + tileDirection.getY(), Category.ENTITIES);

		Vector2i movementVector = ActionHandler.useAction(action, tileDirection);
		Logger.debug("Movement Direction: x: " + movementVector.getX() + ", y: " + movementVector.getY(),
				Category.ENTITIES);

		Vector2i movementPosition = new Vector2i(position.getX() + movementVector.getX(),
				position.getY() + movementVector.getY());

		TileType moveTile = map.getTile(movementPosition.getX(), movementPosition.getY());

		life--;
		fitness++;

		if (moveTile == TileType.FOOD) {
			life += 5;
		}

		if (life <= 0) {
			dead = true;
		}

		if (moveTile == TileType.EMPTY || moveTile == TileType.FOOD) {
			position.setX(movementPosition.getX());
			position.setY(movementPosition.getY());
			Logger.debug(this.toString() + " position: " + position.toString(), Category.ENTITIES);
			return movementPosition;
		} else {
			Logger.debug(this.toString() + " position: " + position.toString(), Category.ENTITIES);
			return position;
		}
	}

	public void setPosition(Vector2i position) {
		this.position = position;
	}

	public Vector2i getPosition() {
		return position;
	}

	public int getFitness() {
		return fitness;
	}

	public boolean isDead() {
		return dead;
	}
}
