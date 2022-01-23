import java.util.*;

public class Main {
	
	public static boolean checkCityLoc (ArrayList<City> cityList, int[] loc){
		boolean check = false;
		for (int i=0; i<cityList.size(); i++){
			int[] cityLoc = cityList.get(i).getLoc();
			if (Arrays.equals(cityLoc, loc)) check = true;
		}
		
		return check;
	}
	
	public static int getCityNum (ArrayList<City> cityList, int[] loc){
		int cityNum = -1;
		for (int i=0; i<cityList.size(); i++){
			int[] cityLoc = cityList.get(i).getLoc();
			if (Arrays.equals(cityLoc, loc)){
				cityNum = i;
				break;
			}
		}
		return cityNum;
	}
	public static GameMap mapGenerator (int[] size, ArrayList<City> cityList){
		GameMap NewMap = new GameMap (size);
		//capital initialization (include for however many capitals are present
		int[] loc = {1,1};
		int[] loc2 = new int[2];
		City capital = new City (loc, 1, false, true);
		Random rand = new Random();
		cityList.add(capital);
		
		loc2[0] = size[0] - 2;
		loc2[1] = size[1] - 2;
		City capital2 = new City (loc2, 2, false, true);
		cityList.add(capital2);
		
		for (int i=0; i<size[0]; i++){
			for (int j=0; j<size[1]; j++){
				int n = rand.nextInt(7);
				int[] tileLoc = {i, j};
				boolean hasCity;
				boolean HASARMY = false;
				int owner = 0;
				
				if (checkCityLoc(cityList, tileLoc)) hasCity = true;
				else hasCity = false;
				
				if (hasCity){
					int index = getCityNum(cityList, tileLoc);
					owner = cityList.get(index).getOwner();
				}
				MapTile newTile = new MapTile(tileLoc, n, hasCity, HASARMY, owner);
				NewMap.setTile(i, j, newTile);
			}
		}
		return NewMap;
	}
	
	public static boolean battle (Army atk, Army def, Player atker, Player defer){
		Random rand = new Random();
		int a = rand.nextInt(atk.getMorale());
		int d = rand.nextInt(def.getMorale());
		int atkStr = atk.getManpower()+atk.getMorale()+a;
		int defStr = def.getManpower()+def.getMorale()+d;
		
		if (atkStr>defStr){
			int difference = defStr/atkStr;
			atk.changeManpower(-difference);
			atk.changeMorale(difference/10);
			defer.graveChk();
			return true;
		}
		else{
			int difference = defStr-atkStr;
			def.changeManpower(-difference);
			def.changeMorale(difference/10);
			atker.graveChk();
			return false;
		}
		
	}
	
	public static void landInitial (int[][] land, GameMap map, int[] size){
		MapTile[][] tileMap = map.getTileMap();
		for (int i=0; i<size[0]; i++){
			for (int j=0; j<size[1]; j++){
				land[i][j] = tileMap[i][j].getOwner();
				land[i+1][j] = tileMap[i][j].getOwner();
				land[i-1][j] = tileMap[i][j].getOwner();
				land[i][j+1] = tileMap[i][j].getOwner();
				land[i][j-1] = tileMap[i][j].getOwner();
			}
		}
	}
	
	//updates morale for global morale actions
	public static void moraleUpdate (Player player, int morale){
		for (int i=0; i<player.getArmies().size(); i++){
			player.getArmies().get(i).changeMorale(morale);
		}
	}
	
