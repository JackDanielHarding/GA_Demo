package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import actions.Action;
import actions.ActionHandler;
import chromesomes.PriorityChromesome;
import chromesomes.ReactionChromesome;
import logging.Logger;
import logging.Logger.Category;
import map.TileMap;
import map.TileType;

public class Entity implements Comparable<Entity> {

	private static final int INITIAL_LIFE = 10;
	private Vector2i position;
	private PriorityChromesome pChromesome;
	private ReactionChromesome rChromesome;
	private int fitness = 0;
	private int life = INITIAL_LIFE;
	private boolean dead = false;

	public Entity() {
		pChromesome = new PriorityChromesome();
		rChromesome = new ReactionChromesome();
	}

	public Entity(Entity parent1, Entity parent2) {
		pChromesome = new PriorityChromesome(parent1.getPChromesome(), parent2.getPChromesome());
		rChromesome = new ReactionChromesome(parent1.getRChromesome(), parent2.getRChromesome());
		mutate();
	}

	public void move(TileMap map) {
		TileType reactTile = null;
		TileType currentTile;
		List<Vector2i> tilePositions = new ArrayList<>();

		for (int viewY = position.getY() - 1; viewY <= position.getY() + 1; viewY++) {
			for (int viewX = position.getX() - 1; viewX <= position.getX() + 1; viewX++) {
				if (!(viewX == position.getX() && viewY == position.getY())) {
					currentTile = map.getTile(viewX, viewY);
					Logger.debug("Checked Tile [" + viewX + ", " + viewY + "] " + currentTile.toString(),
							Category.ENTITIES);
					if ((reactTile == null)
							|| (pChromesome.getPriority(reactTile) < pChromesome.getPriority(currentTile))) {
						reactTile = currentTile;
						tilePositions.clear();
					}

					if (currentTile == reactTile) {
						tilePositions.add(new Vector2i(viewX, viewY));
					}
				}
			}
		}

		Random rand = new Random();
		Vector2i reactPosition = tilePositions.get(rand.nextInt(tilePositions.size()));

		Logger.debug("PriorityTile: " + reactTile.toString(), Category.ENTITIES);

		Action action = rChromesome.getReaction(reactTile);
		Logger.debug("Reaction: " + action.toString(), Category.ENTITIES);

		Vector2i tileDirection = new Vector2i(reactPosition.getX() - position.getX(),
				reactPosition.getY() - position.getY());
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

		if (moveTile == TileType.EMPTY || moveTile == TileType.FOOD) {
			map.setTile(position.getX(), position.getY(), TileType.EMPTY);
			position.setX(movementPosition.getX());
			position.setY(movementPosition.getY());
			map.setTile(position.getX(), position.getY(), TileType.ENTITY);
			Logger.debug(this.toString() + " position: " + position.toString(), Category.ENTITIES);
		}

		if (life <= 0) {
			dead = true;
			Logger.debug(this + " Died", Category.ENTITIES);
		}
	}

	private void mutate() {
		pChromesome.mutate();
		rChromesome.mutate();
	}

	public void reset() {
		life = INITIAL_LIFE;
		fitness = 0;
		dead = false;
	}

	public void setPosition(Vector2i position) {
		this.position = position;
		Logger.debug(this.toString() + " position: " + position.toString(), Category.ENTITIES);
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

	public PriorityChromesome getPChromesome() {
		return pChromesome;
	}

	public ReactionChromesome getRChromesome() {
		return rChromesome;
	}

	@Override
	public int compareTo(Entity entity) {
		if (entity.getFitness() > fitness) {
			return -1;
		} else if (entity.getFitness() == fitness) {
			return 0;
		} else {
			return 1;
		}
	}
}
