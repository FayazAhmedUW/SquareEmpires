import java.util.ArrayList;

public class GameMap {
	
	private int[] size = new int [2];
	private MapTile[][] tiles = new MapTile[size[0]][size[1]];
	
	public GameMap(int[]lenWid){
		this.size = lenWid;
		MapTile[][] tempTiles = new MapTile[size[0]][size[1]];
		this.tiles = tempTiles;
	}
	
	public MapTile[][] getTileMap (){
		return this.tiles;
	}
	public void placeCity (ArrayList<City> cityList, City city){
		cityList.add(city);
	}
	
	public void placeArmy (ArrayList<Army> armyList, Army army){
		armyList.add(army);
	}
	
	public void setTile (int x, int y, MapTile tile){
		this.tiles[x][y] = tile;
	}
	
	public void setSize (int[] newSize){
		this.size = newSize;
	}
	
	public MapTile getTile (int x, int  y){
		return this.tiles[x][y];
	}
	
	public int[] getSize (){
		return this.size;
	}
	
	public void ASCIIMap (){
		for (int i=0; i<this.size[0]; i++){
			for (int j=0; j<this.size[1]; j++){
				if (this.tiles[i][j].getHasCity()) System.out.print("C ");
				else if (this.tiles[i][j].getTerrain()>=0 && this.tiles[i][j].getTerrain()<3) System.out.print("* ");
				else System.out.print("- ");
			}
			System.out.print("\n");
		}
	}

	
}
