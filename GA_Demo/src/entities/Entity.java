package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joml.Vector2i;

import actions.Action;
import genes.BooleanGene;
import genes.IntegerGene;
import genes.MapGene;
import genes.OrderGene;
import logging.Logger;
import logging.Logger.Category;
import map.TileMap;
import map.TileType;

public class Entity implements Comparable<Entity> {

	private static final int INITIAL_LIFE = 15;
	private static final int FOOD_LIFE = 5;
	private static final int VIEW_RANGE = 5;
	private static final float MUTATION_RATE = 0.05f;
	private Vector2i position;
	private OrderGene<TileType> priorityGene;
	private MapGene<TileType, Action> reactionGene;
	private BooleanGene aggressionGene;
	private IntegerGene hungerGene;
	private int fitness = 0;
	private int life = INITIAL_LIFE;
	private boolean dead = false;

	private Vector2i nextMovement;
	private TileType[] sight = new TileType[((VIEW_RANGE * 2 + 1) ^ 2) - 1];

	public Entity() {
		priorityGene = new OrderGene<>("Priority Gene", TileType.class);
		reactionGene = new MapGene<>("Reaction Gene", TileType.class, Action.class);
		aggressionGene = new BooleanGene("Aggression Gene");
		hungerGene = new IntegerGene("Hunger Gene", INITIAL_LIFE);
	}

	public Entity(Entity entity) {
		priorityGene = new OrderGene<>(entity.getPriorityChromosome());
		reactionGene = new MapGene<>(entity.getReactionsChromosome());
		aggressionGene = new BooleanGene(entity.getAggressionGene());
		hungerGene = new IntegerGene(entity.getHungerGene());
		fitness = entity.getFitness();
	}

	public Entity(Entity parent1, Entity parent2) {
		priorityGene = new OrderGene<>(parent1.getPriorityChromosome(), parent2.getPriorityChromosome());
		reactionGene = new MapGene<>(parent1.getReactionsChromosome(), parent2.getReactionsChromosome());
		aggressionGene = new BooleanGene(parent1.getAggressionGene(), parent2.getAggressionGene());
		hungerGene = new IntegerGene(parent1.getHungerGene(), parent2.getHungerGene());
		mutate();
	}

	public void update(TileMap map) {

		life--;
		fitness++;

		Vector2i movementPosition = new Vector2i(position.x() + nextMovement.x(), position.y() + nextMovement.y());

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
		} else {
			int sightIndexX = 0;
			int sightIndexY = 0;

			for (int viewY = position.y() - VIEW_RANGE; viewY <= position.y() + VIEW_RANGE; viewY++) {
				sightIndexX = 0;
				for (int viewX = position.x() - VIEW_RANGE; viewX <= position.x() + VIEW_RANGE; viewX++) {
					if ((!(viewX == position.x() && viewY == position.y()))) {
						if (map.outOfRange(viewX, viewY)) {
							sight[sightIndexX][sightIndexY] = TileType.UNKNOWN;
						} else {
							sight[sightIndexX][sightIndexY] = map.getTile(viewX, viewY);
						}
					} else {
						sight[sightIndexX][sightIndexY] = TileType.SELF;
					}
					sightIndexX++;
				}
				sightIndexY++;
			}
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
		Random random = new Random();

		if (random.nextFloat() <= MUTATION_RATE)
			priorityGene.mutate();
		if (random.nextFloat() <= MUTATION_RATE)
			reactionGene.mutate();
		if (random.nextFloat() <= MUTATION_RATE)
			aggressionGene.mutate();
		if (random.nextFloat() <= MUTATION_RATE)
			hungerGene.mutate();
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
		return priorityGene;
	}

	public MapGene<TileType, Action> getReactionsChromosome() {
		return reactionGene;
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
		Logger.info(priorityGene.toString());
		Logger.info(reactionGene.toString());
		Logger.info(aggressionGene.toString());
		Logger.info(hungerGene.toString());
	}

	public void setNextMovement(Vector2i nextMovement) {
		this.nextMovement = nextMovement;
	}

	public TileType[] getSight() {
		return sight;
	}
}
