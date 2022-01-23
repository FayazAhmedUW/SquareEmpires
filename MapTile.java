import java.util.ArrayList;

public class MapTile {
	private int[] location = new int[2];
	private int terrarin;
	private boolean hasCity;
	private boolean hasArmy;
	private int owner;
	
	public MapTile(int[] loc, int ter, boolean hasC, boolean hasA, int ow){
		this.location = loc;
		this.terrarin = ter;
		this.hasCity = hasC;
		this.hasArmy = hasA;
		this.owner = ow;
	}
	
	/**
	 * @param ter
	 * Legend:
	 * 0 - desert
	 * 1 - hills
	 * 2 - forest
	 * 3 - farmland
	 * 4 - mountains
	 * 5 - plains
	 * 6 - water
	 */
	public void setTerrain (int ter){
		this.terrarin = ter;
	}
	
	public void checkHasCity (boolean hasC){
		this.hasCity = hasC;
	}
	
	public void setHasArmy (boolean hasA){
		this.hasArmy = hasA;
	}
	
	/**
	 * @param ow
	 * 0 - no owner
	 */
	public void setOwner (int ow){
		this.owner = ow;
	}
	
	public int[] getLoc(){
		return this.location;
	}
	
	public int getTerrain(){
		return this.terrarin;
	}
	
	public boolean getHasCity(){
		return this.hasCity;
	}
	
	public boolean getHasArmy(){
		return this.hasArmy;
	}
	
	public int getOwner(){
		return this.owner;
	}
	
	
	public City getCity (ArrayList<City> cityList){
		for (int i=0; i<cityList.size(); i++){
			if (this.location==cityList.get(i).getLoc()){
				return cityList.get(i);
			}
		}
		return null;
	}
	
	public Army getArmy(ArrayList<Army> armyList){
		for (int i=0; i<armyList.size(); i++){
			if (this.location==armyList.get(i).getLoc()){
				return armyList.get(i);
			}
		}
		return null;
	}
	

}