	//updates land controlled when armies move
	public static void landUpdate (Player player, int[] loc, int[][]land, int[] size){
		int ID = player.getID();
		if (loc[0] == 0){
			land[loc[0]][loc[1]]= ID;
			land[loc[0]+1][loc[1]] = ID;
		}
		else if (loc[0] == size[0]-1){
			land[loc[0]-1][loc[1]] = ID;
			land[loc[0]][loc[1]] = ID;
		}
		else{
			land[loc[0]-1][loc[1]] = ID;
			land[loc[0]][loc[1]] = ID;
			land[loc[0]+1][loc[1]] = ID;
		}
		if (loc[1] == 0){
			land[loc[0]][loc[1]] = ID;
			land[loc[0]][loc[1]+1] = ID;
		}
		else if (loc[1] == size[1]-1){
			land[loc[0]][loc[1]-1] = ID;
			land[loc[0]][loc[1]] = ID;
		}
		else{
			land[loc[0]][loc[1]-1] = ID;
			land[loc[0]][loc[1]] = ID;
			land[loc[0]][loc[1]+1] = ID;
		}
		
	}
	
	public static void GameTurn (int[] size, GameMap map, Player player, ArrayList<City> cityList, ArrayList<Player> playerList, int[][] land){
		ArrayList<City> ownedCities = player.getOwnCity();
		
		//manpower update
		for (int i=0; i<ownedCities.size(); i++){
			if (ownedCities.get(i).getCapital()) player.setManpower(player.getManpower()+15);
			else player.setManpower(player.getManpower()+10);
		}
		
		//gold update
		for (int i=0; i<ownedCities.size(); i++){
			if (ownedCities.get(i).getCapital()) player.setGold(player.getGold()+20);
			else player.setGold(player.getGold()+10);
		}
		
		//land tile collection (manpower and gold)
		for (int i=0; i<size[0]; i++){
			for (int j=0; j<size[1]; j++){
				if (land[i][j] == player.getID()){
					player.setManpower(player.getManpower()+2);
					player.setGold(player.getGold()+1);
				}
			}
		}
		
		//army autogeneration
		boolean reinforceCheck = false;
		for (int i=0; i<ownedCities.size(); i++){
			for (int j=0; j<player.getArmies().size(); j++){
				if (Arrays.equals(ownedCities.get(i).getLoc(), (player.getArmies().get(j).getLoc()))){
					player.getArmies().get(j).changeManpower(10);
					reinforceCheck = true;
				}
			}
			//create a new army in a city if none are present
			if (!reinforceCheck) player.getArmies().add(new Army(10, 2, ownedCities.get(i).getLoc(), false));
		}
		
		//USER INPUT BEGINS
		/*move army input, 
		 * LOC_OF_ARMY - current location of the army selected (1D integer array in format (x,y)), 
		 * LOC_TO_MOVE - location the army is moving to (assumed to be valid, integer array see above)
		 * repeat section for however many moves are allowed per turn per player
		*/
		boolean cityPresent = false;
		boolean armyPresentEn = false;
		boolean armyPresentOwn = false;
		int enemy;
		int enemyArmy;
		int friendlyArmy;
		int cityCaptured;
		
		//if a city is present at the location being moved to its position in the city array list is returned
		if (checkCityLoc(cityList, LOC_TO_MOVE)) cityCaptured = getCityNum(cityList, LOC_TO_MOVE);
		
		//if an army is present at the end location it checks if it is an enemy or friendly and returns their appropriate location in the army array list
		for (int a=0; a<playerList.size(); a++){
			for (int b=0; b<playerList.get(a).getArmies().size(); b++){
				if (player.getID()!=playerList.get(a).getID() && Array.equals(LOC_TO_MOVE, playerList.get(a).getArmies().get(b).getLoc())){
					armyPresentEn = true;
					enemy = a;
					enemyArmy = b;
					break;
				}
				else if (player.getID()==playerList.get(a).getID() && Array.equals(LOC_TO_MOVE, playerList.get(a).getArmies().get(b).getLoc())){
					armyPresentOwn = true;
					friendlyArmy=b;
				}
			}
		}
		
		//initiates movement
		for (int i=0; i<player.getArmies().size(); i++){
			if (Arrays.equals(player.getArmies().get(i).getLoc(),LOC_OF_ARMY)){
				//if city with enemy is present
				if (cityPresent && armyPresentEn){
					boolean battleResult = battle(player.getArmies().get(i), playerList.get(enemy).getArmies().get(enemyArmy), player, playerList.get(enemy));
					//win case
					if (battleResult){
						//enemy city
						playerList.get(enemy).getOwnCity().remove(cityList.get(cityCaptured));
						cityList.get(cityCaptured).setOwner(player.getID());
						player.getOwnCity().add(cityList.get(cityCaptured));
						player.getArmies().get(i).move(LOC_TO_MOVE);
						player.getArmies().get(i).changeMorale(5);
						moraleUpdate(player, 5);
						moraleUpdate(playerList.get(enemy), -10);
						landUpdate(player, LOC_TO_MOVE, land, size);
						
					}
				}
				//if city with no enemy is present
				else if (cityPresent){
					//unowned city
					if (cityList.get(cityCaptured).getOwner() == 0){
						cityList.get(cityCaptured).setOwner(player.getID());
						player.getOwnCity().add(cityList.get(cityCaptured));
						player.getArmies().get(i).move(LOC_TO_MOVE);
						player.getArmies().get(i).changeMorale(5);
						moraleUpdate(player, 5);
						landUpdate(player, LOC_TO_MOVE, land, size);
					}
					//enemy city
					else{
						playerList.get(enemy).getOwnCity().remove(cityList.get(cityCaptured));
						cityList.get(cityCaptured).setOwner(player.getID());
						player.getOwnCity().add(cityList.get(cityCaptured));
						player.getArmies().get(i).changeMorale(5);
						moraleUpdate(player, 5);
						moraleUpdate(playerList.get(enemy), -10);
						landUpdate(player, LOC_TO_MOVE, land, size);
						
					}
				}
				//if enemy is present with no city
				else if (armyPresentEn){
					boolean battleResult = battle(player.getArmies().get(i), playerList.get(enemy).getArmies().get(enemyArmy), player, playerList.get(enemy));
					//if battle is won
					if (battleResult){
						player.getArmies().get(i).move(LOC_TO_MOVE);
						landUpdate(player, LOC_TO_MOVE, land, size);
					}
					//else army is destroyed and cannot be moved
				}
				
				//if friendly army is present, merges armies
				else if (armyPresentOwn){
					player.getArmies().get(friendlyArmy).changeManpower(player.getArmies().get(i).getManpower());
					player.getArmies().get(friendlyArmy).changeMorale(player.getArmies().get(i).getMorale());
					player.getArmies().remove(i);
				}
				
				//simple movement
				else{
					player.getArmies().get(i).move(LOC_TO_MOVE);
					landUpdate(player, LOC_TO_MOVE, land, size);
				}
			}
			
		}
		//gold maintenance
		for (int i=0; i<player.getArmies().size(); i++){
			int men = player.getArmies().get(i).getManpower();
			player.setGold(player.getGold()-men);
		}
		//end turn
		
	}

	public static void main(String[] args) {
		ArrayList<Player> players = new ArrayList<Player>();
		ArrayList<Army> armies = new ArrayList<Army>();
		int[] size = {4, 4};
		ArrayList<City> cityList = new ArrayList<City>();
		
		//map initialization
		GameMap map = mapGenerator(size, cityList);
		//array of land, used for zones of control reference, updates everytime an army moves
		int[][] land = new int[size[0]][size[1]];
		landInitial(land, map, size);
		
		//player initialization (add more players here)
		Army startingArmy = new Army (10, 10, cityList.get(0).getLoc(), false);
		Player player1 = new Player ("Red", 0, startingArmy, cityList.get(0), 1, 100, 1000);
		Army startingArmy2 = new Army (10, 10, cityList.get(1).getLoc(), false);
		Player player2 = new Player ("Blue", 0, startingArmy, cityList.get(1), 2, 100, 1000);
		players.add(player1);
		players.add(player2);
		
		//place in game loop
		GameTurn (size, map, player1, cityList, players, land);
		GameTurn (size, map, player2, cityList, players, land);
		map.ASCIIMap();
		
	}

}