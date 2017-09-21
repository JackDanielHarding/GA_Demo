package map;
public class TileMap {
	
	private TileType[][] tiles;
	
	public TileMap(int size){
		tiles = new TileType[size][size];
		
		for(int y = 0; y < size; y++){
			for(int x = 0; x < size; x++){
				if((x == 0 || x == size -1) || (y == 0 || y == size -1)){
					tiles[x][y] = TileType.WALL;
				} else {
					tiles[x][y] = TileType.EMPTY;
				}
			}
		}
	}
	
	public TileType getTile(int x, int y){
		return tiles[x][y];
	}
	
	public void setTile(int x, int y, TileType type){
		tiles[x][y] = type;
	}
	
	public TileType[][] getTiles(){
		return tiles;
	}
}
