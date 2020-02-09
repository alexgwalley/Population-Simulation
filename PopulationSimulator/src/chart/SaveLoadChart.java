package chart;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import entity.Animal;
import entity.DNA;
import main.Game;

public class SaveLoadChart {
	
	public static void saveData() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("res/chart.dna", true));
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
	
	public static String[] loadData(String specie, DataType d) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("res/chart.dna"));
		String[] lines = (String[]) br.lines().toArray();
		ArrayList<String> out = new ArrayList<String>();
		int index = 0;
		switch(d) {
		case TIME: index=0; break;
		case NUM: index=2; break;
		case FOOD: index=3; break;
		case FOVA: index=4; break;
		case FOVR: index=5; break;
		case MOVESPEED: index=6; break;
		case RADIUS: index=7; break;
		case MUTATIONRATE: index=8; break;
		case EATINGRATE: index=9; break;
		case FLEERADIUS: index=10; break;
		case MATINGMIN: index=11; break;
		default: index=1; break;
		}
		for(String line : lines) {
			String[] data = line.split(",");
			if(!data[1].equals(specie)) continue;
			out.add(data[index]);
		}
		return (String[]) out.toArray();
	}

}
