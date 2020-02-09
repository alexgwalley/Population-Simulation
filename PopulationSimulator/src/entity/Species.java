package entity;

import java.util.ArrayList;

public class Species {
	
	public static ArrayList<Species> speciesList = new ArrayList<Species>();
	
	private String name;
	
	public Species(String name) {
		this.name = name;
		speciesList.add(this);
	}
	
	public String getName() {
		return name;
	}
	
	public static Species getSpecies(String species) {
		for(Species specie : speciesList)
			if(specie.getName().equals(species))
				return specie;
		return null;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
