package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector2i;

import actions.Action;
import actions.ActionHandler;
import genes.BooleanGene;
import genes.IntegerGene;
import genes.OrderGene;
import genes.ReactionGene;
import logging.Logger;
import logging.Logger.Category;
import map.TileMap;
import map.TileType;

public class Entity implements Comparable<Entity> {

	private static final int INITIAL_LIFE = 10;
	private static final int FOOD_LIFE = 5;
	private static final int VIEW_RANGE = 3;
	private Vector2i position;
	private OrderGene<TileType> priorityChrom;
	private ReactionGene reactionChrom;
	private BooleanGene aggressionGene;
	private IntegerGene hungerGene;
	private int fitness = 0;
	private int life = INITIAL_LIFE;
	private boolean dead = false;

	public Entity() {
		priorityChrom = new OrderGene<>("Priority Chromosome", TileType.class);
		reactionChrom = new ReactionGene();
		aggressionGene = new BooleanGene("Aggression Gene");
		hungerGene = new IntegerGene("Hunger Gene", INITIAL_LIFE);
	}

	public Entity(Entity entity) {
		priorityChrom = new OrderGene<>(entity.getPriorityChromosome());
		reactionChrom = new ReactionGene(entity.getReactionsChromosome());
		aggressionGene = new BooleanGene(entity.getAggressionGene());
		hungerGene = new IntegerGene(entity.getHungerGene());
		fitness = entity.getFitness();
	}

	public Entity(Entity parent1, Entity parent2) {
		priorityChrom = new OrderGene<>(parent1.getPriorityChromosome(), parent2.getPriorityChromosome());
		reactionChrom = new ReactionGene(parent1.getReactionsChromosome(), parent2.getReactionsChromosome());
		aggressionGene = new BooleanGene(parent1.getAggressionGene(), parent2.getAggressionGene());
		hungerGene = new IntegerGene(parent1.getHungerGene(), parent2.getHungerGene());
		mutate();
	}

	public void update(TileMap map) {

		life--;
		fitness++;

		TileType currentTile;
		Map<TileType, List<Vector2i>> tilePositions = new EnumMap<>(TileType.class);
		for (TileType type : TileType.values()) {
			tilePositions.put(type, new ArrayList<Vector2i>());
		}

		for (int viewY = position.y() - VIEW_RANGE; viewY <= position.y() + VIEW_RANGE; viewY++) {
			for (int viewX = position.x() - VIEW_RANGE; viewX <= position.x() + VIEW_RANGE; viewX++) {
				if ((!(viewX == position.x() && viewY == position.y())) && !map.outOfRange(viewX, viewY)) {
					currentTile = map.getTile(viewX, viewY);
					List<Vector2i> tileList = tilePositions.get(currentTile);
					tileList.add(new Vector2i(viewX, viewY));
				}
			}
		}

		Vector2i reactPosition = new Vector2i(0, 0);
		boolean found = false;
		TileType reactTile = null;

		List<TileType> priorities = priorityChrom.getList();
		for (int i = 0; i < priorities.size(); i++) {
			reactTile = priorities.get(i);
			if ((reactTile == TileType.FOOD) && life > hungerGene.getValue()) {
				continue;
			}
			List<Vector2i> priorityPositions = tilePositions.get(reactTile);
			Collections.shuffle(priorityPositions);
			for (Vector2i priorityPosition : priorityPositions) {
				Logger.debug("Checking Position: " + priorityPosition, Category.ENTITIES);
				reactPosition = inSight(map, priorityPosition);
				if (reactPosition != null) {
					found = true;
					break;
				}
			}
			if (found) {
				break;
			}
		}

		if (reactPosition == null) {
			return;
		}

		Logger.debug("Found Tile: " + found, Category.ENTITIES);
		Logger.debug("PriorityTile: " + reactTile.toString(), Category.ENTITIES);

		Action action = reactionChrom.getReaction(reactTile);
		Logger.debug("Reaction: " + action.toString(), Category.ENTITIES);
		Logger.debug("React Position: " + reactPosition, Category.ENTITIES);
		Logger.debug("Current Position: " + position, Category.ENTITIES);

		Vector2i tileDirection = new Vector2i(reactPosition.x() - position.x(), reactPosition.y() - position.y());
		Logger.debug("Tile Direction: " + tileDirection, Category.ENTITIES);

		Vector2i movementVector = ActionHandler.useAction(action, tileDirection);
		Logger.debug("Movement Direction: " + movementVector, Category.ENTITIES);

		Vector2i movementPosition = new Vector2i(position.x() + movementVector.x(), position.y() + movementVector.y());

		TileType moveTile = map.getTile(movementPosition.x(), movementPosition.y());

		if (moveTile == TileType.FOOD) {
			life += FOOD_LIFE;
			if (life > INITIAL_LIFE) {
				life = INITIAL_LIFE;
			}
		}

		if (moveTile == TileType.ENTITY && aggressionGene.getValue()) {
			map.killEntity(movementPosition);
		} else {

			if (moveTile == TileType.EMPTY || moveTile == TileType.FOOD) {
				map.setTile(position.x(), position.y(), TileType.EMPTY);
				position.setComponent(0, movementPosition.x());
				position.setComponent(1, movementPosition.y());
				map.setTile(position.x(), position.y(), TileType.ENTITY);
				Logger.debug(this.toString() + " position: " + position.toString(), Category.ENTITIES);
			}
		}

		if (life <= 0) {
			dead = true;
			Logger.debug(this + " Died", Category.ENTITIES);
		}
	}

