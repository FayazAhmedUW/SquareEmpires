/**
 * Player management class
 */
import java.util.ArrayList;

public class Player {
	
	private ArrayList<Army> armies = new ArrayList<Army>();
	private ArrayList<City> cities = new ArrayList<City>();
	private ArrayList<Mission> goals = new ArrayList<Mission>();
	private String name = new String ();
	private int id;
	private int AIFlavour;
	private int gold;
	private int manpower;
	
	/**
	 * Constructor for the Player class
	 * @param name - name of the player
	 * @param AI - the AI flavour, 0 if human other numbers are AI
	 * @param first - starting army
	 * @param Capital - capital city of the player
	 * @param ID - ID number of the player, used to identify in various program methods and processes
	 * @param cash - starting gold of the player
	 * @param manpow - starting manpower of the player
	 */
	public Player (String name, int AI, Army first, City Capital, int ID, int cash, int manpow){
		this.name = name;
		this.AIFlavour = AI;
		this.armies.add(first);
		this.id = ID;
		this.cities.add(Capital);
		this.gold = cash;
		this.manpower = manpow;
	}
	
	/**
	 * Mutator for the name instance variable
	 * @param name - new name
	 */
	public void setName (String name){
		this.name = name;
	}
	
	/**
	 * Mutator for the player ID instance variable
	 * @param ID - new ID number
	 */
	public void setID (int ID){
		this.id = ID;
	}
	
	/**
	 * Mutator for the AI flavour 
	 * @param AI - new AI flavour
	 */
	public void setFlavour (int AI){
		this.AIFlavour = AI;
	}
	
	/**
	 * Mutator for the gold instance variable
	 * @param gold - new gold amount
	 */
	public void setGold (int gold){
		this.gold = gold;
	}
	
	/**
	 * Mutator for the manpower instance variable
	 * @param manpower - new manpower amount
	 */
	public void setManpower (int manpower){
		this.manpower = manpower;
	}
	
	/**
	 * Adds another army to the player's armyList
	 * @param army - new army being added to the player's control
	 */
	public void addArmy (Army army){
		armies.add(army);
	}
	
	/**
	 * Accessor for the name instance variable
	 * @return the name of the player
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Accessor for the ID instance variable
	 * @return the player's ID number
	 */
	public int getID(){
		return this.id;
	}
	
	/**
	 * Accessor for the AIFlavour instance variable
	 * @return the AI type
	 */
	public int getFlavour(){
		return this.AIFlavour;
	}
	
	/**
	 * Accessor for the gold instance variable
	 * @return the amount of gold the player has
	 */
	public int getGold(){
		return this.gold;
	}
	
	/**
	 * Accessor for the manpower instance variable
	 * @return the amount of manpower the player has
	 */
	public int getManpower(){
		return this.manpower;
	}
	
	/**
	 * Accessor for the armies instance variable
	 * @return the list of all armies owned by the player
	 */
	public ArrayList<Army> getArmies(){
		return this.armies;
	}
	
	/**
	 * Accessor for the cities instance variable
	 * @return the cities owned by the player
	 */
	public ArrayList<City> getOwnCity(){
		return this.cities;
	}
	
	/**
	 * Method that removes armies that are of 0 manpower, i.e dead armies
	 */
	public void graveChk (){
		for (int i=0; i<this.armies.size(); i++){
			if (this.armies.get(i).getManpower() <= 0) this.armies.remove(i);
		}
	}
	
	/**
	 * Adds a mission, only applicable if the player is an AI
	 * @param impossible - mission being added
	 */
	public void addMission (Mission impossible){ //XD
		this.goals.add(impossible);
	}
	
	/**
	 * Removes a mission, only applicable if the Player is an AI
	 * @param impossible - mission being removed
	 */
	public void removeMission (Mission impossible){
		for (int i=0; i<this.goals.size(); i++){
			if (this.goals.get(i).equals(impossible)){
				this.goals.remove(i);
				break;
			}
		}
	}
}