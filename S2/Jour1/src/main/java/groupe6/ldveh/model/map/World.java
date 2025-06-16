package groupe6.ldveh.model.map;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.ArrayList;

import java.util.HashMap;
import groupe6.ldveh.model.logic.UnknownPlaceException;
import groupe6.ldveh.model.logic.ExistPlaceException;

public class World {

	private String name;

	public HashMap<Place, HashMap<Path, Place>> cache1;
	public ArrayList<Place> listePlaces;
	public ArrayList<Path> listePaths;
	
	public World(String name) {
		this.name = name;
		this.cache1 = new HashMap<Place, HashMap<Path, Place>>();
		this.cache = new HashMap<Place, HashMap<Path, Place>>();
		this.listePlaces = new ArrayList<Place>();
		this.listePaths = new ArrayList<Path>();
	}
	
	public void addPath(Path path) throws UnknownPlaceException {
		if (!listePlaces.contains(path.firstPlace) || !listePlaces.contains(path.secondePlace)) {
			throw new UnknownPlaceException();
		}
		
		listePaths.add(path);
	}
	
	public void addPlace(Place place) throws ExistPlaceException {
		if (this.listePlaces.contains(place) ) {
			throw new ExistPlaceException("This place already exist in this world!");
		}
		
		if (this.listePlaces.size() != 0) {
			for (int i = 0; i < this.listePlaces.size(); i++) {
				if (this.listePlaces.get(i).getId() == place.getId()) {
					throw new ExistPlaceException("This place already exist in this world!");
				}
			}
		}
		
		listePlaces.add(place);
	}
	
	public Place getPlaceFromName(String name) {
		for (Place place : this.listePlaces) {
			if (place.getName() == name) {
				return place;
			}
		}
		return null;
	}
	
	public Place getPlaceFromId(int id) {
		for (Place place : this.listePlaces) {
			if (place.getId() == id) {
				return place;
			}
		}
		return null;
	}
	
	public HashMap<Path, Place> getPathsFrom(Place place) {
		if (place.isEnd() == true) {
			return null;
		}
		else if (this.cache1.containsKey(place)) {
			return this.cache1.get(place);
		}
		else {
			HashMap<Path, Place> hashMap = new HashMap<Path, Place>();
			for (Path path : listePaths) {
				if (path.firstPlace.equals(place)) {
					hashMap.put(path, path.secondePlace);
				}
				else if (path.secondePlace.equals(place)) {
					hashMap.put(path, path.firstPlace);
				}
			}
			return hashMap;
		}
	}
	public HashMap<Place, HashMap<Path, Place>> cache = new HashMap<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<Place, HashMap<Path, Place>> getCache() {
		return cache1;
	}

	public void setCache(HashMap<Place, HashMap<Path, Place>> cache) {
		this.cache1 = cache;
	}

	public ArrayList<Place> getListePlaces() {
		return listePlaces;
	}

	public void setListePlaces(ArrayList<Place> listePlaces) {
		this.listePlaces = listePlaces;
	}

	public ArrayList<Path> getListePaths() {
		return listePaths;
	}

	public void setListePaths(ArrayList<Path> listePaths) {
		this.listePaths = listePaths;
	}
	
}
