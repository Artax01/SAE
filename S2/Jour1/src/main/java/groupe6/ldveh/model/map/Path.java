package groupe6.ldveh.model.map;

public class Path {
	public Place firstPlace;
	public Place secondePlace;
	public int length;
	
	public Path(Place p1, Place p2) {
		this.firstPlace = p1;
		this.secondePlace = p2;
	}

	public Place getFirstPlace() {
		return firstPlace;
	}

	public void setFirstPlace(Place firstPlace) {
		this.firstPlace = firstPlace;
	}

	public Place getSecondePlace() {
		return secondePlace;
	}

	public void setSecondePlace(Place secondePlace) {
		this.secondePlace = secondePlace;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
