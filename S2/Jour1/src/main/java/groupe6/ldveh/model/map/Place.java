package groupe6.ldveh.model.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import groupe6.ldveh.model.entity.Monster;

public class Place {

	private int id;
	private String name;
	private Monster monster;
	private String description;
	private boolean isEnd;
	private boolean isStart;
	private boolean isDefeat;

	private World world;
	
	public Place(int id, String name, Monster monster, String description, boolean isEnd, boolean isStart, boolean isDefeat) {
		this.id = id;
		this.monster = monster;
		this.name = name;
		this.description = description;
		this.isEnd = isEnd;
		this.isStart = isStart;
		this.isDefeat = isDefeat;
	}
	
	public List<Place> getAdjacentsPlace() {
		ArrayList<Place> liste = new ArrayList<Place>();
		for (Place place : world.getPathsFrom(this).values()) {
			liste.add(place);
		}
		return liste;
	}
	
	public HashMap<Path, Place> getPaths() {
		return world.getPathsFrom(this);
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean isEnd() {
		return isEnd == true;
	}

	public Monster getMonster() {
		return monster;
	}

	public void setMonster(Monster monster) {
		this.monster = monster;
	}

	public String getDescription() {
		
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public boolean isDefeat() {
		return isDefeat;
	}

	public void setDefeat(boolean isDefeat) {
		this.isDefeat = isDefeat;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
}