	private Vector2i inSight(TileMap map, Vector2i tilePosition) {
		List<Vector2i> viewTiles = getTilesOnLine(position, tilePosition);
		for (Vector2i tile : viewTiles) {
			Logger.debug("Line of Sight Position: x: " + tile.x() + ", y: " + tile.y(), Category.ENTITIES);
			TileType tileType = map.getTile(tile.x(), tile.y());
			if (!(tile.x() == position.x() && tile.y() == position.y())
					&& (tileType == TileType.WALL || tileType == TileType.ENTITY)) {
				Logger.debug("Tile Blocked", Category.ENTITIES);
				return null;
			}
		}
		return viewTiles.get(1);
	}

	private static List<Vector2i> getTilesOnLine(Vector2i start, Vector2i goal) {
		List<Vector2i> tiles = new ArrayList<>();
		int x0 = start.x();
		int y0 = start.y();
		int x1 = goal.x();
		int y1 = goal.y();

		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;

		int err = dx - dy;
		int e2;

		while (true) {
			tiles.add(new Vector2i(x0, y0));

			if (x0 == x1 && y0 == y1)
				break;

			e2 = 2 * err;
			if (e2 > -dy) {
				err = err - dy;
				x0 = x0 + sx;
			}

			if (e2 < dx) {
				err = err + dx;
				y0 = y0 + sy;
			}
		}

		return tiles;
	}

	private void mutate() {
		priorityChrom.mutate();
		reactionChrom.mutate();
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

	public OrderGene<TileType> getPriorityChromosome() {
		return priorityChrom;
	}

	public ReactionGene getReactionsChromosome() {
		return reactionChrom;
	}

	public BooleanGene getAggressionGene() {
		return aggressionGene;
	}

	public IntegerGene getHungerGene() {
		return hungerGene;
	}

	public void kill() {
		dead = true;
	}

	@Override
	public int compareTo(Entity entity) {
		if (entity.getFitness() > fitness) {
			return 1;
		} else if (entity.getFitness() == fitness) {
			return 0;
		} else {
			return -1;
		}
	}

	public void printStats() {
		Logger.info("Fitness: " + fitness);
		Logger.info(priorityChrom.toString());
		Logger.info(reactionChrom.toString());
		Logger.info(aggressionGene.toString());
		Logger.info(hungerGene.toString());
	}
}
