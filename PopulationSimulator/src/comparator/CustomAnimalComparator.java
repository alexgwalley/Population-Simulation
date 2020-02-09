package comparator;

import java.util.Comparator;

import entity.Animal;

public class CustomAnimalComparator implements Comparator<Animal> {

	@Override
	public int compare(Animal a1, Animal a2) {
		if(a1.getTimeAlive() == a2.getTimeAlive()) return 0;
		if(a1.getTimeAlive() < a2.getTimeAlive()) return 1;
		return -1;
	}

}
