package main;

import java.io.BufferedWriter;
import java.util.ArrayList;

import entity.Animal;
import entity.DNA;

public class SaveLoadChart {
	
	public static void saveData() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("chart.dna", true));
		ArrayList<String> species = new ArrayList<String>();
		for(Animal a : Game.animals)
			if(!species.contains(a.getDna().getSpecies())) species.add(a.getDna().getSpecies());
		double time = System.currentTimeMillis();
		for(String specie : species) {
			int totNum = 0;
			int totFood = 0;
			float totFOVa = 0;
			int totFOVr = 0;
			int totMoveSpeed = 0;
			int totRadius = 0;
			float totMutRate = 0;
			int totEatRate = 0;
			int totFleeRadius = 0;
			int totMateMin = 0;
			for(Animal a : Game.animals) {
				DNA d = a.getDna();
				if(!a.getDna().getSpecies().equals(specie)) continue;
				totNum++;
				totFood+=a.getFood();
				totFOVa+=d.getFieldOfViewAngle();
				totFOVr+=d.getFieldOfViewRadius();
				totMoveSpeed+=d.getMoveSpeed();
				totRadius+=d.getRadius();
				totMutRate+=d.getMutationRate();
				totEatRate+=d.getEatingRate();
				totFleeRadius+=d.getFleeRadius();
				totMateMin+=d.getMatingMinimum();
			}
			bw.write(time+","+specie+","+totNum+","+totFood/totNum+","+totFOVa/totNum+","+totFOVr/totNum+","+
					totMoveSpeed/totNum+","+totRadius/totNum+","+totMutRate/totNum+","+totEatRate/totNum+","+
					totFleeRadius/totNum+","+totMateMin/totNum);
			bw.newLine();
		}
		bw.close();
	}

}
